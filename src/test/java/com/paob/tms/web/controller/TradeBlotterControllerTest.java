package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.paob.tms.dto.req.TradeBlotterQueryReq;
import com.paob.tms.dto.resp.TradeBlotterResp;
import com.paob.tms.service.TradeBlotterService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TradeBlotterControllerTest {

    @Mock
    private TradeBlotterService tradeBlotterService;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private TradeBlotterController tradeBlotterController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tradeBlotterController).build();
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Test
    void queryTradeBlotterList_ShouldReturnList() throws Exception {
        // 准备测试数据
        TradeBlotterQueryReq req = new TradeBlotterQueryReq();
        Page<TradeBlotterResp> page = new Page<>();
        page.setRecords(Arrays.asList(new TradeBlotterResp()));
        page.setTotal(1);

        // 模拟service行为
        when(tradeBlotterService.queryTradeBlotterList(any())).thenReturn(page);

        // 执行测试
        mockMvc.perform(get("/api/trade-blotter/list")
                .param("tradeNumber", "TEST001"))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(tradeBlotterService).queryTradeBlotterList(any());
    }

    @Test
    void getDetail_ShouldReturnDetail() throws Exception {
        // 准备测试数据
        String tradeNumber = "TEST001";
        TradeBlotterResp expectedResp = new TradeBlotterResp();
        when(tradeBlotterService.getTradeBlotterDetail(tradeNumber)).thenReturn(expectedResp);

        // 执行测试
        mockMvc.perform(get("/api/trade-blotter/detail/{tradeNumber}", tradeNumber))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(tradeBlotterService).getTradeBlotterDetail(tradeNumber);
    }

    @Test
    void export_ShouldSucceed() throws Exception {
        // 准备测试数据
        TradeBlotterQueryReq req = new TradeBlotterQueryReq();

        // 执行测试
        mockMvc.perform(get("/api/trade-blotter/export")
                .param("tradeNumber", "TEST001"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/octet-stream"));

        // 验证service方法被调用
        verify(tradeBlotterService).exportTradeBlotter(any(), any());
    }

    @Test
    void makeVoucher_ShouldSucceed() throws Exception {
        // 准备测试数据
        List<String> tradeNumber = Lists.newArrayList();
        tradeNumber.add("TEST01");

        // 执行测试
        mockMvc.perform(post("/api/trade-blotter/make-voucher/{tradeNumber}", tradeNumber))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(tradeBlotterService).makeVoucher(tradeNumber);
    }

    @Test
    void uploadTradeBlotterFile_ShouldSucceed() throws Exception {
        // 准备测试数据
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.xlsx",
                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                "test data".getBytes()
        );
        doNothing().when(tradeBlotterService).uploadTradeBlotterFile(any());

        // 执行测试
        mockMvc.perform(multipart("/api/trade-blotter/upload")
                .file(file))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(tradeBlotterService).uploadTradeBlotterFile(any());
    }

    @Test
    void uploadOtherAttachment_ShouldSucceed() throws Exception {
        // 准备测试数据
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.pdf",
                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                "test data".getBytes()
        );
        doNothing().when(tradeBlotterService).uploadOtherAttachment(any());

        // 执行测试
        mockMvc.perform(multipart("/api/trade-blotter/uploadOther")
                .file(file))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(tradeBlotterService).uploadOtherAttachment(any());
    }

    @Test
    void confirmUpload_ShouldSucceed() throws Exception {
        // 准备测试数据
        doNothing().when(tradeBlotterService).confirmUpload();

        // 执行测试
        mockMvc.perform(get("/api/trade-blotter/confirm"))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(tradeBlotterService).confirmUpload();
    }

    @Test
    void queryIsin_ShouldReturnIsinList() throws Exception {
        // 准备测试数据
        List<String> expectedIsins = Arrays.asList("ISIN001", "ISIN002");
        when(tradeBlotterService.queryAllIsin()).thenReturn(expectedIsins);

        // 执行测试
        mockMvc.perform(get("/api/trade-blotter/queryIsin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        // 验证service方法被调用
        verify(tradeBlotterService).queryAllIsin();
    }
} 