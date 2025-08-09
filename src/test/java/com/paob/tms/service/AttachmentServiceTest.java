package com.paob.tms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.AttachmentQueryReq;
import com.paob.tms.dto.resp.AttachmentResp;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AttachmentServiceTest {

    @InjectMocks
    private AttachmentService attachmentService;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("分页查询附件列表 - 正常场景")
    void queryAttachmentList_ShouldReturnPage() {
        // Arrange
        AttachmentQueryReq req = new AttachmentQueryReq();
        req.setPageNum(1);
        req.setPageSize(10);
        req.setAttachmentType("DOCUMENT");
        req.setBusinessKey("BUSINESS001");
        
        // Act
        IPage<AttachmentResp> result = attachmentService.queryAttachmentList(req);
        
        // Assert
        assertNotNull(result);
        verify(attachmentService, times(1)).queryAttachmentList(req);
    }

    @Test
    @DisplayName("分页查询附件列表 - 空查询条件")
    void queryAttachmentList_WithEmptyQuery_ShouldReturnPage() {
        // Arrange
        AttachmentQueryReq req = new AttachmentQueryReq();
        
        // Act
        IPage<AttachmentResp> result = attachmentService.queryAttachmentList(req);
        
        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("分页查询附件列表 - 无效附件类型")
    void queryAttachmentList_WithInvalidType_ShouldReturnEmptyPage() {
        // Arrange
        AttachmentQueryReq req = new AttachmentQueryReq();
        req.setPageNum(1);
        req.setPageSize(10);
        req.setAttachmentType("INVALID_TYPE");
        
        // Act
        IPage<AttachmentResp> result = attachmentService.queryAttachmentList(req);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getRecords().isEmpty());
    }

    @Test
    @DisplayName("上传附件 - 正常场景")
    void uploadAttachment_ShouldUploadSuccessfully() throws IOException {
        // Arrange
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "test.pdf",
            "application/pdf",
            "test content".getBytes()
        );
        String attachmentType = "DOCUMENT";
        String businessKey = "BUSINESS001";
        String uploadBy = "admin";
        
        // Act
        AttachmentResp result = attachmentService.uploadAttachment(file, attachmentType, businessKey, uploadBy);
        
        // Assert
        assertNotNull(result);
        verify(attachmentService, times(1)).uploadAttachment(file, attachmentType, businessKey, uploadBy);
    }

    @Test
    @DisplayName("上传附件 - 空文件")
    void uploadAttachment_WithEmptyFile_ShouldThrowException() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "",
            "application/pdf",
            new byte[0]
        );
        String attachmentType = "DOCUMENT";
        String businessKey = "BUSINESS001";
        String uploadBy = "admin";
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            attachmentService.uploadAttachment(file, attachmentType, businessKey, uploadBy)
        );
    }

    @Test
    @DisplayName("删除附件 - 正常场景")
    void deleteAttachment_ShouldDeleteSuccessfully() {
        // Arrange
        Long id = 1L;
        String updatedBy = "admin";
        
        // Act & Assert
        assertDoesNotThrow(() -> attachmentService.deleteAttachment(id, updatedBy));
        verify(attachmentService, times(1)).deleteAttachment(id, updatedBy);
    }

    @Test
    @DisplayName("删除附件 - 无效ID")
    void deleteAttachment_WithInvalidId_ShouldThrowException() {
        // Arrange
        Long id = -1L;
        String updatedBy = "admin";
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            attachmentService.deleteAttachment(id, updatedBy)
        );
    }

    @Test
    @DisplayName("下载附件 - 正常场景")
    void downloadAttachment_ShouldDownloadSuccessfully() throws IOException {
        // Arrange
        Long id = 1L;
        
        // Act & Assert
        assertDoesNotThrow(() -> attachmentService.downloadAttachment(id, response));
        verify(attachmentService, times(1)).downloadAttachment(id, response);
    }

    @Test
    @DisplayName("下载附件 - 无效ID")
    void downloadAttachment_WithInvalidId_ShouldThrowException() {
        // Arrange
        Long id = -1L;
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            attachmentService.downloadAttachment(id, response)
        );
    }

    @Test
    @DisplayName("打包下载附件 - 正常场景")
    void downloadAttachmentPackage_ShouldDownloadSuccessfully() throws IOException {
        // Arrange
        String businessKey = "BUSINESS001";
        
        // Act & Assert
        assertDoesNotThrow(() -> attachmentService.downloadAttachmentPackage(businessKey, response));
        verify(attachmentService, times(1)).downloadAttachmentPackage(businessKey, response);
    }

    @Test
    @DisplayName("打包下载附件 - 无效业务键")
    void downloadAttachmentPackage_WithInvalidBusinessKey_ShouldThrowException() {
        // Arrange
        String businessKey = "";
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            attachmentService.downloadAttachmentPackage(businessKey, response)
        );
    }
} 