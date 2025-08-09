package com.paob.tms.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paob.tms.common.BusinessException;
import com.paob.tms.common.ResultCode;
import com.paob.tms.dto.req.*;
import com.paob.tms.dto.resp.*;
import com.paob.tms.enums.*;
import com.paob.tms.mapper.*;
import com.paob.tms.model.*;
import com.paob.tms.service.JournalService;
import com.paob.tms.service.SegmentOptionService;
import com.paob.tms.util.UserUtils;
import com.paob.tms.util.VoucherFileUtil;
import com.paob.tms.utils.CaseInsensitiveReplace;
import com.paob.tms.utils.ExpressionCalculator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.paob.tms.enums.AccrualTypeEnum.*;
import static com.paob.tms.enums.JournalTemplateTypeEnum.TRADE_DATE;
import static com.paob.tms.enums.JournalTemplateTypeEnum.VALUE_DATE;

@Slf4j
@Service
public class JournalServiceImpl extends ServiceImpl<TmsCitJournalMapper, TmsCitJournal> implements JournalService {

    @Autowired
    private TmsCitJournalMapper tmsCitJournalMapper;
    @Autowired
    private SegmentOptionService segmentOptionService;
    @Autowired
    private TmsCitJournalTempMapper tmsCitJournalTempMapper;
    @Autowired
    private TmsVoucherDetailMapper tmsVoucherDetailMapper;
    @Autowired
    private TmsAccrualMapper tmsAccrualMapper;
    @Autowired
    private TmsTradeBlotterMapper tmsTradeBlotterMapper;
    @Autowired
    private TradeCorporateActionMapper tradeCorporateActionMapper;
    @Autowired
    private VoucherFileUtil voucherFileUtil;

    private  Map<String, List<SelectOptionResp>> segment2;//业务段
    private  Map<String, List<SelectOptionResp>> segment3;//成本中心
    private  Map<String, List<SelectOptionResp>> segment4;//产品段
    private  Map<String, List<SelectOptionResp>> segment5;//科目
    private  Map<String, List<SelectOptionResp>> segment6;//子目
    private  Map<String, List<SelectOptionResp>> segment8;//关联方

    @Value("${file.template.path}")
    private String templatePath;

    @Value("${voucher.file.useJavaEncrypt:true}")
    private boolean useJavaEncrypt;

    @Override
    public IPage<JournalResp> queryJournalList(JournalQueryReq req) {
        // 创建分页对象
        Page<TmsCitJournal> page = new Page<>(req.getPageNum(), req.getPageSize());
        
        // 执行查询
        IPage<TmsCitJournal> resultPage = baseMapper.selectJournalList(page, req);

        // 转换为响应对象
        IPage<JournalResp> respPage = new Page<>();
        BeanUtils.copyProperties(resultPage, respPage, "records");

        // 按凭证编号分组
        Map<String, List<TmsCitJournal>> groupByJournalSequence = resultPage.getRecords().stream()
                .collect(Collectors.groupingBy(TmsCitJournal::getJournalSequence));

        List<JournalResp> respList = groupByJournalSequence.entrySet().stream().map(entry -> {
            String journalSequence = entry.getKey();
            List<TmsCitJournal> journalList = baseMapper.selectJournalDetailList(journalSequence);
            TmsCitJournal firstJournal = entry.getValue().get(0);
            
            JournalResp resp = new JournalResp();
            BeanUtils.copyProperties(firstJournal, resp);
            
            // 设置凭证明细
            List<JournalDetailResp> detailList = journalList.stream().map(journal -> {
                JournalDetailResp detail = new JournalDetailResp();
                BeanUtils.copyProperties(journal, detail);
                return detail;
            }).collect(Collectors.toList());

            resp.setJournalDetail(detailList);
            resp.setJournalDetailCount(detailList.size());

            return resp;
        }).collect(Collectors.toList());

        respPage.setRecords(respList);

        // 启动新线程处理数据
        CompletableFuture.runAsync(() -> {
            try {
                processJournalData();
            } catch (Exception e) {
                log.error("处理段值数据失败", e);
            }
        });

        return respPage;
    }

    /**
     * 处理段值数据
     */
    private void processJournalData() {
        // 读取段值数据
        SegmentValueReq req = new SegmentValueReq();
        req.setBookNo("HK_SOB");
        req.setSegmentName("SEGMENT2");
        List<SelectOptionResp> list = segmentOptionService.querySegmentList(req);
        segment2 = list.stream().collect(Collectors.groupingBy(SelectOptionResp::getKey));
        req.setSegmentName("SEGMENT3");
        list = segmentOptionService.querySegmentList(req);
        segment3 = list.stream().collect(Collectors.groupingBy(SelectOptionResp::getKey));
        req.setSegmentName("SEGMENT4");
        list = segmentOptionService.querySegmentList(req);
        segment4 = list.stream().collect(Collectors.groupingBy(SelectOptionResp::getKey));
        req.setSegmentName("SEGMENT5");
        list = segmentOptionService.querySegmentList(req);
        segment5 = list.stream().collect(Collectors.groupingBy(SelectOptionResp::getKey));
        req.setSegmentName("SEGMENT6");
        list = segmentOptionService.querySegmentList(req);
        segment6 = list.stream().collect(Collectors.groupingBy(SelectOptionResp::getKey));
        req.setSegmentName("SEGMENT8");
        list = segmentOptionService.querySegmentList(req);
        segment8 = list.stream().collect(Collectors.groupingBy(SelectOptionResp::getKey));
        
        
        
    }

