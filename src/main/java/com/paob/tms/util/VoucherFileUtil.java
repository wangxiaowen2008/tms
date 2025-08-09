package com.paob.tms.util;

import com.paob.tms.model.TmsVoucherDetail;
import com.paob.tms.mapper.TmsVoucherDetailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 凭证文件处理工具类
 */
@Component
public class VoucherFileUtil {
    private static final Logger log = LoggerFactory.getLogger(VoucherFileUtil.class);
    
    @Value("${voucher.file.path}")
    private String voucherFilePath;
    
    @Autowired
    private TmsVoucherDetailMapper tmsVoucherDetailMapper;
    
    /**
     * 处理凭证文件
     * @param voucherDetails 凭证明细列表
     * @param useJavaEncrypt 是否使用Java加密（true=Java，false=调用of_out.sh）
     */
    public void processVoucherFiles(List<TmsVoucherDetail> voucherDetails, boolean useJavaEncrypt) {
        try {
            // 获取当前日期
            String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            
            // 获取公司代码（所有记录的公司代码应该相同）
            String companyCode = voucherDetails.get(0).getCompanyCode();
            
            // 从数据库获取最新的文件序号
            String latestFileIndex = getLatestFileIndex(currentDate);
            int fileIndex = 100; // 默认值
            
            if (latestFileIndex != null && latestFileIndex.length() >= 11) {
                // 截取后三位数字并加1
                String lastThreeDigits = latestFileIndex.substring(8);
                fileIndex = Integer.parseInt(lastThreeDigits) + 1;
            }
            
            String fileIndexStr = String.format("%03d", fileIndex);
            
            // 生成批次号
            String batchSeq = String.format("B%s%s", currentDate, fileIndexStr);
            
            // 设置批次号和文件序号
            for (TmsVoucherDetail detail : voucherDetails) {
                detail.setBatchSeq(batchSeq);
                detail.setFileIndex(fileIndexStr);
            }
            
            // 生成数据文件
            String dataFileName = String.format("A23_%s_%s_%s.txt", currentDate, companyCode, fileIndexStr);
            Path dataFilePath = Paths.get(voucherFilePath, dataFileName);
            
            // 写入数据文件内容
            try (BufferedWriter writer = Files.newBufferedWriter(dataFilePath)) {
                for (TmsVoucherDetail detail : voucherDetails) {
                    String line = buildVoucherLine(detail);
                    writer.write(line);
                    writer.newLine();
                }
            }
            
            // 备份原始数据文件
            Path backupPath = Paths.get(voucherFilePath, dataFileName + ".bak");
            Files.copy(dataFilePath, backupPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            
            // 生成压缩文件名和密码文件名
            String compressedFileName = String.format("A23_%s_%s_%s.tar.gz", currentDate, companyCode, fileIndexStr);
            String pwdFileName = String.format("A23_%s_Pw.txt", currentDate);
            
            if (useJavaEncrypt) {
                // Java加密
                FileEncryptionUtil.encryptFile(
                    voucherFilePath,
                    pwdFileName,
                    dataFileName,
                    getPublicKeyPath()
                );
            } else {
                // 调用shell脚本加密
                ProcessBuilder pb = new ProcessBuilder(
                    "/bin/bash",
                    Paths.get("src/main/resources/bash/of_out.sh").toAbsolutePath().toString(),
                    voucherFilePath,
                    pwdFileName,
                    dataFileName,
                    getPublicKeyPath()
                );
                pb.inheritIO();
                Process proc = pb.start();
                int exitCode = proc.waitFor();
                if (exitCode != 0) {
                    throw new RuntimeException("调用of_out.sh加密失败，exitCode=" + exitCode);
                }
            }
            
            // 生成.OK文件
            createOkFile(compressedFileName);
            createOkFile(pwdFileName);
            
            // 记录文件名到汇总文件
            String summaryFileName = String.format("A23_%s.txt", currentDate);
            appendToSummaryFile(summaryFileName, compressedFileName, pwdFileName);
            
            // 生成汇总文件的.OK文件
            createOkFile(summaryFileName);
            
            // 更新数据库中的file_index和batch_seq
            String newBatchSeq = IdWorker.get32UUID();
            for (TmsVoucherDetail detail : voucherDetails) {
                detail.setFileIndex(currentDate + fileIndexStr);
                detail.setBatchSeq(newBatchSeq);
                tmsVoucherDetailMapper.updateById(detail);
            }
            
        } catch (Exception e) {
            log.error("处理凭证文件失败", e);
            //throw new RuntimeException("处理凭证文件失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取最新的文件序号
     */
    private String getLatestFileIndex(String currentDate) {
        LambdaQueryWrapper<TmsVoucherDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(TmsVoucherDetail::getFileIndex, currentDate)
               .orderByDesc(TmsVoucherDetail::getFileIndex)
               .last("LIMIT 1");
        
        TmsVoucherDetail latestDetail = tmsVoucherDetailMapper.selectOne(wrapper);
        return latestDetail != null ? latestDetail.getFileIndex() : null;
    }
    
    /**
     * 构建凭证行内容
     */
    private String buildVoucherLine(TmsVoucherDetail detail) {
        return String.join("|",
            detail.getLineMark(),
            detail.getVoucherSource(),
            detail.getVoucherCategory(),
            detail.getBusinessVoucherNum() != null ? detail.getBusinessVoucherNum() : "",
            detail.getTransDate(),
            detail.getCcy(),
            detail.getExchangeRateType(),
            detail.getVoucherType(),
            detail.getRetentionTypeId() != null ? detail.getRetentionTypeId() : "",
            detail.getBudgetVersionId() != null ? detail.getBudgetVersionId() : "",
            detail.getSetOfBooksId(),
            detail.getCompanyCode(),
            detail.getBusinessCode(),
            detail.getCostCenterCode(),
            detail.getProductionCode(),
            detail.getGlCode(),
            detail.getSubCode(),
            detail.getField1(),
            detail.getField2(),
            detail.getDrTranAmt() != null ? detail.getDrTranAmt().toString() : "0.00",
            detail.getCrTranAmt() != null ? detail.getCrTranAmt().toString() : "0.00",
            detail.getVoucherBatchName(),
            detail.getBatchDesc() != null ? detail.getBatchDesc() : "",
            detail.getVoucherName(),
            detail.getVoucherDesc() != null ? detail.getVoucherDesc() : "",
            detail.getVoucherLineDesc(),
            detail.getFlexfield11() != null ? detail.getFlexfield11() : "",
            detail.getFlexfield12() != null ? detail.getFlexfield12() : "",
            detail.getFlexfield13() != null ? detail.getFlexfield13() : "",
            detail.getFlexfield14() != null ? detail.getFlexfield14() : "",
            detail.getFlexfield15() != null ? detail.getFlexfield15() : "",
            detail.getFlexfield16() != null ? detail.getFlexfield16() : "",
            detail.getFlexfield17() != null ? detail.getFlexfield17() : "",
            detail.getFlexfield18() != null ? detail.getFlexfield18() : "",
            detail.getFlexfield19() != null ? detail.getFlexfield19() : "",
            detail.getFlexfield20() != null ? detail.getFlexfield20() : "",
            detail.getSystemId()
        );
    }
    
    /**
     * 创建.OK文件
     */
    private void createOkFile(String fileName) throws IOException {
        Path okFilePath = Paths.get(voucherFilePath, fileName + ".OK");
        // 如果文件已存在，先删除
        Files.deleteIfExists(okFilePath);
        // 创建新文件
        Files.createFile(okFilePath);
    }
    
    /**
     * 追加内容到汇总文件
     */
    private void appendToSummaryFile(String summaryFileName, String compressedFileName, String pwdFileName) throws IOException {
        Path summaryFilePath = Paths.get(voucherFilePath, summaryFileName);
        try (BufferedWriter writer = Files.newBufferedWriter(summaryFilePath, 
                java.nio.file.StandardOpenOption.CREATE, 
                java.nio.file.StandardOpenOption.APPEND)) {
            writer.write(compressedFileName);
            writer.newLine();
            writer.write(pwdFileName);
            writer.newLine();
        }
    }
    
    /**
     * 获取公钥文件路径
     */
    private String getPublicKeyPath() {
        return Paths.get(voucherFilePath, "public.key").toString();
    }
} 