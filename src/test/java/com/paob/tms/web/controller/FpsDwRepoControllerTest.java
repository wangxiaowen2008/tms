package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.FpsDwRepoQueryReq;
import com.paob.tms.dto.req.FpsDwRepoMakeVoucherReq;
import com.paob.tms.dto.resp.FpsDwRepoResp;
import com.paob.tms.service.FpsDwRepoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FpsDwRepoControllerTest {

    @Mock
    private FpsDwRepoService fpsDwRepoService;

    @InjectMocks
    private FpsDwRepoController fpsDwRepoController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(fpsDwRepoController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void queryList_ShouldReturnList() throws Exception {
        // 准备测试数据
        FpsDwRepoQueryReq req = new FpsDwRepoQueryReq();
        Page<FpsDwRepoResp> page = new Page<>();
        page.setRecords(Arrays.asList(new FpsDwRepoResp()));
        page.setTotal(1);

        // 模拟service行为
        when(fpsDwRepoService.queryList(any())).thenReturn(page);

        // 执行测试
        mockMvc.perform(get("/api/fps-dw-repo/list")
                .param("tradeNumber", "TEST001"))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(fpsDwRepoService).queryList(any());
    }

    @Test
    void getDetail_ShouldReturnDetail() throws Exception {
        // 准备测试数据
        String tradeNumber = "TEST001";
        FpsDwRepoResp expectedResp = new FpsDwRepoResp();
        when(fpsDwRepoService.getDetail(tradeNumber)).thenReturn(expectedResp);

        // 执行测试
        mockMvc.perform(get("/api/fps-dw-repo/detail/{tradeNumber}", tradeNumber))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(fpsDwRepoService).getDetail(tradeNumber);
    }

    @Test
    void makeVoucher_ShouldSucceed() throws Exception {
        // 准备测试数据
        FpsDwRepoMakeVoucherReq req = new FpsDwRepoMakeVoucherReq();
        req.setTradeNumbers(Arrays.asList("TEST001", "TEST002"));
        when(fpsDwRepoService.makeVoucher(any())).thenReturn(true);

        // 执行测试
        mockMvc.perform(post("/api/fps-dw-repo/make-voucher")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(fpsDwRepoService).makeVoucher(any());
    }

    @Test
    void export_ShouldSucceed() throws Exception {
        // 准备测试数据
        FpsDwRepoQueryReq req = new FpsDwRepoQueryReq();
        when(fpsDwRepoService.export(any())).thenReturn(Arrays.asList(new FpsDwRepoResp()));

        // 执行测试
        mockMvc.perform(get("/api/fps-dw-repo/export")
                .param("tradeNumber", "TEST001"))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(fpsDwRepoService).export(any());
    }
} 