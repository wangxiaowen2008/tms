package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paob.tms.dto.req.BankAccountBalanceQueryReq;
import com.paob.tms.dto.resp.BankAccountBalanceResp;
import com.paob.tms.service.BankAccountBalanceService;
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

class BankAccountBalanceControllerTest {

    @Mock
    private BankAccountBalanceService bankAccountBalanceService;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private BankAccountBalanceController bankAccountBalanceController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bankAccountBalanceController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void list_ShouldReturnList() throws Exception {
        // 准备测试数据
        BankAccountBalanceQueryReq query = new BankAccountBalanceQueryReq();
        Page<BankAccountBalanceResp> page = new Page<>();
        page.setRecords(Arrays.asList(new BankAccountBalanceResp()));
        page.setTotal(1);

        // 模拟service行为
        when(bankAccountBalanceService.queryBankAccountBalancePage(any())).thenReturn(page);

        // 执行测试
        mockMvc.perform(get("/api/bank-account-balance/list")
                .param("currency", "USD"))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(bankAccountBalanceService).queryBankAccountBalancePage(any());
    }

    @Test
    void export_ShouldSucceed() throws Exception {
        // 准备测试数据
        BankAccountBalanceQueryReq query = new BankAccountBalanceQueryReq();

        // 执行测试
        mockMvc.perform(get("/api/bank-account-balance/export")
                .param("currency", "USD"))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(bankAccountBalanceService).exportBankAccountBalance(any(), any());
    }
} 