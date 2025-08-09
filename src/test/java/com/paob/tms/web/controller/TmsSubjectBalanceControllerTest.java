package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.paob.tms.dto.req.SubjectBalanceQueryReq;
import com.paob.tms.model.TmsSubjectBalance;
import com.paob.tms.service.TmsSubjectBalanceService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TmsSubjectBalanceControllerTest {

    @Mock
    private TmsSubjectBalanceService tmsSubjectBalanceService;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private TmsSubjectBalanceController tmsSubjectBalanceController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tmsSubjectBalanceController).build();
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Test
    void list_ShouldReturnSubjectBalanceList() throws Exception {
        // 准备测试数据
        SubjectBalanceQueryReq req = new SubjectBalanceQueryReq();
        Page<TmsSubjectBalance> page = new Page<>();
        page.setRecords(Arrays.asList(new TmsSubjectBalance()));
        page.setTotal(1);

        // 模拟service行为
        when(tmsSubjectBalanceService.querySubjectBalancePage(any())).thenReturn(page);

        // 执行测试
        mockMvc.perform(get("/api/subject-balance/list")
                .param("subjectCode", "TEST001"))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(tmsSubjectBalanceService).querySubjectBalancePage(any());
    }

    @Test
    void export_ShouldSucceed() throws Exception {
        // 准备测试数据
        SubjectBalanceQueryReq req = new SubjectBalanceQueryReq();

        // 执行测试
        mockMvc.perform(get("/api/subject-balance/export")
                .param("subjectCode", "TEST001"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/octet-stream"));

        // 验证service方法被调用
        verify(tmsSubjectBalanceService).exportSubjectBalance(any(), any());
    }

    @Test
    void getDetail_ShouldReturnDetail() throws Exception {
        // 准备测试数据
        String subjectCode = "TEST001";
        TmsSubjectBalance expectedResp = new TmsSubjectBalance();
        when(tmsSubjectBalanceService.getSubjectBalanceDetail(subjectCode)).thenReturn(expectedResp);

        // 执行测试
        mockMvc.perform(get("/api/subject-balance/detail/{subjectCode}", subjectCode))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(tmsSubjectBalanceService).getSubjectBalanceDetail(subjectCode);
    }
} 