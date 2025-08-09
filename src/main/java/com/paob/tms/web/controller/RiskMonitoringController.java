package com.paob.tms.web.controller;

import com.paob.tms.service.RiskMonitoringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/risk/monitoring")
@Tag(name = "风险监控管理", description = "风险监控相关接口")
public class RiskMonitoringController {

    @Autowired
    private RiskMonitoringService riskMonitoringService;

    @PostMapping("/upload")
    @Operation(summary = "上传风险监控文件")
    public String uploadRiskMonitoringFile(@RequestParam("file") MultipartFile file) {
        try {
            return riskMonitoringService.uploadRiskMonitoringFile(file);
        } catch (IOException e) {
            log.error("上传风险监控文件失败", e);
            return "上传失败，请检查是否网络不稳定原因，并请重试！";
        }
    }
} 