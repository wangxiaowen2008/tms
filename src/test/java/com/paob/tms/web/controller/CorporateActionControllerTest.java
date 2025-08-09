package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paob.tms.dto.req.CorporateActionQueryReq;
import com.paob.tms.dto.resp.CorporateActionResp;
import com.paob.tms.service.CorporateActionService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CorporateActionControllerTest {

    @Mock
    private CorporateActionService corporateActionService;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private CorporateActionController corporateActionController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(corporateActionController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void queryList_ShouldReturnList() throws Exception {
        // 准备测试数据
        CorporateActionQueryReq req = new CorporateActionQueryReq();
        Page<CorporateActionResp> page = new Page<>();
        page.setRecords(Arrays.asList(new CorporateActionResp()));
        page.setTotal(1);

        // 模拟service行为
        when(corporateActionService.queryList(any())).thenReturn(page);

        // 执行测试
        mockMvc.perform(get("/api/corporate-action/list")
                .param("remark", "EFB"))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(corporateActionService).queryList(any());
    }

    @Test
    void exportList_ShouldSucceed() throws Exception {
        // 准备测试数据
        CorporateActionQueryReq req = new CorporateActionQueryReq();

        // 执行测试
        mockMvc.perform(get("/api/corporate-action/export")
                .param("remark", "EFB"))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(corporateActionService).exportList(any(), any());
    }

    @Test
    void batchVoucher_ShouldSucceed() throws Exception {
        // 准备测试数据
        List<String> actionNos = Arrays.asList("EFB00001", "EFB00002");
        when(corporateActionService.batchVoucher(any())).thenReturn("success");

        // 执行测试
        mockMvc.perform(post("/api/corporate-action/batch-voucher")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(actionNos)))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(corporateActionService).batchVoucher(any());
    }

    @Test
    void getDetail_ShouldReturnDetail() throws Exception {
        // 准备测试数据
        String actionNo = "EFB00001";
        CorporateActionResp resp = new CorporateActionResp();
        when(corporateActionService.getDetail(any())).thenReturn(resp);

        // 执行测试
        mockMvc.perform(get("/api/corporate-action/detail/{actionNo}", actionNo))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(corporateActionService).getDetail(actionNo);
    }

    @Test
    void calculateCorporateAction_ShouldSucceed() throws Exception {
        // 执行测试
        mockMvc.perform(get("/api/corporate-action/calculate"))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(corporateActionService).calculateCorporateAction();
    }
} 