    @Override
    public void exportJournalList(JournalQueryReq req, HttpServletResponse response) {
        try {
            // 设置响应头
            String fileName = "凭证列表_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xlsx";
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + URLEncoder.encode(fileName, "UTF-8"));

            // 查询导出数据
            List<TmsCitJournal> journalList = baseMapper.selectJournalListForExport(req);

            // 转换为导出对象
            List<JournalExportResp> exportList = journalList.stream().map(journal -> {
                JournalExportResp exportResp = new JournalExportResp();
                //根据凭证状态，JournalStatusEnum 转换
                exportResp.setJournalStatus(JournalStatusEnum.getByCode(journal.getJournalStatus()+"").getDescription());
                BeanUtils.copyProperties(journal, exportResp);
                return exportResp;
            }).collect(Collectors.toList());

            // 导出Excel
            EasyExcel.write(response.getOutputStream(), JournalExportResp.class)
                    .sheet("凭证列表")
                    .doWrite(exportList);
        } catch (IOException e) {
            log.error("导出凭证列表失败", e);
            throw new RuntimeException("导出凭证列表失败", e);
        }
    }

    /**
     * 创建凭证明细对象
     * @param journal 凭证信息
     * @param currentUser 当前用户
     * @return 凭证明细对象
     */
    private TmsVoucherDetail createVoucherDetail(TmsCitJournal journal, String currentUser) {
        TmsVoucherDetail voucherDetail = new TmsVoucherDetail();
        voucherDetail.setTmsCitJournalId(journal.getTmsCitJournalId());
        voucherDetail.setLineMark("10");
        voucherDetail.setVoucherSource("业务");
        voucherDetail.setVoucherCategory("记帐凭证");
        voucherDetail.setBusinessVoucherNum(null);
        voucherDetail.setTransDate(journal.getEffectiveDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        voucherDetail.setCcy(journal.getCurNo());
        voucherDetail.setExchangeRateType("公司");
        voucherDetail.setVoucherType("A");
        voucherDetail.setRetentionTypeId(null);
        voucherDetail.setBudgetVersionId(null);
        voucherDetail.setSetOfBooksId("122");
        voucherDetail.setCompanyCode(journal.getSegment1());
        voucherDetail.setBusinessCode(journal.getSegment2());
        voucherDetail.setCostCenterCode(journal.getSegment3());
        voucherDetail.setProductionCode(journal.getSegment4());
        voucherDetail.setGlCode(journal.getSegment5());
        voucherDetail.setSubCode(journal.getSegment6());
        voucherDetail.setField1("0000");
        voucherDetail.setField2(journal.getSegment8());
        voucherDetail.setDrTranAmt(journal.getEnteredDr());
        voucherDetail.setCrTranAmt(journal.getEnteredCr());
        voucherDetail.setVoucherBatchName(journal.getJournalSequence());
        voucherDetail.setBatchDesc(null);
        voucherDetail.setVoucherName(null);
        voucherDetail.setVoucherDesc(null);
        voucherDetail.setVoucherLineDesc(journal.getJournalLineDescription());
        voucherDetail.setFlexfield11(null);
        voucherDetail.setFlexfield12(null);z
        voucherDetail.setFlexfield13(null);
        voucherDetail.setFlexfield14(null);
        voucherDetail.setFlexfield15(null);
        voucherDetail.setFlexfield16(null);
        voucherDetail.setFlexfield17(null);
        voucherDetail.setFlexfield18(null);
        voucherDetail.setFlexfield19(null);
        voucherDetail.setFlexfield20(null);
        voucherDetail.setSystemId("PAOB-TMS");
        voucherDetail.setJournalStatus("1");//上传中
        voucherDetail.setCreatedBy(currentUser);
        voucherDetail.setCreatedTime(LocalDateTime.now());
        voucherDetail.setUpdatedBy(currentUser);
        voucherDetail.setUpdatedTime(LocalDateTime.now());
        
        return voucherDetail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveJournal(JournalApproveReq req) {
        // 查询凭证信息
        List<TmsCitJournal> journalList = baseMapper.selectJournalDetailList(req.getJournalSequence());
        if (journalList.isEmpty()) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "凭证不存在");
        }

        // 获取第一条记录（所有记录的状态应该相同）
        TmsCitJournal journal = journalList.get(0);

        // 校验凭证状态必须为"待复核"
        if (!JournalStatusEnum.PENDING_REVIEW.getCode().equals(journal.getJournalStatus()+"")) {
            throw new BusinessException(ResultCode.ERROR.getCode(), "凭证状态不是待复核，不能进行复核操作");
        }

        // 校验复核人不能是凭证创建人
        String currentUser = UserUtils.getUser().getUmNo();
        if (currentUser.equals(journal.getCreatedBy())) {
            throw new BusinessException("复核人不能是凭证创建人");
        }

        // 更新凭证状态为"复核通过&待上传"
        TmsCitJournal updateEntity = new TmsCitJournal();
        updateEntity.setJournalStatus(Integer.parseInt(JournalStatusEnum.REVIEW_PASSED_PENDING_UPLOAD.getCode()));
        updateEntity.setCheckBy(currentUser);
        updateEntity.setCheckDate(LocalDate.now());

        // 更新所有相关记录
        List<TmsVoucherDetail> voucherDetails = new ArrayList<>();
        for (TmsCitJournal j : journalList) {
            updateEntity.setTmsCitJournalId(j.getTmsCitJournalId());
            baseMapper.updateById(updateEntity);
            
            // 向tms_voucher_detail表插入记录
            TmsVoucherDetail voucherDetail = createVoucherDetail(j, currentUser);
            
            // 检查记录是否存在
            TmsVoucherDetail existingDetail = tmsVoucherDetailMapper.selectById(j.getTmsCitJournalId());
            if (existingDetail != null) {
                // 如果存在则更新
                voucherDetail.setCreatedBy(existingDetail.getCreatedBy());
                voucherDetail.setCreatedTime(existingDetail.getCreatedTime());
                tmsVoucherDetailMapper.updateById(voucherDetail);
            } else {
                // 如果不存在则插入
                tmsVoucherDetailMapper.insert(voucherDetail);
            }
            voucherDetails.add(voucherDetail);
        }
        
        // 处理凭证文件
        voucherFileUtil.processVoucherFiles(voucherDetails, useJavaEncrypt);

        log.info("凭证复核通过成功，凭证编号：{}", req.getJournalSequence());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unApproveJournal(JournalUnApproveReq req) {
        // 查询凭证信息
        List<TmsCitJournal> journalList = baseMapper.selectJournalDetailList(req.getJournalSequence());
        if (journalList.isEmpty()) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "凭证不存在");
        }

        // 获取第一条记录进行校验
        TmsCitJournal journal = journalList.get(0);

        // 校验凭证状态必须为"待复核"
        if (!JournalStatusEnum.PENDING_REVIEW.getCode().equals(journal.getJournalStatus()+"")) {
            throw new BusinessException(ResultCode.ERROR.getCode(), "凭证状态不是待复核，不能进行复核操作");
        }

        // 校验复核人不能是凭证创建人
        String currentUser = UserUtils.getUser().getUmNo();
        if (currentUser.equals(journal.getCreatedBy())) {
            throw new BusinessException(ResultCode.ERROR.getCode(), "复核人不能是凭证创建人");
        }

        // 更新凭证状态为"复核不通过"
        TmsCitJournal updateEntity = new TmsCitJournal();
        updateEntity.setJournalStatus(Integer.parseInt(JournalStatusEnum.REVIEW_REJECTED.getCode()));
        updateEntity.setCheckBy(currentUser);
        updateEntity.setCheckDate(LocalDate.now());

        // 更新所有相关记录
        for (TmsCitJournal record : journalList) {
            updateEntity.setTmsCitJournalId(record.getTmsCitJournalId());
            baseMapper.updateById(updateEntity);
        }

        log.info("凭证复核不通过成功，凭证编号：{}", req.getJournalSequence());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String batchRejectJournal(JournalBatchRejectReq req) {
        if (req.getJournalSequence() == null || req.getJournalSequence().isEmpty()) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "凭证编号列表不能为空");
        }

        String currentUser = UserUtils.getUser().getUmNo();
        int successCount = 0;

        for (String journalSequence : req.getJournalSequence()) {
            // 查询凭证信息
            List<TmsCitJournal> journalList = baseMapper.selectJournalDetailList(journalSequence);
            if (journalList.isEmpty()) {
                log.warn("凭证不存在，凭证编号：{}", journalSequence);
                continue;
            }

            // 获取第一条记录进行校验
            TmsCitJournal journal = journalList.get(0);

            // 校验凭证状态必须为"待复核"
            if (!JournalStatusEnum.PENDING_REVIEW.getCode().equals(journal.getJournalStatus()+"")) {
                log.warn("凭证状态不是待复核，不能进行复核操作，凭证编号：{}", journalSequence);
                continue;
            }

            // 校验复核人不能是凭证创建人sl
            if (currentUser.equals(journal.getCreatedBy())) {
                log.warn("复核人不能是凭证创建人，凭证编号：{}", journalSequence);
                continue;
            }

            // 更新凭证状态为"复核拒绝"
            TmsCitJournal updateEntity = new TmsCitJournal();
            updateEntity.setJournalStatus(Integer.parseInt(JournalStatusEnum.REVIEW_REJECTED.getCode()));
            updateEntity.setCheckBy(currentUser);
            updateEntity.setCheckDate(LocalDate.now());

            // 更新所有相关记录
            boolean allSuccess = true;
            for (TmsCitJournal record : journalList) {
                updateEntity.setTmsCitJournalId(record.getTmsCitJournalId());
                if (baseMapper.updateById(updateEntity) <= 0) {
                    allSuccess = false;
                    break;
                }
            }

            if (allSuccess) {
                successCount++;
                log.info("凭证复核拒绝成功，凭证编号：{}", journalSequence);
            }
        }

        return String.format("已复核拒绝%d条会计凭证", successCount);
    }


     private void processJournalData1() {
        // 读取段值数据
        SegmentValueReq req = new SegmentValueReq();
        req.setBookNo("HK_SOB");
        req.setSegmentName("SEGMENT2");
        List<SelectOptionResp> list = segmentOptionService.querySegmentList(req);
        segment2 = list.stream().collect(Collectors.groupingBy(SelectOptionResp::getKey));
        req.setSegmentName("SEGMENT3");
        list = segmentOptionService.querySegmentList(req);
        segment3 = list.stream().collect(Collectors.groupingBy(SelectOptionResp::getKey));
        req.setSegmentName("SEGMENT4");
        list = segmentOptionService.querySegmentList(req);
        segment4 = list.stream().collect(Collectors.groupingBy(SelectOptionResp::getKey));
        req.setSegmentName("SEGMENT5");
        list = segmentOptionService.querySegmentList(req);
        segment5 = list.stream().collect(Collectors.groupingBy(SelectOptionResp::getKey));
        req.setSegmentName("SEGMENT6");
        list = segmentOptionService.querySegmentList(req);
        segment6 = list.stream().collect(Collectors.groupingBy(SelectOptionResp::getKey));
        req.setSegmentName("SEGMENT8");
        list = segmentOptionService.querySegmentList(req);
        segment8 = list.stream().collect(Collectors.groupingBy(SelectOptionResp::getKey));
        
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String batchApproveJournal(JournalBatchApproveReq req) {
        if (req.getJournalSequence() == null || req.getJournalSequence().isEmpty()) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "凭证编号列表不能为空");
        }

        String currentUser = UserUtils.getUser().getUmNo();
        int successCount = 0;
        List<TmsVoucherDetail> allVoucherDetails = new ArrayList<>();

        for (String journalSequence : req.getJournalSequence()) {
            // 查询凭证信息
            List<TmsCitJournal> journalList = baseMapper.selectJournalDetailList(journalSequence);
            if (journalList.isEmpty()) {
                log.warn("凭证不存在，凭证编号：{}", journalSequence);
                continue;
            }

            // 获取第一条记录进行校验
            TmsCitJournal journal = journalList.get(0);

            // 校验凭证状态必须为"待复核"
            if (!JournalStatusEnum.PENDING_REVIEW.getCode().equals(journal.getJournalStatus()+"")) {
                log.warn("凭证状态不是待复核，不能进行复核操作，凭证编号：{}", journalSequence);
                continue;
            }

            // 校验复核人不能是凭证创建人
            if (currentUser.equals(journal.getCreatedBy())) {
                log.warn("复核人不能是凭证创建人，凭证编号：{}", journalSequence);
                continue;
            }

            // 更新凭证状态为"复核通过&待上传"
            TmsCitJournal updateEntity = new TmsCitJournal();
            updateEntity.setJournalStatus(Integer.parseInt(JournalStatusEnum.REVIEW_PASSED_PENDING_UPLOAD.getCode()));
            updateEntity.setCheckBy(currentUser);
            updateEntity.setCheckDate(LocalDate.now());

            // 更新所有相关记录
            boolean allSuccess = true;
            List<TmsVoucherDetail> voucherDetails = new ArrayList<>();
            for (TmsCitJournal record : journalList) {
                updateEntity.setTmsCitJournalId(record.getTmsCitJournalId());
                if (baseMapper.updateById(updateEntity) <= 0) {
                    allSuccess = false;
                    break;
                }
                
                // 向tms_voucher_detail表插入记录
                TmsVoucherDetail voucherDetail = createVoucherDetail(record, currentUser);
                
                // 检查记录是否存在
                TmsVoucherDetail existingDetail = tmsVoucherDetailMapper.selectById(record.getTmsCitJournalId());
                if (existingDetail != null) {
                    // 如果存在则更新

                    voucherDetail.setCreatedBy(existingDetail.getCreatedBy());
                    voucherDetail.setCreatedTime(existingDetail.getCreatedTime());
                    
                    tmsVoucherDetailMapper.updateById(voucherDetail);
                    voucherDetails.add(voucherDetail);
                } else {
                    // 如果不存在则插入
                    tmsVoucherDetailMapper.insert(voucherDetail);
                    voucherDetails.add(voucherDetail);
                }
            }

            if (allSuccess) {
                successCount++;
                allVoucherDetails.addAll(voucherDetails);
                log.info("凭证复核通过成功，凭证编号：{}", journalSequence);
            }
        }

        // 处理所有凭证文件
        if (!allVoucherDetails.isEmpty()) {
            voucherFileUtil.processVoucherFiles(allVoucherDetails, useJavaEncrypt);
        }

        return String.format("已复核通过%d条会计凭证", successCount);
    }

    @Override
    public void downloadTemplate(HttpServletResponse response) {
        try {
            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + URLEncoder.encode("凭证模板.xlsx", "UTF-8"));

            // 从配置的路径读取模板文件
            File templateFile = new File(templatePath);
            if (!templateFile.exists()) {
                throw new BusinessException(ResultCode.ERROR.getCode(), "模板文件不存在");
            }

            try (FileInputStream inputStream = new FileInputStream(templateFile);
                 OutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            }
        } catch (IOException e) {
            log.error("下载凭证模板失败", e);
            throw new BusinessException(ResultCode.ERROR.getCode(), "下载凭证模板失败");
        }
    }

    @Override
    public List<JournalDetailResp> getJournalDetail(String journalSequence) {
        // 查询凭证信息
        List<TmsCitJournal> journalList = baseMapper.selectJournalDetailList(journalSequence);
        if (journalList.isEmpty()) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "凭证不存在");
        }

        // 按凭证编号分组
        Map<String, List<TmsCitJournal>> groupByJournalSequence = journalList.stream()
                .collect(Collectors.groupingBy(TmsCitJournal::getJournalSequence));

        // 转换为响应对象
        return groupByJournalSequence.entrySet().stream().map(entry -> {
            String key = entry.getKey();
            List<TmsCitJournal> journals = entry.getValue();
            
            // 获取第一条记录作为主记录
            TmsCitJournal mainJournal = journals.get(0);
            JournalDetailResp detail = new JournalDetailResp();
            BeanUtils.copyProperties(mainJournal, detail);

            // 处理凭证明细列表
            List<TmsCitJournal> segmentList = journals.stream()
                    .map(journal -> {
                        TmsCitJournal  segment = new TmsCitJournal();
                        BeanUtils.copyProperties(journal, segment);
                        return segment;
                    })
                    .collect(Collectors.toList());
            detail.setSegmentList(segmentList);
            return detail;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJournal(List<JournalDetailResp> list) {
        if (list == null || list.isEmpty()) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "凭证信息不能为空");
        }
        JournalDetailResp req = list.get(0);
        // 查询凭证信息
        List<TmsCitJournal> journalList = baseMapper.selectJournalDetailList(req.getJournalSequence());
        if (journalList.isEmpty()) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "凭证不存在");
        }

        // 获取第一条记录进行校验
        TmsCitJournal journal = journalList.get(0);

        // 校验凭证状态必须为"待复核"或"复核拒绝"
        if (!JournalStatusEnum.PENDING_REVIEW.getCode().equals(journal.getJournalStatus()+"") 
            && !JournalStatusEnum.REVIEW_REJECTED.getCode().equals(journal.getJournalStatus()+"")) {
            throw new BusinessException(ResultCode.ERROR.getCode(), "凭证状态不是待复核或复核拒绝，不能进行编辑操作");
        }

        // 校验借贷平衡
        BigDecimal totalDebit = req.getSegmentList().stream()
                .map(TmsCitJournal::getEnteredDr)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalCredit = req.getSegmentList().stream()
                .map(TmsCitJournal::getEnteredCr)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalDebit.compareTo(totalCredit) != 0) {
            throw new BusinessException(ResultCode.ERROR.getCode(), "借贷不平衡");
        }

        // 更新凭证明细
        for (TmsCitJournal segment : req.getSegmentList()) {
            TmsCitJournal detailEntity = new TmsCitJournal();
            BeanUtils.copyProperties(segment, detailEntity);
            detailEntity.setCurNo(req.getCurNo());
            detailEntity.setBooksNo(req.getBooksNo());
            detailEntity.setEffectiveDate(req.getEffectiveDate());
            detailEntity.setSegment1(req.getSegment1());
            detailEntity.setUpdatedBy(UserUtils.getUser().getUmNo());
            detailEntity.setUpdatedTime(LocalDateTime.now());
            baseMapper.updateById(detailEntity);
        }
        log.info("凭证编辑成功，凭证编号：{}", req.getJournalSequence());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String importJournal(MultipartFile file) {
        // 校验文件格式
        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.endsWith(".xlsx")) {
            throw new BusinessException(ResultCode.ERROR.getCode(), "文件格式不符合校验文件！");
        }

        try {
            // 读取Excel文件
            List<Map<Integer, String>> dataList = EasyExcel.read(file.getInputStream())
                    .sheet()
                    .headRowNumber(1)
                    .doReadSync();

            if (dataList.isEmpty()) {
                throw new BusinessException(ResultCode.ERROR.getCode(), "文件不可读，请检查文件后重试！");
            }

            // 按凭证编号分组
            Map<String, List<Map<Integer, String>>> groupByJournalSequence = dataList.stream()
                    .collect(Collectors.groupingBy(row -> row.get(0))); // 第一列为凭证编号

            int successCount = 0;
            for (Map.Entry<String, List<Map<Integer, String>>> entry : groupByJournalSequence.entrySet()) {
                String journalSequence = entry.getKey();
                List<Map<Integer, String>> rows = entry.getValue();

                // 校验凭证编号是否已存在
                if (!baseMapper.selectJournalDetailList(journalSequence).isEmpty()) {
                    throw new BusinessException(ResultCode.ERROR.getCode(), String.format("[%s]已存在", journalSequence));
                }

                // 校验借贷平衡
                BigDecimal totalDebit = BigDecimal.ZERO;
                BigDecimal totalCredit = BigDecimal.ZERO;
                for (Map<Integer, String> row : rows) {
                    String debitStr = row.get(11); // 原币借方金额
                    String creditStr = row.get(12); // 原币贷方金额
                    if (StringUtils.hasText(debitStr)) {
                        totalDebit = totalDebit.add(new BigDecimal(debitStr));
                    }
                    if (StringUtils.hasText(creditStr)) {
                        totalCredit = totalCredit.add(new BigDecimal(creditStr));
                    }
                }
                if (totalDebit.compareTo(totalCredit) != 0) {
                    throw new BusinessException(ResultCode.ERROR.getCode(), String.format("[%s]借贷不平衡", journalSequence));
                }

                // 保存凭证数据
                for (int i = 0; i < rows.size(); i++) {
                    Map<Integer, String> row = rows.get(i);
                    // 校验必填字段
                    if (!validateRequiredFields(row)) {
                        throw new BusinessException(ResultCode.ERROR.getCode(), String.format("[%s]存在必填字段为空", journalSequence));
                    }

                    // 校验段值是否存在
                    String segment5Value = row.get(5); // 科目
                    String segment6Value = row.get(6); // 子目
                    String segment2Value = row.get(7); // 业务段
                    String segment3Value = row.get(8); // 成本中心
                    String segment4Value = row.get(9); // 产品段
                    String segment8Value = row.get(10); // 关联方

                    int rowNum = i + 2; // Excel行号（从1开始，且有表头）
                    StringBuilder errorMsg = new StringBuilder();
                    
                    if (!segment5.containsKey(segment5Value)) {
                        errorMsg.append(String.format("[%d行6列]科目不存在 ", rowNum));
                    }
                    if (!segment6.containsKey(segment6Value)) {
                        errorMsg.append(String.format("[%d行7列]子目不存在 ", rowNum));
                    }
                    if(!segment2.containsKey(segment2Value)){
                        errorMsg.append(String.format("[%d行8列]业务段不存在 ", rowNum));
                    }
                    if(!segment3.containsKey(segment3Value)){
                        errorMsg.append(String.format("[%d行9列]成本中心不存在 ", rowNum));
                    }
                    if(!segment4.containsKey(segment4Value)){
                        errorMsg.append(String.format("[%d行10列]产品段不存在 ", rowNum));
                    }
                    if(!segment8.containsKey(segment8Value)){
                        errorMsg.append(String.format("[%d行11列]关联方不存在 ", rowNum));
                    }

                    if (errorMsg.length() > 0) {
                        throw new BusinessException(ResultCode.ERROR.getCode(), errorMsg.toString().trim());
                    }


                    TmsCitJournal journal = new TmsCitJournal();
                    journal.setJournalSequence(row.get(0)); // 凭证编号
                    journal.setBooksNo(row.get(1)); // 账套
                    journal.setSegment1(row.get(2)); // 公司段
                    journal.setEffectiveDate(LocalDate.parse(row.get(3))); // 凭证日期
                    journal.setCurNo(row.get(4)); // 币种
                    journal.setSegment5(segment5Value); // 科目
                    journal.setSegment6(segment6Value); // 子目
                    journal.setSegment2(segment2Value); // 业务段
                    journal.setSegment3(segment3Value); // 成本中心
                    journal.setSegment4(segment4Value); // 产品段
                    journal.setSegment8(segment8Value); // 关联方
                    journal.setEnteredDr(StringUtils.hasText(row.get(11)) ? new BigDecimal(row.get(11)) : null); // 原币借方金额
                    journal.setEnteredCr(StringUtils.hasText(row.get(12)) ? new BigDecimal(row.get(12)) : null); // 原币贷方金额
                    journal.setAccountedDr(StringUtils.hasText(row.get(13)) ? new BigDecimal(row.get(13)) : null); // 本位币借方金额
                    journal.setAccountedCr(StringUtils.hasText(row.get(14)) ? new BigDecimal(row.get(14)) : null); // 本位币贷方金额
                    journal.setJournalLineDescription(row.get(15)); // 凭证摘要
                    journal.setJournalStatus(Integer.parseInt(JournalStatusEnum.PENDING_REVIEW.getCode())); // 状态：待复核
                    journal.setCreatedBy(UserUtils.getUser().getUmNo());
                    journal.setCreatedTime(LocalDateTime.now());

                    baseMapper.insert(journal);
                }
                successCount++;
            }

            return String.format("已成功上传%d条会计凭证，请联系复核同事进行复核处理！", successCount);
        } catch (IOException e) {
            log.error("导入凭证失败", e);
            throw new BusinessException(ResultCode.ERROR.getCode(), "导入凭证失败：" + e.getMessage());
        }
    }

    /**
     * 校验必填字段
     */
    private boolean validateRequiredFields(Map<Integer, String> row) {
        // 校验除本位币金额外的所有字段
        for (int i = 0; i <= 10; i++) {
            if (!StringUtils.hasText(row.get(i))) {
                return false;
            }
        }
        // 校验原币借贷金额至少有一个
        if (!StringUtils.hasText(row.get(11)) && !StringUtils.hasText(row.get(12))) {
            return false;
        }
        // 校验凭证摘要
        return StringUtils.hasText(row.get(15));
    }

    @Override
    public List<JournalTemplateResp> queryTemplateList() {
        return tmsCitJournalTempMapper.selectTemplateList();
    }

    /**
     * 批量制证实现方法
     * 根据不同的数据源类型，调用相应的处理方法生成凭证
     *
     * @param req 批量制证请求对象
     * @return 批量制证响应对象
     * @throws BusinessException 当参数错误或业务处理异常时抛出
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JournalBatchCreateResp batchCreateJournal(JournalBatchCreateReq req) {
        log.info("开始批量制证，请求参数：dataSource={}, businessIds={}", req.getDataSource(), req.getBusinessIds());

        // 参数校验
        if (!StringUtils.hasText(req.getDataSource()) || CollectionUtils.isEmpty(req.getBusinessIds())) {
            log.error("批量制证失败：数据源和业务ID列表不能为空");
            throw new BusinessException("数据源和业务ID列表不能为空");
        }

        // 根据数据源处理不同的业务逻辑
        List<String> allJournalIds = new ArrayList<>();
        List<String> allJournalNumbers = new ArrayList<>();

        try {
            DataSourceEnum dataSource = DataSourceEnum.getByCode(req.getDataSource());
            if (dataSource == null) {
                log.error("批量制证失败：不支持的数据源类型，dataSource={}", req.getDataSource());
                throw new BusinessException("不支持的数据源类型");
            }

            // 遍历处理每个业务ID
            for (String businessId : req.getBusinessIds()) {
                JournalBatchCreateResp resp;
                switch (dataSource) {
                    case TMS_TRADE_BLOTTER:
                        resp = handleTradeBlotterJournal(businessId,req.getTempType());
                        break;
                    case TMS_ACCRUAL:
                        log.info("开始处理计提记录制证，businessId={},tempType={}", businessId,req.getTempType());
                        resp = handleAccrualJournal(businessId,req.getTempType());
                        break;
                    case TMS_CORPORATE_ACTION:
                        log.info("开始处理公司行为制证，businessId={}", businessId);
                        resp = handleCorporateActionJournal(businessId);
                        break;
                    default:
                        log.error("批量制证失败：不支持的数据源类型，dataSource={}", req.getDataSource());
                        throw new BusinessException("不支持的数据源类型");
                }

                // 收集所有凭证ID和编号
                allJournalIds.addAll(resp.getJournalIds());
                allJournalNumbers.addAll(resp.getJournalNumbers());
            }

            log.info("批量制证成功，生成凭证数量：{}，凭证编号：{}", allJournalIds.size(), allJournalNumbers);
            return new JournalBatchCreateResp(allJournalIds, allJournalNumbers);
        } catch (Exception e) {
            log.error("批量制证失败：dataSource={}, businessIds={}, error={}",
                    req.getDataSource(), req.getBusinessIds(), e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 处理交易记录制证
     * 根据交易记录的类型和备注匹配对应的凭证模板，生成凭证
     * @param tempType 凭证模板类型
     * @param businessId 业务ID（交易记录ID）
     * @return 批量创建凭证响应
     * @throws BusinessException 当交易记录不存在或未找到匹配的凭证模板时抛出
     */
    private JournalBatchCreateResp handleTradeBlotterJournal(String businessId,String tempType) {
        List<String> journalIds = new ArrayList<>();
        List<String> journalNumbers = new ArrayList<>();
        // 获取交易记录，修改逻辑，原先通过关联id查询修改为关联trade_number字段获取唯一一条交易记录
        log.info("开始处理交易记录制证，businessId={}", businessId);
        TmsTradeBlotter tradeBlotter = tmsTradeBlotterMapper.selectOne(
                new LambdaQueryWrapper<TmsTradeBlotter>()
                        .eq(TmsTradeBlotter::getTradeNumber, businessId)
        );
        if (tradeBlotter == null) {
            log.error("交易记录制证失败：记录不存在，businessId={}", businessId);
            return new JournalBatchCreateResp(journalIds, journalNumbers);
        }
        log.info("查询到交易记录：tradeNumber={}, buySellBorrowLend={}, remark={}",
                tradeBlotter.getTradeNumber(), tradeBlotter.getBuySellBorrowLend(), tradeBlotter.getRemark());

        JournalTemplateTypeEnum templateTypeEnum = JournalTemplateTypeEnum.getByCode(tempType);
        // 如果传入了模板类型，使用模板类型查询
        String effectiveDate = null;
        switch (templateTypeEnum) {
            case TRADE_DATE:
                effectiveDate = "Trade Date";
                break;
            case VALUE_DATE:
                effectiveDate = "Value Date";
                break;
            default:
                throw new BusinessException("不支持的交易记录模板类型：" + tempType);
        }
        if(effectiveDate == null){
            log.info("不支持的交易记录模板类型：" + tempType);
            return new JournalBatchCreateResp(journalIds, journalNumbers);
        }
        if(TRADE_DATE.getCode().equals(tempType) && org.apache.commons.lang3.StringUtils.isNotBlank(tradeBlotter.getJournalSequence())){
            log.info("该交易记录已生成过凭证，请勿重复生成");
            return new JournalBatchCreateResp(journalIds, journalNumbers);

        }
        if(VALUE_DATE.getCode().equals(tempType) && org.apache.commons.lang3.StringUtils.isNotBlank(tradeBlotter.getValueDateJournalSequence())){
            log.info("该交易记录已生成过凭证，请勿重复生成");
            return new JournalBatchCreateResp(journalIds, journalNumbers);
        }

        // 查询匹配的模板
        List<TmsCitJournalTemp> templates = tmsCitJournalTempMapper.selectList(
                new LambdaQueryWrapper<TmsCitJournalTemp>()
                        .eq(TmsCitJournalTemp::getEffectiveDate, effectiveDate)
                        .eq(TmsCitJournalTemp::getTempType, tradeBlotter.getBuySellBorrowLend())
                        .eq(TmsCitJournalTemp::getRemark, tradeBlotter.getRemark())
        );

        if (CollectionUtils.isEmpty(templates)) {
            log.error("交易记录制证失败：未找到匹配的凭证模板，buySellBorrowLend={}, remark={}",
                    tradeBlotter.getBuySellBorrowLend(), tradeBlotter.getRemark());
            return new JournalBatchCreateResp(journalIds, journalNumbers);
        }
        log.info("查询到匹配的凭证模板数量：{}", templates.size());

        // 按模板编号分组
        Map<Long, List<TmsCitJournalTemp>> templateGroups = templates.stream()
                .collect(Collectors.groupingBy(TmsCitJournalTemp::getTempNo));
        log.info("模板分组数量：{}", templateGroups.size());


        // 用businessId关联tms_cit_journal表data_id字段删除对应的凭证记录，避免重复制证，添加data_source=TMS_TRADE_BLOTTER删除条件
        tmsCitJournalMapper.delete(new LambdaQueryWrapper<TmsCitJournal>()
                .eq(TmsCitJournal::getDataId, businessId)
                .eq(TmsCitJournal::getDataSource, DataSourceEnum.TMS_TRADE_BLOTTER.getCode()));
        log.info("删除tms_cit_journal表数据成功，businessId={}", businessId);


        // 遍历每个模板组
        for (Map.Entry<Long, List<TmsCitJournalTemp>> entry : templateGroups.entrySet()) {
            List<TmsCitJournalTemp> groupTemplates = entry.getValue();
            log.info("开始处理模板组：tempNo={}, 模板数量={}", entry.getKey(), groupTemplates.size());

            // 生成凭证编号
            String journalNumber = generateJournalNumber();
            log.info("生成凭证编号：{}", journalNumber);

            // 转换模板为凭证
            List<TmsCitJournal> journals = convertTemplateToJournal(groupTemplates, tradeBlotter, journalNumber);
            log.info("转换生成凭证数量：{}", journals.size());

            // 保存凭证
            for (TmsCitJournal journal : journals) {
                journal.setTmsCitJournalId(IdWorker.getIdStr());
                tmsCitJournalMapper.insert(journal);
                journalIds.add(journal.getTmsCitJournalId());
                journalNumbers.add(journal.getJournalSequence());
                log.info("保存凭证成功：journalId={}, journalSequence={}, enteredDr={}, enteredCr={}",
                        journal.getTmsCitJournalId(), journal.getJournalSequence(),
                        journal.getEnteredDr(), journal.getEnteredCr());
            }

            // 根据凭证日期配置回写凭证编号
            effectiveDate = groupTemplates.get(0).getEffectiveDate();
            if (effectiveDate != null && EffectiveDateEnum.TRADE_DATE.getCode().equals(effectiveDate.toUpperCase())) {
                // 如果凭证日期配置为Trade Date，回写至journal_sequence
                tradeBlotter.setJournalSequence(journalNumber);
                tradeBlotter.setJournalStatus(JournalStatusEnum.PENDING_REVIEW.getCode());
                log.info("回写凭证编号至journal_sequence：journalNumber={}", journalNumber);
            } else if (effectiveDate != null && EffectiveDateEnum.VALUE_DATE.getCode().equals(effectiveDate.toUpperCase())) {
                // 如果凭证日期配置为Value Date，回写至value_date_journal_sequence
                tradeBlotter.setValueDateJournalSequence(journalNumber);
                tradeBlotter.setValueDateJournalStatus(JournalStatusEnum.PENDING_REVIEW.getCode());
                log.info("回写凭证编号至value_date_journal_sequence：journalNumber={}", journalNumber);
            } else {
                // 其他情况回写至journal_sequence
                tradeBlotter.setJournalSequence(journalNumber);
                tradeBlotter.setJournalStatus(JournalStatusEnum.PENDING_REVIEW.getCode());
                log.info("回写凭证编号至journal_sequence（默认）：journalNumber={}", journalNumber);
            }
        }

        // 更新交易记录
        tmsTradeBlotterMapper.updateById(tradeBlotter);
        log.info("更新交易记录状态成功：tradeNumber={}, journalSequence={}, valueDateJournalSequence={}",
                tradeBlotter.getTradeNumber(), tradeBlotter.getJournalSequence(), tradeBlotter.getValueDateJournalSequence());

        return new JournalBatchCreateResp(journalIds, journalNumbers);
    }

    /**
     * 处理计提记录制证
     * 根据计提记录的类型和备注匹配对应的凭证模板，生成凭证
     * 包括三种类型的凭证：计提利息收入、分摊溢折价、计提估值变动
     * @param tempType 凭证模板类型
     * @param businessId 业务ID（计提记录ID）
     * @return 批量创建凭证响应
     * @throws BusinessException 当计提记录不存在或未找到匹配的凭证模板时抛出
     */
    private JournalBatchCreateResp handleAccrualJournal(String businessId,String tempType) {
        // 查询计提记录
        TmsAccrual accrual = tmsAccrualMapper.selectById(Long.valueOf(businessId));
        if (accrual == null) {
            log.error("计提记录制证失败：记录不存在，businessId={}", businessId);
            throw new BusinessException("计提记录不存在");
        }
        log.info("查询到计提记录：tradeNumber={}, date={}, currency={}",
                accrual.getTradeNumber(), accrual.getAccrualDate(), accrual.getCurrency());

        // 用businessId关联tms_cit_journal表data_id字段删除对应的凭证记录，避免重复制证，添加data_source=TMS_ACCRUAL删除条件
        tmsCitJournalMapper.delete(new LambdaQueryWrapper<TmsCitJournal>()
                .eq(TmsCitJournal::getDataId, businessId)
                .eq(TmsCitJournal::getDataSource, DataSourceEnum.TMS_ACCRUAL.getCode()));

        List<String> journalIds = new ArrayList<>();
        List<String> journalNumbers = new ArrayList<>();
        List<String> interestJournalNumbers = new ArrayList<>();
        List<String> allocPremDiscJournalNumbers = new ArrayList<>();
        List<String> valuationJournalNumbers = new ArrayList<>();

        JournalTemplateTypeEnum templateTypeEnum = JournalTemplateTypeEnum.getByCode(tempType);
        switch (templateTypeEnum) {
            case ACCRUAL_INTEREST:
                log.info("开始处理计提利息收入");
                //如果计提已生成凭证，则不处理
                if (org.apache.commons.lang3.StringUtils.isNotBlank(accrual.getInterestJournalNo())) {
                    log.info("计提已生成凭证，跳过处理");
                    break;
                }
                handleAccrualInterest(accrual, journalIds, interestJournalNumbers);
                break;
            case ALLOC_PREM_DISC:
                // 2. 处理分摊溢折价
                log.info("开始处理分摊溢折价");
                // 如果分摊溢折价已生成凭证，则不处理
                if (org.apache.commons.lang3.StringUtils.isNotBlank(accrual.getAllocPremDiscJournalNo())) {
                    log.info("分摊溢折价已生成凭证，跳过处理");
                    break;
                }
                handleAccrualPremiumDiscount(accrual, journalIds, allocPremDiscJournalNumbers);
                break;
            case ACCRUAL_VALUATION:
                log.info("开始处理计提估值变动");
                //  如果估值变动已生成凭证，则不处理
                if (org.apache.commons.lang3.StringUtils.isNotBlank(accrual.getValuationJournalNo())) {
                    log.info("估值变动已生成凭证，跳过处理");
                    break;
                }
                handleAccrualValuation(accrual, journalIds, valuationJournalNumbers);
                break;
            default:
                throw new BusinessException("不支持的计提记录模板类型：" + tempType);
        }

        // 更新计提记录的凭证编号和状态
        // 回写计提利息收入凭证编号
        if (!interestJournalNumbers.isEmpty()) {
            accrual.setInterestJournalNo(String.join(",", interestJournalNumbers));
            accrual.setInterestJournalStatus(JournalStatusEnum.PENDING_REVIEW.getCode());
            log.info("回写计提利息收入凭证编号：{}", accrual.getInterestJournalNo());
        }

        // 回写分摊溢折价凭证编号
        if (!allocPremDiscJournalNumbers.isEmpty()) {
            accrual.setAllocPremDiscJournalNo(String.join(",", allocPremDiscJournalNumbers));
            accrual.setAllocPremDiscJournalStatus(JournalStatusEnum.PENDING_REVIEW.getCode());
            log.info("回写分摊溢折价凭证编号：{}", accrual.getAllocPremDiscJournalNo());
        }

        // 回写计提估值变动凭证编号
        if (!valuationJournalNumbers.isEmpty()) {
            accrual.setValuationJournalNo(String.join(",", valuationJournalNumbers));
            accrual.setValuationJournalStatus(JournalStatusEnum.PENDING_REVIEW.getCode());
            log.info("回写计提估值变动凭证编号：{}", accrual.getValuationJournalNo());
        }

        // 合并所有凭证编号
        journalNumbers.addAll(interestJournalNumbers);
        journalNumbers.addAll(allocPremDiscJournalNumbers);
        journalNumbers.addAll(valuationJournalNumbers);

        tmsAccrualMapper.updateById(accrual);
        log.info("更新计提记录状态成功：tradeNumber={}", accrual.getTradeNumber());

        return new JournalBatchCreateResp(journalIds, journalNumbers);
    }

    /**
     * 处理计提利息收入
     * 根据计提记录的类型和备注匹配对应的凭证模板，生成计提利息收入凭证
     *
     * @param accrual 计提记录
     * @param journalIds 凭证ID列表，用于存储生成的凭证ID
     * @param journalNumbers 凭证编号列表，用于存储生成的凭证编号
     */
    private void handleAccrualInterest(TmsAccrual accrual, List<String> journalIds, List<String> journalNumbers) {
        // 查询匹配的模板
        List<TmsCitJournalTemp> templates = tmsCitJournalTempMapper.selectList(
                new LambdaQueryWrapper<TmsCitJournalTemp>()
                        .eq(TmsCitJournalTemp::getTempType, ACCRUAL_INTEREST.getCode())
                        .eq(TmsCitJournalTemp::getRemark, accrual.getRemark())
        );

        if (!CollectionUtils.isEmpty(templates)) {
            // 按模板编号分组
            Map<Long, List<TmsCitJournalTemp>> templateGroups = templates.stream()
                    .collect(Collectors.groupingBy(TmsCitJournalTemp::getTempNo));

            // 遍历每个模板组
            for (Map.Entry<Long, List<TmsCitJournalTemp>> entry : templateGroups.entrySet()) {
                List<TmsCitJournalTemp> groupTemplates = entry.getValue();

                // 生成凭证编号
                String journalNumber = generateJournalNumber();

                // 转换模板为凭证
                List<TmsCitJournal> journals = convertTemplateToJournal(groupTemplates, accrual, journalNumber);

                // 保存凭证
                for (TmsCitJournal journal : journals) {
                    journal.setTmsCitJournalId(IdWorker.getIdStr());
                    tmsCitJournalMapper.insert(journal);
                    journalIds.add(journal.getTmsCitJournalId());
                }
                journalNumbers.add(journalNumber);
            }
        }
    }

    /**
     * 处理分摊溢折价
     * 根据计提记录的类型和备注匹配对应的凭证模板，生成分摊溢折价凭证
     *
     * @param accrual 计提记录
     * @param journalIds 凭证ID列表，用于存储生成的凭证ID
     * @param journalNumbers 凭证编号列表，用于存储生成的凭证编号
     */
    private void handleAccrualPremiumDiscount(TmsAccrual accrual, List<String> journalIds, List<String> journalNumbers) {
        // 查询匹配的模板
        List<TmsCitJournalTemp> templates = tmsCitJournalTempMapper.selectList(
                new LambdaQueryWrapper<TmsCitJournalTemp>()
                        .eq(TmsCitJournalTemp::getTempType, ALLOC_PREM_DISC.getCode())
                        .eq(TmsCitJournalTemp::getRemark, accrual.getRemark())
        );

        if (!CollectionUtils.isEmpty(templates)) {
            // 按模板编号分组
            Map<Long, List<TmsCitJournalTemp>> templateGroups = templates.stream()
                    .collect(Collectors.groupingBy(TmsCitJournalTemp::getTempNo));

            // 遍历每个模板组
            for (Map.Entry<Long, List<TmsCitJournalTemp>> entry : templateGroups.entrySet()) {
                List<TmsCitJournalTemp> groupTemplates = entry.getValue();

                // 生成凭证编号
                String journalNumber = generateJournalNumber();

                // 转换模板为凭证
                List<TmsCitJournal> journals = convertTemplateToJournal(groupTemplates, accrual, journalNumber);

                // 保存凭证
                for (TmsCitJournal journal : journals) {
                    journal.setTmsCitJournalId(IdWorker.getIdStr());
                    tmsCitJournalMapper.insert(journal);
                    journalIds.add(journal.getTmsCitJournalId());
                }
                journalNumbers.add(journalNumber);
            }
        }
    }

    /**
     * 处理计提估值变动
     * 根据计提记录的类型和备注匹配对应的凭证模板，生成计提估值变动凭证
     *
     * @param accrual 计提记录
     * @param journalIds 凭证ID列表，用于存储生成的凭证ID
     * @param journalNumbers 凭证编号列表，用于存储生成的凭证编号
     */
    private void handleAccrualValuation(TmsAccrual accrual, List<String> journalIds, List<String> journalNumbers) {
        // 查询匹配的模板
        List<TmsCitJournalTemp> templates = tmsCitJournalTempMapper.selectList(
                new LambdaQueryWrapper<TmsCitJournalTemp>()
                        .eq(TmsCitJournalTemp::getTempType, ACCRUAL_VALUATION.getCode())
                        .eq(TmsCitJournalTemp::getRemark, accrual.getRemark())
        );

        if (!CollectionUtils.isEmpty(templates)) {
            // 按模板编号分组
            Map<Long, List<TmsCitJournalTemp>> templateGroups = templates.stream()
                    .collect(Collectors.groupingBy(TmsCitJournalTemp::getTempNo));

            // 遍历每个模板组
            for (Map.Entry<Long, List<TmsCitJournalTemp>> entry : templateGroups.entrySet()) {
                List<TmsCitJournalTemp> groupTemplates = entry.getValue();

                // 生成凭证编号
                String journalNumber = generateJournalNumber();

                // 转换模板为凭证
                List<TmsCitJournal> journals = convertTemplateToJournal(groupTemplates, accrual, journalNumber);

                // 保存凭证
                for (TmsCitJournal journal : journals) {
                    journal.setTmsCitJournalId(IdWorker.getIdStr());
                    tmsCitJournalMapper.insert(journal);
                    journalIds.add(journal.getTmsCitJournalId());
                }
                journalNumbers.add(journalNumber);
            }
        }
    }

    /**
     * 将模板转换为凭证
     * 根据不同的数据源类型（交易记录/计提记录/公司行为记录）调用相应的转换方法
     *
     * @param templates 凭证模板列表
     * @param source 数据源对象（交易记录/计提记录/公司行为记录）
     * @param journalNumber 凭证编号
     * @return 凭证列表
     * @throws BusinessException 当数据源类型不支持时抛出
     */
    private List<TmsCitJournal> convertTemplateToJournal(List<TmsCitJournalTemp> templates, Object source, String journalNumber) {
        if (source instanceof TmsTradeBlotter) {
            return convertTradeBlotterTemplate(templates, (TmsTradeBlotter) source, journalNumber);
        } else if (source instanceof TmsAccrual) {
            return convertAccrualTemplate(templates, (TmsAccrual) source, journalNumber);
        } else if (source instanceof TradeCorporateAction) {
            return convertCorporateActionTemplate(templates, (TradeCorporateAction) source, journalNumber);
        }
        throw new BusinessException("不支持的数据源类型");
    }

    /**
     * 调整凭证借贷方金额
     * 如果借方金额为负数，则将其转为贷方金额；如果贷方金额为负数，则将其转为借方金额
     * 确保借贷方金额始终为非负数
     *
     * @param journal 凭证对象
     */
    private void adjustJournalAmount(TmsCitJournal journal) {
        // 如果借方金额为负数
        if (journal.getEnteredDr() != null && journal.getEnteredDr().compareTo(BigDecimal.ZERO) < 0) {
            // 将贷方金额设为借方金额的相反数
            journal.setEnteredCr(journal.getEnteredDr().abs());
            // 借方金额置为0
            journal.setEnteredDr(BigDecimal.ZERO);
        }

        // 如果贷方金额为负数
        if (journal.getEnteredCr() != null && journal.getEnteredCr().compareTo(BigDecimal.ZERO) < 0) {
            // 将借方金额设为贷方金额的相反数
            journal.setEnteredDr(journal.getEnteredCr().abs());
            // 贷方金额置为0
            journal.setEnteredCr(BigDecimal.ZERO);
        }
    }

    /**
     * 转换交易记录模板为凭证
     * 根据交易记录的信息和凭证模板生成对应的凭证
     *
     * @param templates 凭证模板列表
     * @param tradeBlotter 交易记录
     * @param journalNumber 凭证编号
     * @return 凭证列表
     */
    private List<TmsCitJournal> convertTradeBlotterTemplate(List<TmsCitJournalTemp> templates, TmsTradeBlotter tradeBlotter, String journalNumber) {
        List<TmsCitJournal> journals = new ArrayList<>();

        // 检查模板数量是否为2且借贷方金额都为空
        boolean isTwoEmptyTemplates = templates.size() == 2 &&
                templates.stream().allMatch(t -> StringUtils.isEmpty(t.getEnteredDr()) && StringUtils.isEmpty(t.getEnteredCr()));

        for (int i = 0; i < templates.size(); i++) {
            TmsCitJournalTemp template = templates.get(i);
            TmsCitJournal journal = new TmsCitJournal();
            BeanUtils.copyProperties(template, journal);
            //重置创建时间和更新时间，创建人，更新人
            journal.setCreatedTime(LocalDateTime.now());
            journal.setUpdatedTime(LocalDateTime.now());
            journal.setCreatedBy(UserUtils.getUser().getUmNo());
            journal.setUpdatedBy(UserUtils.getUser().getUmNo());

            // 设置凭证编号
            journal.setJournalSequence(journalNumber);

            // 设置制证规则编号
            journal.setTmsPostingRuleId(template.getTempNo().toString());

            // 设置category_name
            journal.setCategoryName("1");

            // 设置数据源
            journal.setDataSource(DataSourceEnum.TMS_TRADE_BLOTTER.getCode());
            journal.setDataId(tradeBlotter.getTradeNumber());

            // 设置币种
            if (StringUtils.isEmpty(journal.getCurNo())) {
                journal.setCurNo(tradeBlotter.getCurrency());
            }

            // 设置凭证日期
            EffectiveDateEnum effectiveDate = EffectiveDateEnum.getByCode(template.getEffectiveDate().toUpperCase());
            if (effectiveDate != null) {
                switch (effectiveDate) {
                    case CURRENT_DATE:
                        journal.setEffectiveDate(LocalDate.now());
                        break;
                    case TRADE_DATE:
                        journal.setEffectiveDate(tradeBlotter.getTradeDate());
                        break;
                    case VALUE_DATE:
                        journal.setEffectiveDate(tradeBlotter.getValueDate());
                        break;
                    case MATURITY_DATE:
                        journal.setEffectiveDate(tradeBlotter.getMaturityDate());
                        break;
                }
            }

            // 设置金额
            if (isTwoEmptyTemplates) {
                // 处理两个空模板的情况
                BigDecimal settlementAmount = tradeBlotter.getSettlementAmount();
                if (settlementAmount != null) {
                    if (i == 0) {
                        // 第一条记录：借方金额为Settlement Amount，贷方金额为0
                        journal.setEnteredDr(settlementAmount);
                        journal.setEnteredCr(BigDecimal.ZERO);
                    } else {
                        // 第二条记录：借方金额为0，贷方金额为Settlement Amount
                        journal.setEnteredDr(BigDecimal.ZERO);
                        journal.setEnteredCr(settlementAmount);
                    }
                }
            } else {
                journal.setEnteredCr(BigDecimal.ZERO);
                journal.setEnteredDr(BigDecimal.ZERO);
                // 处理模板中有金额的情况
                if (StringUtils.hasText(template.getEnteredDr())) {
                    journal.setEnteredDr(calculateAmount(template.getEnteredDr(), tradeBlotter));
                }
                if (StringUtils.hasText(template.getEnteredCr())) {
                    journal.setEnteredCr(calculateAmount(template.getEnteredCr(), tradeBlotter));
                }
            }

            // 调整借贷方金额
            adjustJournalAmount(journal);

            // 设置凭证状态为待复核
            journal.setJournalStatus(Integer.parseInt(JournalStatusEnum.PENDING_REVIEW.getCode()));

            // 设置创建人和更新人
            String currentUser = UserUtils.getUser().getUmNo();
            journal.setCreatedBy(currentUser);
            journal.setUpdatedBy(currentUser);

            // 处理凭证摘要中的替换参数
            String description = template.getJournalLineDescription();
            if (StringUtils.hasText(description)) {
                description = replaceDescriptionParams(description, tradeBlotter);
                journal.setJournalLineDescription(description);
            }

            journals.add(journal);
        }

        return journals;
    }

    /**
     * 计算交易记录相关金额
     * 根据金额表达式和交易记录信息计算具体的金额
     * 支持多种金额类型：结算金额、面值金额、价格、比例、已计提利息、未分摊溢折价、已计提估值变动等
     *
     * @param expression 金额表达式
     * @param tradeBlotter 交易记录
     * @return 计算后的金额
     */
    private BigDecimal calculateAmount(String expression, TmsTradeBlotter tradeBlotter) {
        // 替换表达式中的特殊标记
        String processedExpression = expression;

        // 替换中文括号为英文括号，替换轧差：和英文冒号为空串
        processedExpression = processedExpression.replace("（", "(")
                .replace("）", ")")
                .replace("轧差：", "")
                .replace(":", "");

        // 替换Settlement Amount
        if (processedExpression.toUpperCase().contains(AmountTypeEnum.SETTLEMENT_AMOUNT.getCode())) {
            processedExpression = CaseInsensitiveReplace.replaceIgnoreCase(processedExpression, AmountTypeEnum.SETTLEMENT_AMOUNT.getCode(),
                    tradeBlotter.getSettlementAmount() != null ? tradeBlotter.getSettlementAmount().toString() : "0");
        }

        // 替换Face Amount
        if (processedExpression.toUpperCase().contains(AmountTypeEnum.FACE_AMOUNT.getCode())) {
            processedExpression = CaseInsensitiveReplace.replaceIgnoreCase(processedExpression, AmountTypeEnum.FACE_AMOUNT.getCode(),
                    tradeBlotter.getFaceAmount() != null ? tradeBlotter.getFaceAmount().toString() : "0");
        }

        // 替换Price
        if (processedExpression.toUpperCase().contains(AmountTypeEnum.PRICE.getCode())) {
            processedExpression = CaseInsensitiveReplace.replaceIgnoreCase(processedExpression, AmountTypeEnum.PRICE.getCode(),
                    tradeBlotter.getPrice() != null ? tradeBlotter.getPrice().toString() : "0");
        }

        // 计算比例
        if (processedExpression.contains(AmountTypeEnum.RATIO.getCode())) {
            BigDecimal buyAmount = calculateBuyAmount(tradeBlotter);
            BigDecimal sellAmount = calculateSellAmount(tradeBlotter);
            BigDecimal ratio = BigDecimal.ZERO;
            if (buyAmount != null && buyAmount.compareTo(BigDecimal.ZERO) != 0) {
                ratio = sellAmount.divide(buyAmount, 8, BigDecimal.ROUND_HALF_UP);
            }
            processedExpression = processedExpression.replace(AmountTypeEnum.RATIO.getCode(), ratio.toString());
        }

        // 替换已计提利息
        if (processedExpression.contains(AmountTypeEnum.ACCRUED_INTEREST.getCode())) {
            BigDecimal accruedInterest = getAccruedInterest(tradeBlotter);
            processedExpression = processedExpression.replace(AmountTypeEnum.ACCRUED_INTEREST.getCode(),
                    accruedInterest != null ? accruedInterest.toString() : "0");
        }

        // 替换未分摊溢折价
        if (processedExpression.contains(AmountTypeEnum.UNAMORTIZED_PREMIUM.getCode())) {
            BigDecimal unamortizedPremium = getUnamortizedPremium(tradeBlotter);
            processedExpression = processedExpression.replace(AmountTypeEnum.UNAMORTIZED_PREMIUM.getCode(),
                    unamortizedPremium != null ? unamortizedPremium.toString() : "0");
        }

        // 替换已计提估值变动
        if (processedExpression.contains(AmountTypeEnum.ACCRUED_VALUATION_CHANGE.getCode())) {
            BigDecimal valuationChange = getValuationChange(tradeBlotter);
            processedExpression = processedExpression.replace(AmountTypeEnum.ACCRUED_VALUATION_CHANGE.getCode(),
                    valuationChange != null ? valuationChange.toString() : "0");
        }

        try {
            return ExpressionCalculator.calculate(processedExpression);
        } catch (Exception e) {
            log.error("计算表达式失败: {}", processedExpression, e);
            return BigDecimal.ZERO;
        }
    }

    /**
     * 计算买入金额
     * 根据ISIN、币种、备注等信息汇总所有买入交易的金额
     *
     * @param tradeBlotter 交易记录
     * @return 买入金额
     */
    private BigDecimal calculateBuyAmount(TmsTradeBlotter tradeBlotter) {
        return tmsTradeBlotterMapper.selectList(
                        new LambdaQueryWrapper<TmsTradeBlotter>()
                                .eq(TmsTradeBlotter::getIsin, tradeBlotter.getIsin())
                                .eq(TmsTradeBlotter::getCurrency, tradeBlotter.getCurrency())
                                .eq(TmsTradeBlotter::getRemark, tradeBlotter.getRemark())
                                .eq(TmsTradeBlotter::getBuySellBorrowLend, "PAOB Buy")
                ).stream()
                .map(TmsTradeBlotter::getFaceAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 计算卖出金额
     * 根据ISIN、币种、备注等信息汇总所有卖出交易的金额
     *
     * @param tradeBlotter 交易记录
     * @return 卖出金额
     */
    private BigDecimal calculateSellAmount(TmsTradeBlotter tradeBlotter) {
        return tmsTradeBlotterMapper.selectList(
                        new LambdaQueryWrapper<TmsTradeBlotter>()
                                .eq(TmsTradeBlotter::getIsin, tradeBlotter.getIsin())
                                .eq(TmsTradeBlotter::getCurrency, tradeBlotter.getCurrency())
                                .eq(TmsTradeBlotter::getRemark, tradeBlotter.getRemark())
                                .eq(TmsTradeBlotter::getBuySellBorrowLend, "PAOB Sell")
                ).stream()
                .map(TmsTradeBlotter::getFaceAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 获取已计提利息
     * 根据ISIN、币种、备注等信息获取前一日的已计提利息金额
     *
     * @param tradeBlotter 交易记录
     * @return 已计提利息金额
     */
    private BigDecimal getAccruedInterest(TmsTradeBlotter tradeBlotter) {
        TmsAccrual accrual = tmsAccrualMapper.selectOne(
                new LambdaQueryWrapper<TmsAccrual>()
                        .eq(TmsAccrual::getIsin, tradeBlotter.getIsin())
                        .eq(TmsAccrual::getCurrency, tradeBlotter.getCurrency())
                        .eq(TmsAccrual::getRemark, tradeBlotter.getRemark())
                        .eq(TmsAccrual::getAccrualDate, tradeBlotter.getTradeDate().minusDays(1))
        );
        return accrual != null ? accrual.getAccruedInterest(): BigDecimal.ZERO;
    }

    /**
     * 获取未分摊溢折价
     * 根据ISIN、币种、备注等信息获取前一日的未分摊溢折价金额
     *
     * @param tradeBlotter 交易记录
     * @return 未分摊溢折价金额
     */
    private BigDecimal getUnamortizedPremium(TmsTradeBlotter tradeBlotter) {
        TmsAccrual accrual = tmsAccrualMapper.selectOne(
                new LambdaQueryWrapper<TmsAccrual>()
                        .eq(TmsAccrual::getIsin, tradeBlotter.getIsin())
                        .eq(TmsAccrual::getCurrency, tradeBlotter.getCurrency())
                        .eq(TmsAccrual::getRemark, tradeBlotter.getRemark())
                        .eq(TmsAccrual::getAccrualDate, tradeBlotter.getTradeDate().minusDays(1))
        );
        return accrual != null ? accrual.getNoAllocPremDisc(): BigDecimal.ZERO;
    }

    /**
     * 获取已计提估值变动
     * 根据ISIN、币种、备注等信息获取前一日的已计提估值变动金额
     *
     * @param tradeBlotter 交易记录
     * @return 已计提估值变动金额
     */
    private BigDecimal getValuationChange(TmsTradeBlotter tradeBlotter) {
        TmsAccrual accrual = tmsAccrualMapper.selectOne(
                new LambdaQueryWrapper<TmsAccrual>()
                        .eq(TmsAccrual::getIsin, tradeBlotter.getIsin())
                        .eq(TmsAccrual::getCurrency, tradeBlotter.getCurrency())
                        .eq(TmsAccrual::getRemark, tradeBlotter.getRemark())
                        .eq(TmsAccrual::getAccrualDate, tradeBlotter.getTradeDate().minusDays(1))
        );
        return accrual != null ? accrual.getAccruedValuationChange() : BigDecimal.ZERO;
    }

    /**
     * 转换计提记录模板为凭证
     * 根据计提记录的信息和凭证模板生成对应的凭证
     *
     * @param templates 凭证模板列表
     * @param accrual 计提记录
     * @param journalNumber 凭证编号
     * @return 凭证列表
     */
    private List<TmsCitJournal> convertAccrualTemplate(List<TmsCitJournalTemp> templates, TmsAccrual accrual, String journalNumber) {
        List<TmsCitJournal> journals = new ArrayList<>();

        // 检查模板数量是否为2且借贷方金额都为空
        boolean isTwoEmptyTemplates = templates.size() == 2 &&
                templates.stream().allMatch(t -> StringUtils.isEmpty(t.getEnteredDr()) && StringUtils.isEmpty(t.getEnteredCr()));

        for (int i = 0; i < templates.size(); i++) {
            TmsCitJournalTemp template = templates.get(i);
            TmsCitJournal journal = new TmsCitJournal();
            BeanUtils.copyProperties(template, journal);
            //重置创建时间和更新时间，创建人，更新人
            journal.setCreatedTime(LocalDateTime.now());
            journal.setUpdatedTime(LocalDateTime.now());
            journal.setCreatedBy(UserUtils.getUser().getUmNo());
            journal.setUpdatedBy(UserUtils.getUser().getUmNo());

            // 设置凭证编号
            journal.setJournalSequence(journalNumber);

            // 设置制证规则编号
            journal.setTmsPostingRuleId(template.getTempNo().toString());

            // 设置category_name
            journal.setCategoryName("1");

            // 设置数据源
            journal.setDataSource(DataSourceEnum.TMS_ACCRUAL.getCode());
            journal.setDataId(accrual.getId().toString());

            // 设置币种
            if (StringUtils.isEmpty(journal.getCurNo())) {
                journal.setCurNo(accrual.getCurrency());
            }

            // 设置凭证日期
            EffectiveDateEnum effectiveDate = EffectiveDateEnum.getByCode(template.getEffectiveDate().toUpperCase());
            if (effectiveDate != null) {
                switch (effectiveDate) {
                    case CURRENT_DATE:
                        journal.setEffectiveDate(LocalDate.now());
                        break;
                    case ACCRUAL_DATE:
                        journal.setEffectiveDate(accrual.getAccrualDate());
                        break;
                }
            }

            // 设置金额
            if (isTwoEmptyTemplates) {
                // 处理两个空模板的情况
                BigDecimal amount = getAccrualAmountByType(template.getTempType(), accrual);
                if (amount != null) {
                    if (i == 0) {
                        // 第一条记录：借方金额为计提金额，贷方金额为0
                        journal.setEnteredDr(amount);
                        journal.setEnteredCr(BigDecimal.ZERO);
                    } else {
                        // 第二条记录：借方金额为0，贷方金额为计提金额
                        journal.setEnteredDr(BigDecimal.ZERO);
                        journal.setEnteredCr(amount);
                    }
                }
            } else {
                journal.setEnteredCr(BigDecimal.ZERO);
                journal.setEnteredDr(BigDecimal.ZERO);
                // 处理模板中有金额的情况
                if (StringUtils.hasText(template.getEnteredDr())) {
                    journal.setEnteredDr(calculateAccrualAmount(template.getEnteredDr(), accrual));
                }
                if (StringUtils.hasText(template.getEnteredCr())) {
                    journal.setEnteredCr(calculateAccrualAmount(template.getEnteredCr(), accrual));
                }
            }

            // 调整借贷方金额
            adjustJournalAmount(journal);

            // 设置凭证状态为待复核
            journal.setJournalStatus(Integer.parseInt(JournalStatusEnum.PENDING_REVIEW.getCode()));

            // 设置创建人和更新人
            String currentUser = UserUtils.getUser().getUmNo();
            journal.setCreatedBy(currentUser);
            journal.setUpdatedBy(currentUser);

            // 处理凭证摘要中的替换参数
            String description = template.getJournalLineDescription();
            if (StringUtils.hasText(description)) {
                description = replaceDescriptionParams(description, accrual);
                journal.setJournalLineDescription(description);
            }

            journals.add(journal);
        }

        return journals;
    }

    /**
     * 根据模板类型获取计提金额
     * 根据不同的计提类型（计提利息收入/分摊溢折价/计提估值变动）获取对应的金额
     *
     * @param tempType 模板类型
     * @param accrual 计提记录
     * @return 计提金额
     */
    private BigDecimal getAccrualAmountByType(String tempType, TmsAccrual accrual) {
        AccrualTypeEnum accrualType = AccrualTypeEnum.getByCode(tempType);
        if (accrualType == null) {
            return BigDecimal.ZERO;
        }

        switch (accrualType) {
            case ACCRUAL_INTEREST:
                return accrual.getDailyAccruedInterest();
            case ALLOC_PREM_DISC:
                return accrual.getDailyAllocPremDisc();
            case ACCRUAL_VALUATION:
                return accrual.getDailyValuationChange();
            default:
                return BigDecimal.ZERO;
        }
    }

    /**
     * 计算计提记录相关金额
     * 根据金额表达式和计提记录信息计算具体的金额
     * 支持多种金额类型：当日计提利息、当日分摊溢折价、当日估值变动等
     *
     * @param expression 金额表达式
     * @param accrual 计提记录
     * @return 计算后的金额
     */
    private BigDecimal calculateAccrualAmount(String expression, TmsAccrual accrual) {
        // 替换表达式中的特殊标记
        String processedExpression = expression;

        // 替换中文括号为英文括号，替换轧差：和英文冒号为空串
        processedExpression = processedExpression.replace("（", "(")
                .replace("）", ")")
                .replace("轧差：", "")
                .replace(":", "");

        // 替换当日计提利息
        if (processedExpression.contains(AmountTypeEnum.DAILY_ACCRUED_INTEREST.getCode())) {
            processedExpression = processedExpression.replace(AmountTypeEnum.DAILY_ACCRUED_INTEREST.getCode(),
                    accrual.getDailyAccruedInterest() != null ? accrual.getDailyAccruedInterest().toString() : "0");
        }

        // 替换当日分摊溢折价
        if (processedExpression.contains(AmountTypeEnum.DAILY_ALLOC_PREM_DISC.getCode())) {
            processedExpression = processedExpression.replace(AmountTypeEnum.DAILY_ALLOC_PREM_DISC.getCode(),
                    accrual.getDailyAllocPremDisc() != null ? accrual.getDailyAllocPremDisc().toString() : "0");
        }

        // 替换当日估值变动
        if (processedExpression.contains(AmountTypeEnum.DAILY_VALUATION_CHANGE.getCode())) {
            processedExpression = processedExpression.replace(AmountTypeEnum.DAILY_VALUATION_CHANGE.getCode(),
                    accrual.getDailyValuationChange() != null ? accrual.getDailyValuationChange().toString() : "0");
        }

        try {
            return ExpressionCalculator.calculate(processedExpression);
        } catch (Exception e) {
            log.error("计算表达式失败: {}", processedExpression, e);
            return BigDecimal.ZERO;
        }
    }

    /**
     * 转换公司行为记录模板为凭证
     * 根据公司行为记录的信息和凭证模板生成对应的凭证
     *
     * @param templates 凭证模板列表
     * @param corporateAction 公司行为记录
     * @param journalNumber 凭证编号
     * @return 凭证列表
     */
    private List<TmsCitJournal> convertCorporateActionTemplate(List<TmsCitJournalTemp> templates, TradeCorporateAction corporateAction, String journalNumber) {
        List<TmsCitJournal> journals = new ArrayList<>();

        // 检查模板数量是否为2且借贷方金额都为空
        boolean isTwoEmptyTemplates = templates.size() == 2 &&
                templates.stream().allMatch(t -> StringUtils.isEmpty(t.getEnteredDr()) && StringUtils.isEmpty(t.getEnteredCr()));

        for (int i = 0; i < templates.size(); i++) {
            TmsCitJournalTemp template = templates.get(i);
            TmsCitJournal journal = new TmsCitJournal();
            BeanUtils.copyProperties(template, journal);
            //重置创建时间和更新时间，创建人，更新人
            journal.setCreatedTime(LocalDateTime.now());
            journal.setUpdatedTime(LocalDateTime.now());
            journal.setCreatedBy(UserUtils.getUser().getUmNo());
            journal.setUpdatedBy(UserUtils.getUser().getUmNo());


            // 设置凭证编号
            journal.setJournalSequence(journalNumber);

            // 设置制证规则编号
            journal.setTmsPostingRuleId(template.getTempNo().toString());

            // 设置category_name
            journal.setCategoryName("1");

            // 设置数据源
            journal.setDataSource(DataSourceEnum.TMS_CORPORATE_ACTION.getCode());
            journal.setDataId(corporateAction.getId().toString());


            // 设置币种
            if (StringUtils.isEmpty(journal.getCurNo())) {
                journal.setCurNo(corporateAction.getCurrency());
            }

            // 设置凭证日期
            EffectiveDateEnum effectiveDate = EffectiveDateEnum.getByCode(template.getEffectiveDate().toUpperCase());
            if (effectiveDate != null) {
                switch (effectiveDate) {
                    case CURRENT_DATE:
                        journal.setEffectiveDate(LocalDate.now());
                        break;
                    case SETTLEMENT_DATE:
                        journal.setEffectiveDate(corporateAction.getSettlementDate());
                        break;
                }
            }

            // 设置金额
            if (isTwoEmptyTemplates) {
                // 处理两个空模板的情况
                BigDecimal settlementAmount = corporateAction.getSettlementAmount();
                if (settlementAmount != null) {
                    if (i == 0) {
                        // 第一条记录：借方金额为Settlement Amount，贷方金额为0
                        journal.setEnteredDr(settlementAmount);
                        journal.setEnteredCr(BigDecimal.ZERO);
                    } else {
                        // 第二条记录：借方金额为0，贷方金额为Settlement Amount
                        journal.setEnteredDr(BigDecimal.ZERO);
                        journal.setEnteredCr(settlementAmount);
                    }
                }
            } else {
                journal.setEnteredCr(BigDecimal.ZERO);
                journal.setEnteredDr(BigDecimal.ZERO);
                // 处理模板中有金额的情况
                if (StringUtils.hasText(template.getEnteredDr())) {
                    journal.setEnteredDr(calculateCorporateActionAmount(template.getEnteredDr(), corporateAction));
                }
                if (StringUtils.hasText(template.getEnteredCr())) {
                    journal.setEnteredCr(calculateCorporateActionAmount(template.getEnteredCr(), corporateAction));
                }
            }

            // 调整借贷方金额
            adjustJournalAmount(journal);

            // 设置凭证状态为待复核
            journal.setJournalStatus(Integer.parseInt(JournalStatusEnum.PENDING_REVIEW.getCode()));

            // 设置创建人和更新人
            String currentUser = UserUtils.getUser().getUmNo();
            journal.setCreatedBy(currentUser);
            journal.setUpdatedBy(currentUser);

            // 处理凭证摘要中的替换参数
            String description = template.getJournalLineDescription();
            if (StringUtils.hasText(description)) {
                description = replaceDescriptionParams(description, corporateAction);
                journal.setJournalLineDescription(description);
            }

            journals.add(journal);
        }

        return journals;
    }

    /**
     * 计算公司行为记录相关金额
     * 根据金额表达式和公司行为记录信息计算具体的金额
     * 支持多种金额类型：已计提利息、结算金额、面值金额、已计提估值变动等
     *
     * @param expression 金额表达式
     * @param corporateAction 公司行为记录
     * @return 计算后的金额
     */
    private BigDecimal calculateCorporateActionAmount(String expression, TradeCorporateAction corporateAction) {
        // 替换表达式中的特殊标记
        String processedExpression = expression;

        // 替换中文括号为英文括号，替换轧差：和英文冒号为空串
        processedExpression = processedExpression.replace("（", "(")
                .replace("）", ")")
                .replace("轧差：", "")
                .replace(":", "");

        // 替换已计提利息
        if (processedExpression.contains(AmountTypeEnum.ACCRUED_INTEREST.getCode())) {
            processedExpression = processedExpression.replace(AmountTypeEnum.ACCRUED_INTEREST.getCode(),
                    corporateAction.getAccruedInterest() != null ? corporateAction.getAccruedInterest().toString() : "0");
        }

        // 替换Settlement Amount
        if (processedExpression.toUpperCase().contains(AmountTypeEnum.SETTLEMENT_AMOUNT.getCode())) {
            processedExpression = CaseInsensitiveReplace.replaceIgnoreCase(processedExpression, AmountTypeEnum.SETTLEMENT_AMOUNT.getCode(),
                    corporateAction.getSettlementAmount() != null ? corporateAction.getSettlementAmount().toString() : "0");

        }

        // 替换Face Amount
        if (processedExpression.toUpperCase().contains(AmountTypeEnum.FACE_AMOUNT.getCode())) {
            BigDecimal faceAmount = calculateFaceAmount(corporateAction);
            processedExpression = CaseInsensitiveReplace.replaceIgnoreCase(processedExpression, AmountTypeEnum.FACE_AMOUNT.getCode(),
                    faceAmount.toString());
        }

        // 替换已计提估值变动
        if (processedExpression.contains(AmountTypeEnum.ACCRUED_VALUATION_CHANGE.getCode())) {
            processedExpression = processedExpression.replace(AmountTypeEnum.ACCRUED_VALUATION_CHANGE.getCode(),
                    corporateAction.getAccruedValuationChange() != null ? corporateAction.getAccruedValuationChange().toString() : "0");
        }

        try {
            return ExpressionCalculator.calculate(processedExpression);
        } catch (Exception e) {
            log.error("计算表达式失败: {}", processedExpression, e);
            return BigDecimal.ZERO;
        }
    }

    /**
     * 计算面值金额
     * 根据公司行为记录关联的交易编号列表计算总面值金额
     *
     * @param corporateAction 公司行为记录
     * @return 面值金额
     */
    private BigDecimal calculateFaceAmount(TradeCorporateAction corporateAction) {
        if (StringUtils.isEmpty(corporateAction.getTradeNumbers())) {
            return BigDecimal.ZERO;
        }

        String[] tradeNumbers = corporateAction.getTradeNumbers().split(",");
        BigDecimal totalFaceAmount = BigDecimal.ZERO;

        for (String tradeNumber : tradeNumbers) {
            TmsTradeBlotter tradeBlotter = tmsTradeBlotterMapper.selectOne(
                    new LambdaQueryWrapper<TmsTradeBlotter>()
                            .eq(TmsTradeBlotter::getTradeNumber, tradeNumber.trim())
            );

            if (tradeBlotter != null && tradeBlotter.getFaceAmount() != null) {
                totalFaceAmount = totalFaceAmount.add(tradeBlotter.getFaceAmount());
            }
        }

        return totalFaceAmount;
    }

    /**
     * 生成凭证编号
     * 格式：J + 年月日 + 6位序号
     * @return 凭证编号
     */
    private String generateJournalNumber() {
        // 生成凭证编号格式：J + 年月日 + 6位序号
        String prefix = "J";
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String sequence = String.format("%06d", new Random().nextInt(1000000));
        return prefix + dateStr + sequence;
    }

    /**
     * 处理公司行为制证
     * @param businessId 业务ID（公司行为记录ID）
     * @return 批量创建凭证响应
     */
    private JournalBatchCreateResp handleCorporateActionJournal(String businessId) {
        List<String> journalIds = new ArrayList<>();
        List<String> journalNumbers = new ArrayList<>();
        // 查询公司行为记录
        TradeCorporateAction corporateAction = tradeCorporateActionMapper.selectById(Long.valueOf(businessId));
        if (corporateAction == null) {
            log.error("公司行为制证失败：记录不存在，businessId={}", businessId);
            return new JournalBatchCreateResp(journalIds, journalNumbers);
        }
        //如果公司行为已生成凭证，则不处理
        if (org.apache.commons.lang3.StringUtils.isNotBlank(corporateAction.getJournalSequence())) {
            log.info("公司行为已生成凭证，无需处理：businessId={}, journalStatus={}", businessId, corporateAction.getJournalStatus());
            return new JournalBatchCreateResp(journalIds, journalNumbers);
        }
        log.info("查询到公司行为记录：actionNo={}, actionType={}, remark={}",
                corporateAction.getActionNo(), corporateAction.getActionType(), corporateAction.getRemark());

        // 查询匹配的模板
        List<TmsCitJournalTemp> templates = tmsCitJournalTempMapper.selectList(
                new LambdaQueryWrapper<TmsCitJournalTemp>()
                        .eq(TmsCitJournalTemp::getTempType, ActionTypeEnum.getByCode(corporateAction.getActionType()).getDescription())
                        .eq(TmsCitJournalTemp::getRemark, corporateAction.getRemark())
        );

        if (CollectionUtils.isEmpty(templates)) {
            log.error("公司行为制证失败：未找到匹配的凭证模板，actionType={}, remark={}",
                    corporateAction.getActionType(), corporateAction.getRemark());
            return new JournalBatchCreateResp(journalIds, journalNumbers);
        }
        log.info("查询到匹配的凭证模板数量：{}", templates.size());

        // 用businessId关联tms_cit_journal表data_id字段删除对应的凭证记录，避免重复制证，添加data_source=TMS_CORPORATE_ACTION删除条件
        tmsCitJournalMapper.delete(new LambdaQueryWrapper<TmsCitJournal>()
                .eq(TmsCitJournal::getDataId, businessId)
                .eq(TmsCitJournal::getDataSource, DataSourceEnum.TMS_CORPORATE_ACTION));


        // 按模板编号分组
        Map<Long, List<TmsCitJournalTemp>> templateGroups = templates.stream()
                .collect(Collectors.groupingBy(TmsCitJournalTemp::getTempNo));
        log.info("模板分组数量：{}", templateGroups.size());



        // 遍历每个模板组
        for (Map.Entry<Long, List<TmsCitJournalTemp>> entry : templateGroups.entrySet()) {
            List<TmsCitJournalTemp> groupTemplates = entry.getValue();
            log.info("开始处理模板组：tempNo={}, 模板数量={}", entry.getKey(), groupTemplates.size());

            // 生成凭证编号
            String journalNumber = generateJournalNumber();
            log.info("生成凭证编号：{}", journalNumber);

            // 转换模板为凭证
            List<TmsCitJournal> journals = convertTemplateToJournal(groupTemplates, corporateAction, journalNumber);
            log.info("转换生成凭证数量：{}", journals.size());

            // 保存凭证
            for (TmsCitJournal journal : journals) {
                journal.setTmsCitJournalId(IdWorker.getIdStr());
                tmsCitJournalMapper.insert(journal);
                journalIds.add(journal.getTmsCitJournalId());
                log.info("保存凭证成功：journalId={}, journalSequence={}, enteredDr={}, enteredCr={}",
                        journal.getTmsCitJournalId(), journal.getJournalSequence(),
                        journal.getEnteredDr(), journal.getEnteredCr());
            }
            journalNumbers.add(journalNumber);
        }

        // 更新公司行为记录的凭证编号
        corporateAction.setJournalSequence(String.join(",", journalNumbers));
        corporateAction.setJournalStatus(JournalStatusEnum.PENDING_REVIEW.getCode());
        tradeCorporateActionMapper.updateById(corporateAction);
        log.info("更新公司行为记录状态成功：actionNo={}, journalSequence={}",
                corporateAction.getActionNo(), corporateAction.getJournalSequence());

        return new JournalBatchCreateResp(journalIds, journalNumbers);
    }

    /**
     * 替换凭证摘要中的参数
     * 根据不同的数据源类型（交易记录/计提记录/公司行为记录）调用相应的处理方法
     *
     * @param description 原始摘要
     * @param record 数据源对象（交易记录/计提记录/公司行为记录）
     * @return 替换后的摘要
     */
    private String replaceDescriptionParams(String description, Object record) {
        if (record instanceof TmsTradeBlotter) {
            description = dealTradeBlotterDescription(description, (TmsTradeBlotter) record);
        } else if (record instanceof TmsAccrual) {
            description = dealAccrualDescription(description, (TmsAccrual) record);
        } else if (record instanceof TradeCorporateAction) {
            description = dealCorporateActionDescription(description, (TradeCorporateAction) record);
        }
        return description;
    }

    /**
     * 处理公司行为记录凭证摘要
     * 替换摘要中的各种参数，包括：
     * 1. 币种：将HKD替换为HK$，CNH/CNY替换为RMB
     * 2. 结算金额：使用公司行为记录的结算金额
     * 3. 收益率：获取第一笔交易的收益率
     * 4. 起息日：获取第一笔交易的起息日并格式化为yyyyMMdd格式
     * 5. 到期日：获取第一笔交易的到期日并格式化为yyyyMMdd格式
     * 6. 面值金额：根据交易编号列表计算总面值金额
     * 7. ISIN：使用公司行为记录的ISIN
     *
     * @param description 原始摘要
     * @param record 公司行为记录
     * @return 替换后的摘要
     */
    private String dealCorporateActionDescription(String description, TradeCorporateAction record) {
        TradeCorporateAction corporateAction = record;
        // 替换币种
        if (description.toUpperCase().contains(DescriptionParamEnum.CURRENCY.getCode())) {
            String currency = corporateAction.getCurrency();
            if ("HKD".equals(currency)) {
                currency = "HK$";
            } else if ("CNH".equals(currency) || "CNY".equals(currency)) {
                currency = "RMB";
            }
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.CURRENCY.getCode(),
                    currency);
        }

        // 替换结算金额
        if (description.toUpperCase().contains(DescriptionParamEnum.SETTLEMENT_AMOUNT.getCode())) {
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.SETTLEMENT_AMOUNT.getCode(),
                    corporateAction.getSettlementAmount() != null ? corporateAction.getSettlementAmount().toString() : "0");
        }

        // 替换收益率
        if (description.toUpperCase().contains(DescriptionParamEnum.YIELD.getCode())) {
            BigDecimal yield = getFirstTradeYield(corporateAction.getTradeNumbers());
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.YIELD.getCode(),
                    yield != null ? yield.toString() : "0");
        }

        // 替换起息日
        if (description.toUpperCase().contains(DescriptionParamEnum.VALUE_DATE.getCode())) {
            LocalDate valueDate = getFirstTradeValueDate(corporateAction.getTradeNumbers());
            //参照上面maturity date处理
            String valueDateStr = "";
            if (valueDate != null) {
                valueDateStr = valueDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            }
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.VALUE_DATE.getCode(),
                    valueDateStr);
        }

        // 替换到期日
        if (description.toUpperCase().contains(DescriptionParamEnum.MATURITY_DATE.getCode())) {
            LocalDate maturityDate = getFirstTradeMaturityDate(corporateAction.getTradeNumbers());
            //参照上面maturity date处理
            String maturityDateStr = "";
            if (maturityDate != null) {
                maturityDateStr = maturityDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            }
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.MATURITY_DATE.getCode(),
                    maturityDateStr);
        }

        // 替换面值金额
        if (description.toUpperCase().contains(DescriptionParamEnum.FACE_AMOUNT.getCode())) {
            BigDecimal totalFaceAmount = calculateTotalFaceAmount(corporateAction.getTradeNumbers());
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.FACE_AMOUNT.getCode(),
                    totalFaceAmount != null ? totalFaceAmount.toString() : "0");
        }

        // 替换ISIN
        if (description.toUpperCase().contains(DescriptionParamEnum.ISIN.getCode())) {
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.ISIN.getCode(),
                    corporateAction.getIsin() != null ? corporateAction.getIsin() : "");
        }
        return description;
    }

    /**
     * 处理计提记录凭证摘要
     * 替换摘要中的各种参数，包括：
     * 1. 币种：将HKD替换为HK$，CNH/CNY替换为RMB
     * 2. 结算金额：根据交易编号计算总结算金额
     * 3. 收益率：获取第一笔交易的收益率
     * 4. 起息日：获取第一笔交易的起息日并格式化为yyyyMMdd格式
     * 5. 到期日：获取第一笔交易的到期日并格式化为yyyyMMdd格式
     * 6. 面值金额：根据交易编号计算总面值金额
     * 7. ISIN：使用计提记录的ISIN
     *
     * @param description 原始摘要
     * @param record 计提记录
     * @return 替换后的摘要
     */
    private String dealAccrualDescription(String description, TmsAccrual record) {
        TmsAccrual accrual = record;
        // 替换币种
        if (description.toUpperCase().contains(DescriptionParamEnum.CURRENCY.getCode())) {
            String currency = accrual.getCurrency();
            if ("HKD".equals(currency)) {
                currency = "HK$";
            } else if ("CNH".equals(currency) || "CNY".equals(currency)) {
                currency = "RMB";
            }
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.CURRENCY.getCode(),
                    currency);
        }

        // 替换结算金额
        if (description.toUpperCase().contains(DescriptionParamEnum.SETTLEMENT_AMOUNT.getCode())) {
            BigDecimal totalSettlementAmount = calculateTotalSettlementAmount(accrual.getTradeNumber());
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.SETTLEMENT_AMOUNT.getCode(),
                    totalSettlementAmount != null ? totalSettlementAmount.toString() : "0");
        }

        // 替换收益率
        if (description.toUpperCase().contains(DescriptionParamEnum.YIELD.getCode())) {
            BigDecimal yield = getFirstTradeYield(accrual.getTradeNumber());
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.YIELD.getCode(),
                    yield != null ? yield.toString() : "0");
        }

        // 替换起息日
        if (description.toUpperCase().contains(DescriptionParamEnum.VALUE_DATE.getCode())) {
            LocalDate valueDate = getFirstTradeValueDate(accrual.getTradeNumber());
            //将valueDate转换为字符串 YYYYMMDD 需先判断valueDate是否为空
            String valueDateStr = "";
            if (valueDate != null) {
                valueDateStr = valueDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            }
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.VALUE_DATE.getCode(),
                    valueDateStr);
        }

        // 替换到期日
        if (description.toUpperCase().contains(DescriptionParamEnum.MATURITY_DATE.getCode())) {
            LocalDate maturityDate = getFirstTradeMaturityDate(accrual.getTradeNumber());
            String maturityDateStr = "";
            if (maturityDate != null) {
                maturityDateStr = maturityDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            }
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.MATURITY_DATE.getCode(),
                    maturityDateStr);
        }

        // 替换面值金额
        if (description.toUpperCase().contains(DescriptionParamEnum.FACE_AMOUNT.getCode())) {
            BigDecimal totalFaceAmount = calculateTotalFaceAmount(accrual.getTradeNumber());
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.FACE_AMOUNT.getCode(),
                    totalFaceAmount != null ? totalFaceAmount.toString() : "0");
        }

        // 替换ISIN
        if (description.toUpperCase().contains(DescriptionParamEnum.ISIN.getCode())) {
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.ISIN.getCode(),
                    accrual.getIsin() != null ? accrual.getIsin() : "");
        }
        return description;
    }

    /**
     * 处理交易记录凭证摘要
     * 替换摘要中的各种参数，包括：
     * 1. 币种：将HKD替换为HK$，CNH/CNY替换为RMB
     * 2. 结算金额：使用交易记录的结算金额
     * 3. 收益率：使用交易记录的收益率
     * 4. 起息日：将日期格式化为yyyyMMdd格式
     * 5. 到期日：将日期格式化为yyyyMMdd格式
     * 6. 面值金额：使用交易记录的面值金额
     * 7. ISIN：使用交易记录的ISIN
     *
     * @param description 原始摘要
     * @param record 交易记录
     * @return 替换后的摘要
     */
    private String dealTradeBlotterDescription(String description, TmsTradeBlotter record) {
        TmsTradeBlotter tradeBlotter = record;
        // 替换币种
        if (description.toUpperCase().contains(DescriptionParamEnum.CURRENCY.getCode())) {
            String currency = tradeBlotter.getCurrency();
            if ("HKD".equals(currency)) {
                currency = "HK$";
            } else if ("CNH".equals(currency) || "CNY".equals(currency)) {
                currency = "RMB";
            }
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.CURRENCY.getCode(), currency);
        }

        // 替换结算金额
        if (description.toUpperCase().contains(DescriptionParamEnum.SETTLEMENT_AMOUNT.getCode())) {
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.SETTLEMENT_AMOUNT.getCode(),
                    tradeBlotter.getSettlementAmount() != null ? tradeBlotter.getSettlementAmount().toString() : "0");
        }

        // 替换收益率
        if (description.toUpperCase().contains(DescriptionParamEnum.YIELD.getCode())) {
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.YIELD.getCode(),
                    tradeBlotter.getYield() != null ? tradeBlotter.getYield().toString() : "0");
        }

        // 替换起息日
        if (description.toUpperCase().contains(DescriptionParamEnum.VALUE_DATE.getCode())) {
            //将valueDate转换为字符串 YYYYMMDD 需先判断valueDate是否为空
            String valueDate = "";
            if (tradeBlotter.getValueDate() != null) {
                valueDate = tradeBlotter.getValueDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            }
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.VALUE_DATE.getCode(),
                    valueDate);
        }

        // 替换到期日
        if (description.toUpperCase().contains(DescriptionParamEnum.MATURITY_DATE.getCode())) {
            String maturityDate = "";
            if (tradeBlotter.getMaturityDate() != null) {
                maturityDate = tradeBlotter.getMaturityDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            }
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.MATURITY_DATE.getCode(),
                    maturityDate);
        }

        // 替换面值金额
        if (description.toUpperCase().contains(DescriptionParamEnum.FACE_AMOUNT.getCode())) {
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.FACE_AMOUNT.getCode(),
                    tradeBlotter.getFaceAmount() != null ? tradeBlotter.getFaceAmount().toString() : "0");
        }

        // 替换ISIN
        if (description.toUpperCase().contains(DescriptionParamEnum.ISIN.getCode())) {
            description = CaseInsensitiveReplace.replaceIgnoreCase(description, DescriptionParamEnum.ISIN.getCode(),
                    tradeBlotter.getIsin() != null ? tradeBlotter.getIsin() : "");
        }
        return description;
    }

    /**
     * 计算总结算金额
     * 根据交易编号列表计算所有相关交易的总结算金额
     *
     * @param tradeNumbers 交易编号列表（逗号分隔）
     * @return 总结算金额
     */
    private BigDecimal calculateTotalSettlementAmount(String tradeNumbers) {
        if (StringUtils.isEmpty(tradeNumbers)) {
            return BigDecimal.ZERO;
        }

        return Arrays.stream(tradeNumbers.split(","))
                .map(String::trim)
                .map(tradeNumber -> tmsTradeBlotterMapper.selectOne(
                        new LambdaQueryWrapper<TmsTradeBlotter>()
                                .eq(TmsTradeBlotter::getTradeNumber, tradeNumber)
                ))
                .filter(Objects::nonNull)
                .map(TmsTradeBlotter::getSettlementAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 获取第一笔交易的起息日
     * 从交易编号列表中获取第一笔交易的起息日
     *
     * @param tradeNumbers 交易编号列表（逗号分隔）
     * @return 起息日
     */
    private LocalDate getFirstTradeValueDate(String tradeNumbers) {
        if (StringUtils.isEmpty(tradeNumbers)) {
            return null;
        }

        String firstTradeNumber = tradeNumbers.split(",")[0].trim();
        TmsTradeBlotter tradeBlotter = tmsTradeBlotterMapper.selectOne(
                new LambdaQueryWrapper<TmsTradeBlotter>()
                        .eq(TmsTradeBlotter::getTradeNumber, firstTradeNumber)
        );

        return tradeBlotter != null ? tradeBlotter.getValueDate() : null;
    }

    /**
     * 获取第一笔交易的收益率
     * 从交易编号列表中获取第一笔交易的收益率
     *
     * @param tradeNumbers 交易编号列表（逗号分隔）
     * @return 收益率
     */
    private BigDecimal getFirstTradeYield(String tradeNumbers) {
        if (StringUtils.isEmpty(tradeNumbers)) {
            return null;
        }

        String firstTradeNumber = tradeNumbers.split(",")[0].trim();
        TmsTradeBlotter tradeBlotter = tmsTradeBlotterMapper.selectOne(
                new LambdaQueryWrapper<TmsTradeBlotter>()
                        .eq(TmsTradeBlotter::getTradeNumber, firstTradeNumber)
        );

        return tradeBlotter != null ? tradeBlotter.getYield() : null;
    }

    /**
     * 获取第一笔交易的到期日
     * 从交易编号列表中获取第一笔交易的到期日
     *
     * @param tradeNumbers 交易编号列表（逗号分隔）
     * @return 到期日
     */
    private LocalDate getFirstTradeMaturityDate(String tradeNumbers) {
        if (StringUtils.isEmpty(tradeNumbers)) {
            return null;
        }

        String firstTradeNumber = tradeNumbers.split(",")[0].trim();
        TmsTradeBlotter tradeBlotter = tmsTradeBlotterMapper.selectOne(
                new LambdaQueryWrapper<TmsTradeBlotter>()
                        .eq(TmsTradeBlotter::getTradeNumber, firstTradeNumber)
        );

        return tradeBlotter != null ? tradeBlotter.getMaturityDate() : null;
    }

    /**
     * 计算总面值金额
     * 根据交易编号列表计算所有相关交易的总面值金额
     *
     * @param tradeNumbers 交易编号列表（逗号分隔）
     * @return 总面值金额
     */
    private BigDecimal calculateTotalFaceAmount(String tradeNumbers) {
        if (StringUtils.isEmpty(tradeNumbers)) {
            return BigDecimal.ZERO;
        }

        return Arrays.stream(tradeNumbers.split(","))
                .map(String::trim)
                .map(tradeNumber -> tmsTradeBlotterMapper.selectOne(
                        new LambdaQueryWrapper<TmsTradeBlotter>()
                                .eq(TmsTradeBlotter::getTradeNumber, tradeNumber)
                ))
                .filter(Objects::nonNull)
                .map(TmsTradeBlotter::getFaceAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<JournalDetailResp> initJournal(JournalInitReq req) {
        log.info("开始制证初始化，请求参数：dataSource={}, businessIds={}, tempName={}, tempType={}",
                req.getDataSource(), req.getBusinessIds(), req.getTempName(), req.getTempType());

        // 参数校验
        if (!StringUtils.hasText(req.getDataSource()) || CollectionUtils.isEmpty(req.getBusinessIds())) {
            log.error("制证初始化失败：数据源和业务ID列表不能为空");
            throw new BusinessException("数据源和业务ID列表不能为空");
        }

        List<JournalDetailResp> result = new ArrayList<>();

        try {
            DataSourceEnum dataSource = DataSourceEnum.getByCode(req.getDataSource());
            if (dataSource == null) {
                log.error("制证初始化失败：不支持的数据源类型，dataSource={}", req.getDataSource());
                throw new BusinessException("不支持的数据源类型");
            }

            // 遍历处理每个业务ID
            for (String businessId : req.getBusinessIds()) {
                List<TmsCitJournal> journals;
                switch (dataSource) {
                    case TMS_TRADE_BLOTTER:
                        journals = initTradeBlotterJournal(businessId, req.getTempName(), req.getTempType());
                        break;
                    case TMS_ACCRUAL:
                        log.info("开始处理计提记录初始化，businessId={}", businessId);
                        journals = initAccrualJournal(businessId, req.getTempName(), req.getTempType());
                        break;
                    case TMS_CORPORATE_ACTION:
                        log.info("开始处理公司行为初始化，businessId={}", businessId);
                        journals = initCorporateActionJournal(businessId, req.getTempName());
                        break;
                    default:
                        log.error("制证初始化失败：不支持的数据源类型，dataSource={}", req.getDataSource());
                        throw new BusinessException("不支持的数据源类型");
                }

                // 按凭证编号分组
                Map<String, List<TmsCitJournal>> journalGroupMap = journals.stream()
                        .collect(Collectors.groupingBy(TmsCitJournal::getJournalSequence));

                // 将分组后的凭证记录转换为 JournalDetailResp
                for (List<TmsCitJournal> journalGroup : journalGroupMap.values()) {
                    if (!journalGroup.isEmpty()) {
                        TmsCitJournal firstJournal = journalGroup.get(0);
                        JournalDetailResp detailResp = new JournalDetailResp();

                        // 设置基本信息
                        detailResp.setBooksNo(firstJournal.getBooksNo());
                        detailResp.setCurNo(firstJournal.getCurNo());
                        detailResp.setDataState(firstJournal.getDataState());
                        detailResp.setEffectiveDate(firstJournal.getEffectiveDate());
                        detailResp.setJournalSequence(firstJournal.getJournalSequence());
                        detailResp.setJournalStatus(firstJournal.getJournalStatus());
                        detailResp.setSegment1(firstJournal.getSegment1());

                        // 设置凭证明细列表
                        detailResp.setSegmentList(journalGroup);

                        result.add(detailResp);
                    }
                }
            }

            log.info("制证初始化成功，生成凭证数量：{}", result.size());
            return result;
        } catch (Exception e) {
            log.error("制证初始化失败：dataSource={}, businessIds={}, tempName={}, tempType={}, error={}",
                    req.getDataSource(), req.getBusinessIds(), req.getTempName(), req.getTempType(), e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 自动生成凭证
     */
    @Override
    public void autoGenerate() {
        //获取交易记录 tradeBlotterList  条件为（trade_date等于当天 或 trade_date小于当天）且凭证编号为空的记录
        log.info("开始自动生成凭证");
        processTradeBlotterForAutoGeneration();
        //参照上面代码实现逻辑，查询（trade_date等于当天 或 trade_date小于当天）且valueDateJournalStatus为未生成凭证的记录
        processTradeBlotterForAutoGenerationByValueDate();

        //获取计提记录 accrualList 计提利息凭证编号为空的记录
        processAccrualForAutoGenerationInterest();
        //获取计提记录 accrualList 分摊溢折价凭证编号为空的记录
        processAccrualForAutoGenerationAlloc();
        //获取计提记录 accrualList 估值变动凭证编号为空的记录
        processAccrualForAutoGenerationValuation();

        //查询公司行为记录表 获取公司行为记录 corporateActionList settlement_date小于等于当天，凭证编号为空的记录
        processAccrualForAutoGenerationCorporate();
        log.info("自动制证完成");
    }

    /**
     * 自动生成企业行为的账目
     * 该方法用于处理当天有结算日期且没有账目序列的企业行为，以及结算日期小于等于当天且没有账目序列的企业行为
     */
    private void processAccrualForAutoGenerationCorporate() {
        // 查询当天有结算日期且没有账目序列的企业行为
        List<TradeCorporateAction> corporateActionList = tradeCorporateActionMapper.selectList(
                new LambdaQueryWrapper<TradeCorporateAction>()
                        .eq(TradeCorporateAction::getSettlementDate, LocalDate.now())
                        .isNull(TradeCorporateAction::getJournalSequence)
        );

        // 查询结算日期小于等于当天且没有账目序列的企业行为
        List<TradeCorporateAction> corporateActionListmp = tradeCorporateActionMapper.selectList(
                new LambdaQueryWrapper<TradeCorporateAction>()
                        .le(TradeCorporateAction::getSettlementDate, LocalDate.now())
                        .isNull(TradeCorporateAction::getJournalSequence)
        );

        // 如果有结算日期小于等于当天且没有账目序列的企业行为，则合并到当天的企业行为列表中
        if (!corporateActionListmp.isEmpty()) {
            corporateActionList.addAll(corporateActionListmp);
        }

        // 如果存在需要处理的企业行为，则创建账目批次请求并调用批量创建账目方法
        if (!corporateActionList.isEmpty()) {
            // 组装JournalBatchCreateReq参数，
            JournalBatchCreateReq req = new JournalBatchCreateReq();
            req.setDataSource(DataSourceEnum.TMS_CORPORATE_ACTION.getCode());
            req.setBusinessIds(corporateActionList.stream().map(TradeCorporateAction::getId).map(String::valueOf).collect(Collectors.toList()));
            // 调用batchCreateJournal方法
            JournalBatchCreateResp journalBatchCreateResp = batchCreateJournal(req);
        }
    }

    /**
     * 自动生成估值凭证的方法
     * 该方法用于处理运费预提的自动记账功能，通过查询未生成估值凭证的预提记录并批量创建相应的会计凭证
     */
    private void processAccrualForAutoGenerationValuation() {
        // 查询未生成估值凭证的预提记录
        List<TmsAccrual> valuationAccrualList = tmsAccrualMapper.selectList(
                new LambdaQueryWrapper<TmsAccrual>()
                        .isNull(TmsAccrual::getValuationJournalNo)
        );
        // 如果存在未生成估值凭证的预提记录，则进行批量创建会计凭证的操作
        if (!valuationAccrualList.isEmpty()) {
            // 组装JournalBatchCreateReq参数，
            JournalBatchCreateReq req = new JournalBatchCreateReq();
            // 设置数据源为TMS预提
            req.setDataSource(DataSourceEnum.TMS_ACCRUAL.getCode());
            // 设置业务ID列表，即需要生成凭证的预提记录ID列表
            req.setBusinessIds(valuationAccrualList.stream().map(TmsAccrual::getId).map(String::valueOf).collect(Collectors.toList()));
            // 设置凭证模板类型为预提估值
            req.setTempType(ACCRUAL_VALUATION.getCode());
            // 调用批量创建会计凭证的接口，并获取响应结果
            JournalBatchCreateResp journalBatchCreateResp = batchCreateJournal(req);
            // 日志记录：估值凭证生成完成
            log.info("ACCRUAL_VALUATION 凭证生成完成");
        }
    }

    /**
     * 自动生成分配相关的准备金凭证
     * 此方法用于处理自动代币生成中的分配部分
     * 它首先查询所有未分配准备金折扣凭证的记录，
     * 然后批量生成相应的会计凭证
     */
    private void processAccrualForAutoGenerationAlloc() {
        // 查询所有未分配准备金折扣凭证的记录
        List<TmsAccrual> allocAccrualList = tmsAccrualMapper.selectList(
                new LambdaQueryWrapper<TmsAccrual>()
                        .isNull(TmsAccrual::getAllocPremDiscJournalNo)
        );
        // 如果查询结果不为空，则批量生成凭证
        if (!allocAccrualList.isEmpty()) {
            // 组装JournalBatchCreateReq参数，
            JournalBatchCreateReq req = new JournalBatchCreateReq();
            // 设置数据源为TMS准备金
            req.setDataSource(DataSourceEnum.TMS_ACCRUAL.getCode());
            // 设置业务ID列表，用于指定需要生成凭证的记录
            req.setBusinessIds(allocAccrualList.stream().map(TmsAccrual::getId).map(String::valueOf).collect(Collectors.toList()));
            // 设置模板类型为分配准备金折扣
            req.setTempType(ALLOC_PREM_DISC.getCode());
            // 调用批量创建凭证的接口，并获取响应
            JournalBatchCreateResp journalBatchCreateResp = batchCreateJournal(req);
            // 日志记录凭证生成完成
            log.info("ALLOC_PREM_DISC 凭证生成完成");
        }
    }

    /**
     * 自动生成利息的凭证处理方法
     * 此方法用于处理那些未关联利息凭证的计息记录，通过创建并提交日记账条目来生成凭证
     */
    private void processAccrualForAutoGenerationInterest() {
        // 查询所有未生成利息凭证的计息记录
        List<TmsAccrual> interestAccrualList = tmsAccrualMapper.selectList(
                new LambdaQueryWrapper<TmsAccrual>()
                        .isNull(TmsAccrual::getInterestJournalNo)
        );
        if (!interestAccrualList.isEmpty()) {
            // 组装JournalBatchCreateReq参数，
            JournalBatchCreateReq req = new JournalBatchCreateReq();
            // 设置数据源为TMS计息记录
            req.setDataSource(DataSourceEnum.TMS_ACCRUAL.getCode());
            // 设置业务ID列表，即需要生成凭证的计息记录ID列表
            req.setBusinessIds(interestAccrualList.stream().map(TmsAccrual::getId).map(String::valueOf).collect(Collectors.toList()));
            // 设置凭证模板类型为计息
            req.setTempType(ACCRUAL_INTEREST.getCode());
            // 批量创建日记账条目
            JournalBatchCreateResp journalBatchCreateResp = batchCreateJournal(req);
            // 日志记录凭证生成完成
            log.info("ACCRUAL_INTEREST 凭证生成完成");
        }
    }

    /**
     * 自动生成当天的交易日记账
     * 此方法查询所有当天或之前的价值日期且未生成日记账的交易记录，并为这些记录生成日记账
     */
    private void processTradeBlotterForAutoGenerationByValueDate() {
        // 查询当天价值日期且未生成日记账的交易记录
        List<TmsTradeBlotter> valueDateTradeBlotterList = tmsTradeBlotterMapper.selectList(
                new LambdaQueryWrapper<TmsTradeBlotter>()
                        .eq(TmsTradeBlotter::getValueDate, LocalDate.now())
                        .isNull(TmsTradeBlotter::getValueDateJournalSequence)
        );

        // 查询当天之前的价值日期且未生成日记账的交易记录
        List<TmsTradeBlotter> valueBlotterListmp = tmsTradeBlotterMapper.selectList(
                new LambdaQueryWrapper<TmsTradeBlotter>()
                        .lt(TmsTradeBlotter::getValueDate, LocalDate.now())
                        .isNull(TmsTradeBlotter::getValueDateJournalSequence)
        );

        // 将当天之前的交易记录添加到当天的交易记录列表中
        if (!valueBlotterListmp.isEmpty()) {
            valueDateTradeBlotterList.addAll(valueBlotterListmp);
        }

        // 如果存在未生成日记账的交易记录，则批量创建日记账
        if (!valueDateTradeBlotterList.isEmpty()) {
            // 组装JournalBatchCreateReq参数，
            JournalBatchCreateReq req = new JournalBatchCreateReq();
            req.setDataSource(DataSourceEnum.TMS_TRADE_BLOTTER.getCode());
            req.setBusinessIds(valueDateTradeBlotterList.stream().map(TmsTradeBlotter::getTradeNumber).collect(Collectors.toList()));
            req.setTempType(VALUE_DATE.getCode());
            // 执行批量创建日记账操作
            JournalBatchCreateResp journalBatchCreateResp = batchCreateJournal(req);
        }
    }


    /**
     * 自动生当天或历史交易日期的日记账凭证
     * 此方法会查询所有交易日期等于或早于当前日期且尚未生成凭证的交易记录，并为这些记录批量生成日记账凭证。
     */
    private void processTradeBlotterForAutoGeneration() {
        // 查询当天交易日期且未生成日记账的交易记录
        List<TmsTradeBlotter> tradeBlotterList = tmsTradeBlotterMapper.selectList(
                new LambdaQueryWrapper<TmsTradeBlotter>()
                        .eq(TmsTradeBlotter::getTradeDate, LocalDate.now())
                        .isNull(TmsTradeBlotter::getJournalSequence)
        );

        // 查询历史交易日期（早于当前日期）且未生成日记账的交易记录
        List<TmsTradeBlotter> tradeBlotterListmp = tmsTradeBlotterMapper.selectList(
                new LambdaQueryWrapper<TmsTradeBlotter>()
                        .lt(TmsTradeBlotter::getTradeDate, LocalDate.now())
                        .isNull(TmsTradeBlotter::getJournalSequence)
        );

        // 合并当天和历史交易记录
        if (!tradeBlotterListmp.isEmpty()) {
            tradeBlotterList.addAll(tradeBlotterListmp);
        }

        // 如果存在符合条件的交易记录，则批量创建日记账
        if (!tradeBlotterList.isEmpty()) {
            // 组装 JournalBatchCreateReq 请求参数
            JournalBatchCreateReq req = new JournalBatchCreateReq();
            req.setDataSource(DataSourceEnum.TMS_TRADE_BLOTTER.getCode());
            req.setBusinessIds(
                    tradeBlotterList.stream()
                            .map(TmsTradeBlotter::getTradeNumber)
                            .collect(Collectors.toList())
            );
            req.setTempType(TRADE_DATE.getCode());

            // 调用 batchCreateJournal 方法生成凭证
            JournalBatchCreateResp journalBatchCreateResp = batchCreateJournal(req);
        }
    }
    /**
     * 初始化交易记录凭证
     */
    private List<TmsCitJournal> initTradeBlotterJournal(String businessId, String tempName, String tempType) {
        // 获取交易记录
        TmsTradeBlotter tradeBlotter = tmsTradeBlotterMapper.selectOne(
                new LambdaQueryWrapper<TmsTradeBlotter>()
                        .eq(TmsTradeBlotter::getTradeNumber, businessId)
        );
        if (tradeBlotter == null) {
            throw new BusinessException("交易记录不存在");
        }

        // 查询匹配的模板
        LambdaQueryWrapper<TmsCitJournalTemp> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(tempName)) {
            // 如果传入了模板名称，优先使用模板名称查询
            queryWrapper.eq(TmsCitJournalTemp::getTempName, tempName);
        } else if (StringUtils.hasText(tempType)) {
            JournalTemplateTypeEnum templateTypeEnum = JournalTemplateTypeEnum.getByCode(tempType);
            // 如果传入了模板类型，使用模板类型查询
            String effectiveDate = null;
            switch (templateTypeEnum) {
                case TRADE_DATE:
                    effectiveDate = "Trade Date";
                    break;
                case VALUE_DATE:
                    effectiveDate = "Value Date";
                    break;
                default:
                    throw new BusinessException("不支持的交易记录模板类型：" + tempType);
            }
            queryWrapper.eq(TmsCitJournalTemp::getEffectiveDate, effectiveDate)
                    .eq(TmsCitJournalTemp::getTempType, tradeBlotter.getBuySellBorrowLend())
                    .eq(TmsCitJournalTemp::getRemark, tradeBlotter.getRemark());
        } else {
            // 否则使用原有的查询条件
            queryWrapper.eq(TmsCitJournalTemp::getTempType, tradeBlotter.getBuySellBorrowLend())
                    .eq(TmsCitJournalTemp::getRemark, tradeBlotter.getRemark());
        }

        List<TmsCitJournalTemp> templates = tmsCitJournalTempMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(templates)) {
            throw new BusinessException("未找到匹配的凭证模板");
        }

        // 按模板编号分组
        Map<Long, List<TmsCitJournalTemp>> templateGroups = templates.stream()
                .collect(Collectors.groupingBy(TmsCitJournalTemp::getTempNo));

        List<TmsCitJournal> allJournals = new ArrayList<>();
        for (Map.Entry<Long, List<TmsCitJournalTemp>> entry : templateGroups.entrySet()) {
            List<TmsCitJournalTemp> groupTemplates = entry.getValue();
            String journalNumber = generateJournalNumber();
            List<TmsCitJournal> journals = convertTemplateToJournal(groupTemplates, tradeBlotter, journalNumber);
            allJournals.addAll(journals);
        }

        return allJournals;
    }

    /**
     * 初始化计提记录凭证
     */
    private List<TmsCitJournal> initAccrualJournal(String businessId, String tempName, String tempType) {
        // 查询计提记录
        TmsAccrual accrual = tmsAccrualMapper.selectById(Long.valueOf(businessId));
        if (accrual == null) {
            throw new BusinessException("计提记录不存在");
        }

        List<TmsCitJournal> allJournals = new ArrayList<>();
        // 根据模板类型处理不同的计提记录
        if (StringUtils.hasText(tempType)) {
            JournalTemplateTypeEnum templateTypeEnum = JournalTemplateTypeEnum.getByCode(tempType);
            switch (templateTypeEnum) {
                case ACCRUAL_INTEREST:
                    allJournals.addAll(initAccrualInterest(accrual, tempName));
                    break;
                case ALLOC_PREM_DISC:
                    allJournals.addAll(initAccrualPremiumDiscount(accrual, tempName));
                    break;
                case ACCRUAL_VALUATION:
                    allJournals.addAll(initAccrualValuation(accrual, tempName));
                    break;
                default:
                    throw new BusinessException("不支持的计提记录模板类型：" + tempType);
            }
        } else {
            // 如果没有指定模板类型，处理所有类型的计提记录
            allJournals.addAll(initAccrualInterest(accrual, tempName));
            if (StringUtils.hasText(tempName)){
                return allJournals;
            }
            allJournals.addAll(initAccrualPremiumDiscount(accrual, tempName));
            allJournals.addAll(initAccrualValuation(accrual, tempName));
        }
        return allJournals;
    }

    /**
     * 初始化公司行为凭证
     */
    private List<TmsCitJournal> initCorporateActionJournal(String businessId, String tempName) {
        // 查询公司行为记录
        TradeCorporateAction corporateAction = tradeCorporateActionMapper.selectById(Long.valueOf(businessId));
        if (corporateAction == null) {
            throw new BusinessException("公司行为记录不存在");
        }

        // 查询匹配的模板
        LambdaQueryWrapper<TmsCitJournalTemp> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(tempName)) {
            // 如果传入了模板名称，优先使用模板名称查询
            queryWrapper.eq(TmsCitJournalTemp::getTempName, tempName);
        } else {
            // 否则使用原有的查询条件
            queryWrapper.eq(TmsCitJournalTemp::getTempType, ActionTypeEnum.getByCode(corporateAction.getActionType()).getDescription())
                    .eq(TmsCitJournalTemp::getRemark, corporateAction.getRemark());
        }

        List<TmsCitJournalTemp> templates = tmsCitJournalTempMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(templates)) {
            throw new BusinessException("未找到匹配的凭证模板");
        }

        String journalNumber = generateJournalNumber();
        return convertTemplateToJournal(templates, corporateAction, journalNumber);
    }

    /**
     * 初始化计提利息收入凭证
     */
    private List<TmsCitJournal> initAccrualInterest(TmsAccrual accrual, String tempName) {
        LambdaQueryWrapper<TmsCitJournalTemp> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(tempName)) {
            queryWrapper.eq(TmsCitJournalTemp::getTempName, tempName);
        }  else {
            queryWrapper.eq(TmsCitJournalTemp::getTempType, ACCRUAL_INTEREST.getCode())
                    .eq(TmsCitJournalTemp::getRemark, accrual.getRemark());
        }
        List<TmsCitJournalTemp> templates = tmsCitJournalTempMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(templates)) {
            throw new BusinessException("未找到匹配的凭证模板");
        }
        String journalNumber = generateJournalNumber();
        return convertTemplateToJournal(templates, accrual, journalNumber);
    }

    /**
     * 初始化分摊溢折价凭证
     */
    private List<TmsCitJournal> initAccrualPremiumDiscount(TmsAccrual accrual, String tempName) {
        LambdaQueryWrapper<TmsCitJournalTemp> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(tempName)) {
            queryWrapper.eq(TmsCitJournalTemp::getTempName, tempName);
        }  else {
            queryWrapper.eq(TmsCitJournalTemp::getTempType, ALLOC_PREM_DISC.getCode())
                    .eq(TmsCitJournalTemp::getRemark, accrual.getRemark());
        }
        List<TmsCitJournalTemp> templates = tmsCitJournalTempMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(templates)) {
            throw new BusinessException("未找到匹配的凭证模板");
        }
        String journalNumber = generateJournalNumber();
        return convertTemplateToJournal(templates, accrual, journalNumber);
    }

    /**
     * 初始化计提估值变动凭证
     */
    private List<TmsCitJournal> initAccrualValuation(TmsAccrual accrual, String tempName) {
        LambdaQueryWrapper<TmsCitJournalTemp> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(tempName)) {
            queryWrapper.eq(TmsCitJournalTemp::getTempName, tempName);
        } else {
            queryWrapper.eq(TmsCitJournalTemp::getTempType, ACCRUAL_VALUATION.getCode())
                    .eq(TmsCitJournalTemp::getRemark, accrual.getRemark());
        }
        List<TmsCitJournalTemp> templates = tmsCitJournalTempMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(templates)) {
            throw new BusinessException("未找到匹配的凭证模板");
        }
        String journalNumber = generateJournalNumber();
        return convertTemplateToJournal(templates, accrual, journalNumber);
    }

}