package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.paob.tms.dto.req.FxListReq;
import com.paob.tms.dto.resp.FxListResp;
import com.paob.tms.service.TmsFxService;
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

class TmsFxControllerTest {

    @Mock
    private TmsFxService tmsFxService;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private TmsFxController tmsFxController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tmsFxController).build();
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Test
    void listFx_ShouldReturnFxList() throws Exception {
        // 准备测试数据
        FxListReq req = new FxListReq();
        Page<FxListResp> page = new Page<>();
        page.setRecords(Arrays.asList(new FxListResp()));
        page.setTotal(1);

        // 模拟service行为
        when(tmsFxService.listFx(any())).thenReturn(page);

        // 执行测试
        mockMvc.perform(get("/api/fx/list")
                .param("tradeNumber", "TEST001"))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(tmsFxService).listFx(any());
    }

    @Test
    void exportFx_ShouldSucceed() throws Exception {
        // 准备测试数据
        FxListReq req = new FxListReq();

        // 执行测试
        mockMvc.perform(get("/api/fx/export")
                .param("tradeNumber", "TEST001"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/octet-stream"));

        // 验证service方法被调用
        verify(tmsFxService).exportFx(any(), any());
    }
} 