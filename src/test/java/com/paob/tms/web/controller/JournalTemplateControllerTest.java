package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.JournalTemplateAddReq;
import com.paob.tms.dto.req.JournalTemplateQueryReq;
import com.paob.tms.dto.resp.JournalTemplateResp;
import com.paob.tms.service.JournalTemplateService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class JournalTemplateControllerTest {

    @Mock
    private JournalTemplateService journalTemplateService;

    @InjectMocks
    private JournalTemplateController journalTemplateController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(journalTemplateController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void queryJournalTemplateList_ShouldReturnList() throws Exception {
        // 准备测试数据
        JournalTemplateQueryReq req = new JournalTemplateQueryReq();
        IPage<JournalTemplateResp> expectedPage = mock(IPage.class);
        when(journalTemplateService.queryJournalTemplateList(any(JournalTemplateQueryReq.class))).thenReturn(expectedPage);

        // 执行测试
        mockMvc.perform(get("/api/journal-template/list")
                .param("pageNum", "1")
                .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        // 验证service方法被调用
        verify(journalTemplateService).queryJournalTemplateList(any(JournalTemplateQueryReq.class));
    }

    @Test
    void addJournalTemplate_ShouldSucceed() throws Exception {
        // 准备测试数据
        JournalTemplateAddReq req = new JournalTemplateAddReq();
        doNothing().when(journalTemplateService).addJournalTemplate(any(JournalTemplateAddReq.class));

        // 执行测试
        mockMvc.perform(post("/api/journal-template/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(journalTemplateService).addJournalTemplate(any(JournalTemplateAddReq.class));
    }

    @Test
    void updateJournalTemplate_ShouldSucceed() throws Exception {
        // 准备测试数据
        JournalTemplateAddReq req = new JournalTemplateAddReq();
        doNothing().when(journalTemplateService).updateJournalTemplate(any(JournalTemplateAddReq.class));

        // 执行测试
        mockMvc.perform(post("/api/journal-template/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(journalTemplateService).updateJournalTemplate(any(JournalTemplateAddReq.class));
    }

    @Test
    void getJournalTemplateDetail_ShouldReturnDetail() throws Exception {
        // 准备测试数据
        String tempName = "TEMPLATE001";
        List<JournalTemplateResp> expectedDetails = Arrays.asList(new JournalTemplateResp());
        when(journalTemplateService.getJournalTemplateDetail(tempName)).thenReturn(expectedDetails);

        // 执行测试
        mockMvc.perform(get("/api/journal-template/{tempName}", tempName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        // 验证service方法被调用
        verify(journalTemplateService).getJournalTemplateDetail(tempName);
    }

    @Test
    void submitJournalTemplate_ShouldSucceed() throws Exception {
        // 准备测试数据
        String tempName = "TEMPLATE001";
        doNothing().when(journalTemplateService).submitJournalTemplate(tempName);

        // 执行测试
        mockMvc.perform(post("/api/journal-template/{tempName}/submit", tempName))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(journalTemplateService).submitJournalTemplate(tempName);
    }

    @Test
    void approveJournalTemplate_ShouldSucceed() throws Exception {
        // 准备测试数据
        String tempName = "TEMPLATE001";
        doNothing().when(journalTemplateService).approveJournalTemplate(tempName);

        // 执行测试
        mockMvc.perform(post("/api/journal-template/{tempName}/review", tempName))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(journalTemplateService).approveJournalTemplate(tempName);
    }

    @Test
    void rejectJournalTemplate_ShouldSucceed() throws Exception {
        // 准备测试数据
        String tempName = "TEMPLATE001";
        doNothing().when(journalTemplateService).rejectJournalTemplate(tempName);

        // 执行测试
        mockMvc.perform(post("/api/journal-template/{tempName}/reject", tempName))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(journalTemplateService).rejectJournalTemplate(tempName);
    }

    @Test
    void getApprovedTemplateNames_ShouldReturnNames() throws Exception {
        // 准备测试数据
        List<String> expectedNames = Arrays.asList("TEMPLATE001", "TEMPLATE002");
        when(journalTemplateService.getApprovedTemplateNames()).thenReturn(expectedNames);

        // 执行测试
        mockMvc.perform(get("/api/journal-template/approved-names"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        // 验证service方法被调用
        verify(journalTemplateService).getApprovedTemplateNames();
    }
} 