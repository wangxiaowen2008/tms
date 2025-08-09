package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paob.tms.dto.req.AccrualQueryReq;
import com.paob.tms.dto.resp.AccrualResp;
import com.paob.tms.service.AccrualService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccrualControllerTest {

    @Mock
    private AccrualService accrualService;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private AccrualController accrualController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accrualController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void queryList_ShouldReturnList() throws Exception {
        // 准备测试数据
        AccrualQueryReq req = new AccrualQueryReq();
        Page<AccrualResp> page = new Page<>();
        page.setRecords(Arrays.asList(new AccrualResp()));
        page.setTotal(1);

        // 模拟service行为
        when(accrualService.queryPage(any())).thenReturn(page);

        // 执行测试
        mockMvc.perform(get("/api/accrual/list")
                .param("remark", "EFB"))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(accrualService).queryPage(any());
    }

    @Test
    void export_ShouldSucceed() throws Exception {
        // 准备测试数据
        AccrualQueryReq req = new AccrualQueryReq();

        // 执行测试
        mockMvc.perform(get("/api/accrual/export")
                .param("remark", "EFB"))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(accrualService).export(any(), any());
    }
} 