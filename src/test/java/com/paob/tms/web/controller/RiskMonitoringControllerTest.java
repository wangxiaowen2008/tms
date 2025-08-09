package com.paob.tms.web.controller;

import com.paob.tms.service.RiskMonitoringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RiskMonitoringControllerTest {

    @Mock
    private RiskMonitoringService riskMonitoringService;

    @InjectMocks
    private RiskMonitoringController riskMonitoringController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(riskMonitoringController).build();
    }

    @Test
    void uploadRiskMonitoringFile_ShouldSucceed() throws Exception {
        // 准备测试数据
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.xlsx",
                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                "test data".getBytes()
        );
        String expectedResponse = "上传成功";
        when(riskMonitoringService.uploadRiskMonitoringFile(any())).thenReturn(expectedResponse);

        // 执行测试
        mockMvc.perform(multipart("/api/risk/monitoring/upload")
                .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

        // 验证service方法被调用
        verify(riskMonitoringService).uploadRiskMonitoringFile(any());
    }

    @Test
    void uploadRiskMonitoringFile_ShouldHandleIOException() throws Exception {
        // 准备测试数据
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.xlsx",
                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                "test data".getBytes()
        );
        when(riskMonitoringService.uploadRiskMonitoringFile(any())).thenThrow(new IOException("测试异常"));

        // 执行测试
        mockMvc.perform(multipart("/api/risk/monitoring/upload")
                .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("上传失败，请检查是否网络不稳定原因，并请重试！"));

        // 验证service方法被调用
        verify(riskMonitoringService).uploadRiskMonitoringFile(any());
    }
} 