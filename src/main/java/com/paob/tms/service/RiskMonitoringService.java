package com.paob.tms.service;

import com.paob.tms.model.RiskMonitoring;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RiskMonitoringService {
    
    /**
     * 上传风险监控文件
     * @param file Excel文件
     * @return 处理结果信息
     */
    String uploadRiskMonitoringFile(MultipartFile file) throws IOException;
    
    /**
     * 批量保存风险监控数据
     * @param list 风险监控数据列表
     */
    void batchSave(List<RiskMonitoring> list);
    
    /**
     * 根据ISIN和报告日期查询
     */
    RiskMonitoring getByIsinAndReportingDate(String isin, String reportingDate);
} 