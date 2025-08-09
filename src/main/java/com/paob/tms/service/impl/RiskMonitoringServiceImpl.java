package com.paob.tms.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paob.tms.mapper.RiskMonitoringMapper;
import com.paob.tms.mapper.TradeBlotterMapper;
import com.paob.tms.model.RiskMonitoring;
import com.paob.tms.service.RiskMonitoringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class RiskMonitoringServiceImpl extends ServiceImpl<RiskMonitoringMapper, RiskMonitoring> implements RiskMonitoringService {

    @Autowired
    private RiskMonitoringMapper riskMonitoringMapper;
    
    @Autowired
    private TradeBlotterMapper tradeBlotterMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String uploadRiskMonitoringFile(MultipartFile file) throws IOException {
        // 1. 文件格式校验
        String fileName = file.getOriginalFilename();
        if (fileName == null || (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls") && !fileName.endsWith(".xlsm"))) {
            throw new IllegalArgumentException("文件格式不正确，仅支持xlsx、xls、xlsm格式");
        }

        List<RiskMonitoring> dataList = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();
        LocalDate reportingDate = null;

        // 2. 读取Excel数据
        EasyExcel.read(file.getInputStream(), RiskMonitoring.class, new PageReadListener<RiskMonitoring>(dataListBatch -> {
            for (RiskMonitoring data : dataListBatch) {
                try {
                    // 数据校验
                    validateData(data);
                    dataList.add(data);
                } catch (Exception e) {
                    errorMessages.add("第" + (dataList.size() + 1) + "行数据错误: " + e.getMessage());
                }
            }
        })).sheet().doRead();

        if (!dataList.isEmpty()) {
            reportingDate = dataList.get(0).getReportingDate();
        }

        // 3. 保存数据
        if (!dataList.isEmpty()) {
            // 设置创建信息
            String currentUser = "admin"; // TODO: 从当前登录用户获取
            LocalDateTime now = LocalDateTime.now();
            dataList.forEach(data -> {
                data.setCreatedBy(currentUser);
                data.setCreatedTime(now);
                data.setUpdatedBy(currentUser);
                data.setUpdatedTime(now);
            });

            // 批量保存
            riskMonitoringMapper.batchInsert(dataList);
        }

        // 4. 数据核对
        StringBuilder resultMessage = new StringBuilder();
        resultMessage.append("《risk monitoring》表导入").append(dataList.size()).append("条估值信息");

        if (reportingDate != null) {
            // 查询缺少的ISIN
            List<String> missingIsins = riskMonitoringMapper.selectMissingIsins(reportingDate);
            if (!missingIsins.isEmpty()) {
                resultMessage.append(",与交易数据核对缺少").append(missingIsins.size())
                        .append("条估值（").append(String.join(",", missingIsins)).append("）");
            }

            // 查询多余的ISIN
            List<String> extraIsins = riskMonitoringMapper.selectExtraIsins(reportingDate);
            if (!extraIsins.isEmpty()) {
                resultMessage.append(",与交易数据核对有多余").append(extraIsins.size())
                        .append("条估值（").append(String.join(",", extraIsins)).append("）");
            }
        }

        // 5. 异步启动计提数据生成
        if (!dataList.isEmpty()) {
            CompletableFuture.runAsync(() -> {
                try {
                    generateAccrualData(dataList);
                } catch (Exception e) {
                    log.error("生成计提数据失败", e);
                }
            });
        }

        return resultMessage.toString();
    }

    @Override
    public void batchSave(List<RiskMonitoring> list) {
        if (list != null && !list.isEmpty()) {
            riskMonitoringMapper.batchInsert(list);
        }
    }

    @Override
    public RiskMonitoring getByIsinAndReportingDate(String isin, String reportingDate) {
        LocalDate date = LocalDate.parse(reportingDate, DateTimeFormatter.ISO_DATE);
        return riskMonitoringMapper.selectByIsinAndReportingDate(isin, date);
    }

    private void validateData(RiskMonitoring data) {
        if (data.getReportingDate() == null) {
            throw new IllegalArgumentException("报告日期不能为空");
        }
        if (data.getIsin() == null || data.getIsin().trim().isEmpty()) {
            throw new IllegalArgumentException("ISIN不能为空");
        }
        // 其他字段校验...
    }

    private void generateAccrualData(List<RiskMonitoring> dataList) {
        // TODO: 实现计提数据生成逻辑
    }
} 