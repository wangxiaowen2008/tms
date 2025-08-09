package com.paob.tms.web.controller;

import com.paob.tms.service.AttachmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AttachmentControllerTest {

    @Mock
    private AttachmentService attachmentService;

    @InjectMocks
    private AttachmentController attachmentController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(attachmentController).build();
    }

    @Test
    void uploadAttachment_ShouldSucceed() throws Exception {
        // 准备测试数据
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "test content".getBytes()
        );
        doNothing().when(attachmentService).uploadAttachment(any(), any(), any(), any());

        // 执行测试
        mockMvc.perform(multipart("/api/attachment/upload")
                .file(file)
                .param("attachmentType", "TEST")
                .param("businessKey", "TEST001")
                .param("uploadBy", "admin"))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(attachmentService).uploadAttachment(any(), any(), any(), any());
    }

    @Test
    void downloadAttachment_ShouldSucceed() throws Exception {
        // 准备测试数据
        Long attachmentId = 1L;
        doNothing().when(attachmentService).downloadAttachment(any(), any());

        // 执行测试
        mockMvc.perform(get("/api/attachment/download/{id}", attachmentId))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(attachmentService).downloadAttachment(any(), any());
    }

    @Test
    void deleteAttachment_ShouldSucceed() throws Exception {
        // 准备测试数据
        Long attachmentId = 1L;
        String updatedBy = "admin";
        doNothing().when(attachmentService).deleteAttachment(any(), any());

        // 执行测试
        mockMvc.perform(delete("/api/attachment/{id}", attachmentId)
                .param("updatedBy", updatedBy))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(attachmentService).deleteAttachment(any(), any());
    }
} 