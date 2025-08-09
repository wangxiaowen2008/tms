package com.paob.tms.service;

import com.paob.tms.model.RiskMonitoring;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RiskMonitoringServiceTest {

    @InjectMocks
    private RiskMonitoringService riskMonitoringService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("上传风险监控文件 - 正常场景")
    void uploadRiskMonitoringFile_ShouldUploadSuccessfully() throws IOException {
        // Arrange
        MultipartFile file = new MockMultipartFile(
            "file",
            "test.xlsx",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "test data".getBytes()
        );
        
        // Act
        String result = riskMonitoringService.uploadRiskMonitoringFile(file);
        
        // Assert
        assertNotNull(result);
        verify(riskMonitoringService, times(1)).uploadRiskMonitoringFile(file);
    }

    @Test
    @DisplayName("上传风险监控文件 - 空文件")
    void uploadRiskMonitoringFile_WithEmptyFile_ShouldThrowException() {
        // Arrange
        MultipartFile file = new MockMultipartFile(
            "file",
            "test.xlsx",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            new byte[0]
        );
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> riskMonitoringService.uploadRiskMonitoringFile(file));
    }

    @Test
    @DisplayName("上传风险监控文件 - 不支持的文件类型")
    void uploadRiskMonitoringFile_WithUnsupportedFileType_ShouldThrowException() {
        // Arrange
        MultipartFile file = new MockMultipartFile(
            "file",
            "test.txt",
            "text/plain",
            "test data".getBytes()
        );
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> riskMonitoringService.uploadRiskMonitoringFile(file));
    }

    @Test
    @DisplayName("批量保存风险监控数据 - 正常场景")
    void batchSave_ShouldSaveSuccessfully() {
        // Arrange
        List<RiskMonitoring> list = Arrays.asList(
            new RiskMonitoring(),
            new RiskMonitoring()
        );
        
        // Act & Assert
        assertDoesNotThrow(() -> riskMonitoringService.batchSave(list));
        verify(riskMonitoringService, times(1)).batchSave(list);
    }

    @Test
    @DisplayName("批量保存风险监控数据 - 空列表")
    void batchSave_WithEmptyList_ShouldNotThrowException() {
        // Arrange
        List<RiskMonitoring> list = Collections.emptyList();
        
        // Act & Assert
        assertDoesNotThrow(() -> riskMonitoringService.batchSave(list));
    }

    @Test
    @DisplayName("根据ISIN和报告日期查询 - 正常场景")
    void getByIsinAndReportingDate_ShouldReturnMonitoring() {
        // Arrange
        String isin = "TEST001";
        String reportingDate = "2024-03-20";
        
        // Act
        RiskMonitoring result = riskMonitoringService.getByIsinAndReportingDate(isin, reportingDate);
        
        // Assert
        assertNotNull(result);
        verify(riskMonitoringService, times(1)).getByIsinAndReportingDate(isin, reportingDate);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("根据ISIN和报告日期查询 - 无效ISIN")
    void getByIsinAndReportingDate_WithInvalidIsin_ShouldReturnNull(String isin) {
        // Arrange
        String reportingDate = "2024-03-20";
        
        // Act
        RiskMonitoring result = riskMonitoringService.getByIsinAndReportingDate(isin, reportingDate);
        
        // Assert
        assertNull(result);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("根据ISIN和报告日期查询 - 无效报告日期")
    void getByIsinAndReportingDate_WithInvalidReportingDate_ShouldReturnNull(String reportingDate) {
        // Arrange
        String isin = "TEST001";
        
        // Act
        RiskMonitoring result = riskMonitoringService.getByIsinAndReportingDate(isin, reportingDate);
        
        // Assert
        assertNull(result);
    }
} 