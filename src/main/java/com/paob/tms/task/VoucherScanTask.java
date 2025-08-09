package com.paob.tms.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.paob.tms.mapper.TmsCitJournalMapper;
import com.paob.tms.mapper.TmsVoucherDetailMapper;
import com.paob.tms.model.TmsCitJournal;
import com.paob.tms.model.TmsVoucherDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class VoucherScanTask {

    @Autowired
    private TmsVoucherDetailMapper voucherDetailMapper;

    @Autowired
    private TmsCitJournalMapper citJournalMapper;

    @Value("${voucher.file.path}")
    private String voucherFilePath;

    //@Scheduled(cron = "0 */1 * * * ?") // 每1分钟执行一次
    public void scanVoucherFiles() {
        log.info("开始扫描凭证文件，文件路径：{}", voucherFilePath);
        try {
            // 查询所有状态为1的记录
            LambdaQueryWrapper<TmsVoucherDetail> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TmsVoucherDetail::getJournalStatus, "1");
            List<TmsVoucherDetail> voucherDetails = voucherDetailMapper.selectList(wrapper);
            log.info("查询到{}条待处理的凭证记录", voucherDetails.size());

            if (voucherDetails.isEmpty()) {
                return;
            }

            // 按file_index分组
            Map<String, List<TmsVoucherDetail>> groupedDetails = voucherDetails.stream()
                    .collect(Collectors.groupingBy(TmsVoucherDetail::getFileIndex));
            log.info("按file_index分组后共有{}组数据", groupedDetails.size());

            for (Map.Entry<String, List<TmsVoucherDetail>> entry : groupedDetails.entrySet()) {
                String fileIndex = entry.getKey();
                List<TmsVoucherDetail> details = entry.getValue();
                
                if (details.isEmpty()) {
                    continue;
                }

                // 获取第一条记录的company_code
                String companyCode = details.get(0).getCompanyCode();
                
                // 组装文件名
                String fileName = String.format("A23_%s_%s_%s_return.txt",
                        fileIndex.substring(0, 8),
                        companyCode,
                        fileIndex.substring(8));
                log.info("处理文件：{}", fileName);

                // 检查文件是否存在
                Path filePath = Paths.get(voucherFilePath, fileName);
                if (!Files.exists(filePath)) {
                    log.info("文件不存在: {}", fileName);
                    continue;
                }

                // 读取文件内容
                List<String> lines = Files.readAllLines(filePath);
                if (lines.isEmpty()) {
                    log.warn("文件为空: {}", fileName);
                    continue;
                }

                log.info("文件内容：{}", lines);

                // 检查是否只有一行且内容为"30|ok"
                if (lines.size() == 1 && "30|ok".equals(lines.get(0))) {
                    log.info("文件验证通过，更新状态为4");
                    // 验证通过，更新状态为4
                    updateVoucherStatus(details, "4", null);
                } else {
                    log.info("文件验证不通过，更新状态为3");
                    // 验证不通过，解析错误信息
                    Map<String, String> errorMap = parseErrorMessages(lines);
                    updateVoucherStatus(details, "3", errorMap);
                }
            }
        } catch (Exception e) {
            log.error("扫描凭证文件失败", e);
        }
    }

    private Map<String, String> parseErrorMessages(List<String> lines) {
        Map<String, String> errorMap = new HashMap<>();
        for (String line : lines) {
            String[] parts = line.split("\\|");
            if (parts.length >= 4) {
                String key = parts[1];
                String value = parts[2] + ":" + parts[3];
                
                // 检查是否已存在该key的错误信息
                if (errorMap.containsKey(key)) {
                    // 如果存在，检查value是否重复
                    String existingValue = errorMap.get(key);
                    String[] existingValues = existingValue.split(";");
                    boolean isDuplicate = false;
                    
                    // 检查新value是否已存在
                    for (String existing : existingValues) {
                        if (existing.equals(value)) {
                            isDuplicate = true;
                            break;
                        }
                    }
                    
                    // 如果不是重复的value，则拼接
                    if (!isDuplicate) {
                        errorMap.put(key, existingValue + ";" + value);
                    }
                } else {
                    // 如果不存在，直接添加
                    errorMap.put(key, value);
                }
                log.info("解析错误信息 - key: {}, value: {}", key, errorMap.get(key));
            }
        }
        return errorMap;
    }

    private void updateVoucherStatus(List<TmsVoucherDetail> details, String status, Map<String, String> errorMap) {
        for (TmsVoucherDetail detail : details) {
            try {
                // 更新tms_voucher_detail表
                detail.setJournalStatus(status);
                if (errorMap != null) {
                    String errorMsg = errorMap.get(detail.getVoucherBatchName());
                    if (StringUtils.hasText(errorMsg)) {
                        detail.setMsg(errorMsg);
                    }
                }
                int updateCount = voucherDetailMapper.updateById(detail);
                log.info("更新tms_voucher_detail表 - id: {}, status: {}, 更新结果: {}", 
                    detail.getTmsCitJournalId(), status, updateCount > 0 ? "成功" : "失败");

                // 更新tms_cit_journal表
                TmsCitJournal journal = new TmsCitJournal();
                journal.setTmsCitJournalId(detail.getTmsCitJournalId());
                journal.setJournalStatus(Integer.parseInt(status));
                if (errorMap != null) {
                    String errorMsg = errorMap.get(detail.getVoucherBatchName());
                    if (StringUtils.hasText(errorMsg)) {
                        journal.setMsg(errorMsg);
                    }
                }
                updateCount = citJournalMapper.updateById(journal);
                log.info("更新tms_cit_journal表 - id: {}, status: {}, 更新结果: {}", 
                    detail.getTmsCitJournalId(), status, updateCount > 0 ? "成功" : "失败");
            } catch (Exception e) {
                log.error("更新状态失败 - id: {}, status: {}", detail.getTmsCitJournalId(), status, e);
            }
        }
    }
} 