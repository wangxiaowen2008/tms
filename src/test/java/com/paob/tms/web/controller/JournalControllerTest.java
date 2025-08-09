package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.*;
import com.paob.tms.dto.resp.JournalDetailResp;
import com.paob.tms.dto.resp.JournalResp;
import com.paob.tms.service.JournalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class JournalControllerTest {

    @Mock
    private JournalService journalService;

    @InjectMocks
    private JournalController journalController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(journalController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void queryJournalList_ShouldReturnList() throws Exception {
        // 准备测试数据
        JournalQueryReq req = new JournalQueryReq();
        IPage<JournalResp> expectedPage = mock(IPage.class);
        when(journalService.queryJournalList(any(JournalQueryReq.class))).thenReturn(expectedPage);

        // 执行测试
        mockMvc.perform(get("/api/journal/list")
                .param("pageNum", "1")
                .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        // 验证service方法被调用
        verify(journalService).queryJournalList(any(JournalQueryReq.class));
    }

    @Test
    void exportJournalList_ShouldSucceed() throws Exception {
        // 准备测试数据
        JournalQueryReq req = new JournalQueryReq();
        doNothing().when(journalService).exportJournalList(any(), any());

        // 执行测试
        mockMvc.perform(get("/api/journal/export")
                .param("pageNum", "1")
                .param("pageSize", "10"))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(journalService).exportJournalList(any(), any());
    }

    @Test
    void approveJournal_ShouldSucceed() throws Exception {
        // 准备测试数据
        JournalApproveReq req = new JournalApproveReq();
        doNothing().when(journalService).approveJournal(any(JournalApproveReq.class));

        // 执行测试
        mockMvc.perform(post("/api/journal/approve")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(journalService).approveJournal(any(JournalApproveReq.class));
    }

    @Test
    void unApproveJournal_ShouldSucceed() throws Exception {
        // 准备测试数据
        JournalUnApproveReq req = new JournalUnApproveReq();
        doNothing().when(journalService).unApproveJournal(any(JournalUnApproveReq.class));

        // 执行测试
        mockMvc.perform(post("/api/journal/unApprove")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(journalService).unApproveJournal(any(JournalUnApproveReq.class));
    }

    @Test
    void batchRejectJournal_ShouldReturnResult() throws Exception {
        // 准备测试数据
        JournalBatchRejectReq req = new JournalBatchRejectReq();
        String expectedResult = "批量拒绝成功";
        when(journalService.batchRejectJournal(any(JournalBatchRejectReq.class))).thenReturn(expectedResult);

        // 执行测试
        mockMvc.perform(post("/api/journal/batch-reject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));

        // 验证service方法被调用
        verify(journalService).batchRejectJournal(any(JournalBatchRejectReq.class));
    }

    @Test
    void batchApproveJournal_ShouldReturnResult() throws Exception {
        // 准备测试数据
        JournalBatchApproveReq req = new JournalBatchApproveReq();
        String expectedResult = "批量通过成功";
        when(journalService.batchApproveJournal(any(JournalBatchApproveReq.class))).thenReturn(expectedResult);

        // 执行测试
        mockMvc.perform(post("/api/journal/batch-approve")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));

        // 验证service方法被调用
        verify(journalService).batchApproveJournal(any(JournalBatchApproveReq.class));
    }

    @Test
    void downloadTemplate_ShouldSucceed() throws Exception {
        // 准备测试数据
        doNothing().when(journalService).downloadTemplate(any());

        // 执行测试
        mockMvc.perform(get("/api/journal/template"))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(journalService).downloadTemplate(any());
    }

    @Test
    void getJournalDetail_ShouldReturnDetail() throws Exception {
        // 准备测试数据
        String journalSequence = "JOURNAL001";
        List<JournalDetailResp> expectedDetails = Arrays.asList(new JournalDetailResp());
        when(journalService.getJournalDetail(journalSequence)).thenReturn(expectedDetails);

        // 执行测试
        mockMvc.perform(get("/api/journal/{journalSequence}", journalSequence))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        // 验证service方法被调用
        verify(journalService).getJournalDetail(journalSequence);
    }

    @Test
    void updateJournal_ShouldSucceed() throws Exception {
        // 准备测试数据
        List<JournalDetailResp> req = Arrays.asList(new JournalDetailResp());
        doNothing().when(journalService).updateJournal(anyList());

        // 执行测试
        mockMvc.perform(post("/api/journal/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(journalService).updateJournal(anyList());
    }

    @Test
    void importJournal_ShouldReturnResult() throws Exception {
        // 准备测试数据
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.xlsx",
                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                "test data".getBytes()
        );
        String expectedResult = "导入成功";
        when(journalService.importJournal(any())).thenReturn(expectedResult);

        // 执行测试
        mockMvc.perform(multipart("/api/journal/import")
                .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));

        // 验证service方法被调用
        verify(journalService).importJournal(any());
    }
} 