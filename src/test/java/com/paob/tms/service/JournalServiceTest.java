package com.paob.tms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.*;
import com.paob.tms.dto.resp.JournalBatchCreateResp;
import com.paob.tms.dto.resp.JournalDetailResp;
import com.paob.tms.dto.resp.JournalResp;
import com.paob.tms.dto.resp.JournalTemplateResp;
import com.paob.tms.model.TmsCitJournal;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JournalServiceTest {

    @InjectMocks
    private JournalService journalService;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("查询凭证列表 - 正常场景")
    void queryJournalList_ShouldReturnPage() {
        // Arrange
        JournalQueryReq req = new JournalQueryReq();
        req.setPageNum(1);
        req.setPageSize(10);
        
        // Act
        IPage<JournalResp> result = journalService.queryJournalList(req);
        
        // Assert
        assertNotNull(result);
        verify(journalService, times(1)).queryJournalList(req);
    }

    @Test
    @DisplayName("查询凭证列表 - 空查询条件")
    void queryJournalList_WithEmptyQuery_ShouldReturnPage() {
        // Arrange
        JournalQueryReq req = new JournalQueryReq();
        
        // Act
        IPage<JournalResp> result = journalService.queryJournalList(req);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getRecords().isEmpty());
    }

    @Test
    @DisplayName("导出凭证列表 - 正常场景")
    void exportJournalList_ShouldExportSuccessfully() {
        // Arrange
        JournalQueryReq req = new JournalQueryReq();
        
        // Act & Assert
        assertDoesNotThrow(() -> journalService.exportJournalList(req, response));
        verify(journalService, times(1)).exportJournalList(req, response);
        verify(response, times(1)).setContentType("application/vnd.ms-excel");
    }

    @Test
    @DisplayName("复核通过 - 正常场景")
    void approveJournal_ShouldApproveSuccessfully() {
        // Arrange
        JournalApproveReq req = new JournalApproveReq();
        req.setJournalSequence("TEST001");
        
        // Act & Assert
        assertDoesNotThrow(() -> journalService.approveJournal(req));
        verify(journalService, times(1)).approveJournal(req);
    }

    @Test
    @DisplayName("复核通过 - 凭证编号为空")
    void approveJournal_WithEmptySequence_ShouldThrowException() {
        // Arrange
        JournalApproveReq req = new JournalApproveReq();
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> journalService.approveJournal(req));
    }

    @Test
    @DisplayName("复核不通过 - 正常场景")
    void unApproveJournal_ShouldUnApproveSuccessfully() {
        // Arrange
        JournalUnApproveReq req = new JournalUnApproveReq();
        req.setJournalSequence("TEST001");
        
        // Act & Assert
        assertDoesNotThrow(() -> journalService.unApproveJournal(req));
        verify(journalService, times(1)).unApproveJournal(req);
    }

    @Test
    @DisplayName("批量复核拒绝 - 正常场景")
    void batchRejectJournal_ShouldRejectSuccessfully() {
        // Arrange
        JournalBatchRejectReq req = new JournalBatchRejectReq();
        req.setJournalSequence(Arrays.asList("TEST001", "TEST002"));
        
        // Act
        String result = journalService.batchRejectJournal(req);
        
        // Assert
        assertNotNull(result);
        verify(journalService, times(1)).batchRejectJournal(req);
    }

    @Test
    @DisplayName("批量复核拒绝 - 空列表")
    void batchRejectJournal_WithEmptyList_ShouldThrowException() {
        // Arrange
        JournalBatchRejectReq req = new JournalBatchRejectReq();
        req.setJournalSequence(Collections.emptyList());
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> journalService.batchRejectJournal(req));
    }

    @Test
    @DisplayName("批量复核通过 - 正常场景")
    void batchApproveJournal_ShouldApproveSuccessfully() {
        // Arrange
        JournalBatchApproveReq req = new JournalBatchApproveReq();
        req.setJournalSequence(Arrays.asList("TEST001", "TEST002"));
        
        // Act
        String result = journalService.batchApproveJournal(req);
        
        // Assert
        assertNotNull(result);
        verify(journalService, times(1)).batchApproveJournal(req);
    }

    @Test
    @DisplayName("批量复核通过 - 空列表")
    void batchApproveJournal_WithEmptyList_ShouldThrowException() {
        // Arrange
        JournalBatchApproveReq req = new JournalBatchApproveReq();
        req.setJournalSequence(Collections.emptyList());
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> journalService.batchApproveJournal(req));
    }

    @Test
    @DisplayName("下载凭证模板 - 正常场景")
    void downloadTemplate_ShouldDownloadSuccessfully() {
        // Act & Assert
        assertDoesNotThrow(() -> journalService.downloadTemplate(response));
        verify(journalService, times(1)).downloadTemplate(response);
        verify(response, times(1)).setContentType("application/vnd.ms-excel");
    }

    @Test
    @DisplayName("查询凭证详情 - 正常场景")
    void getJournalDetail_ShouldReturnDetail() {
        // Arrange
        String journalSequence = "TEST001";
        
        // Act
        List<JournalDetailResp> result = journalService.getJournalDetail(journalSequence);
        
        // Assert
        assertNotNull(result);
        verify(journalService, times(1)).getJournalDetail(journalSequence);
    }

    @Test
    @DisplayName("查询凭证详情 - 无效凭证编号")
    void getJournalDetail_WithInvalidSequence_ShouldReturnEmptyList() {
        // Arrange
        String journalSequence = "INVALID_SEQUENCE";
        
        // Act
        List<JournalDetailResp> result = journalService.getJournalDetail(journalSequence);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("编辑凭证 - 正常场景")
    void updateJournal_ShouldUpdateSuccessfully() {
        // Arrange
        List<JournalDetailResp> list = Arrays.asList(new JournalDetailResp());
        
        // Act & Assert
        assertDoesNotThrow(() -> journalService.updateJournal(list));
        verify(journalService, times(1)).updateJournal(list);
    }

    @Test
    @DisplayName("编辑凭证 - 空列表")
    void updateJournal_WithEmptyList_ShouldThrowException() {
        // Arrange
        List<JournalDetailResp> list = Collections.emptyList();
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> journalService.updateJournal(list));
    }

    @Test
    @DisplayName("导入凭证 - 正常场景")
    void importJournal_ShouldImportSuccessfully() throws Exception {
        // Arrange
        MultipartFile file = new MockMultipartFile(
            "file",
            "test.xlsx",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "test data".getBytes()
        );
        
        // Act
        String result = journalService.importJournal(file);
        
        // Assert
        assertNotNull(result);
        verify(journalService, times(1)).importJournal(file);
    }

    @Test
    @DisplayName("导入凭证 - 空文件")
    void importJournal_WithEmptyFile_ShouldThrowException() {
        // Arrange
        MultipartFile file = new MockMultipartFile(
            "file",
            "test.xlsx",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            new byte[0]
        );
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> journalService.importJournal(file));
    }

    @Test
    @DisplayName("查询模板列表 - 正常场景")
    void queryTemplateList_ShouldReturnList() {
        // Act
        List<JournalTemplateResp> result = journalService.queryTemplateList();
        
        // Assert
        assertNotNull(result);
        verify(journalService, times(1)).queryTemplateList();
    }

    @Test
    @DisplayName("批量制证 - 正常场景")
    void batchCreateJournal_ShouldCreateSuccessfully() {
        // Arrange
        JournalBatchCreateReq req = new JournalBatchCreateReq();
        req.setBusinessIds(Arrays.asList("BUS001", "BUS002"));
        
        // Act
        JournalBatchCreateResp result = journalService.batchCreateJournal(req);
        
        // Assert
        assertNotNull(result);
        verify(journalService, times(1)).batchCreateJournal(req);
    }

    @Test
    @DisplayName("批量制证 - 空业务ID列表")
    void batchCreateJournal_WithEmptyBusinessIds_ShouldThrowException() {
        // Arrange
        JournalBatchCreateReq req = new JournalBatchCreateReq();
        req.setBusinessIds(Collections.emptyList());
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> journalService.batchCreateJournal(req));
    }

    @Test
    @DisplayName("制证初始化 - 正常场景")
    void initJournal_ShouldInitSuccessfully() {
        // Arrange
        JournalInitReq req = new JournalInitReq();
        req.setBusinessIds(Arrays.asList("BUS001", "BUS002"));
        
        // Act
        List<JournalDetailResp> result = journalService.initJournal(req);
        
        // Assert
        assertNotNull(result);
        verify(journalService, times(1)).initJournal(req);
    }

    @Test
    @DisplayName("制证初始化 - 空业务ID列表")
    void initJournal_WithEmptyBusinessIds_ShouldThrowException() {
        // Arrange
        JournalInitReq req = new JournalInitReq();
        req.setBusinessIds(Collections.emptyList());
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> journalService.initJournal(req));
    }

    @Test
    @DisplayName("自动制证 - 正常场景")
    void autoGenerate_ShouldGenerateSuccessfully() {
        // Act & Assert
        assertDoesNotThrow(() -> journalService.autoGenerate());
        verify(journalService, times(1)).autoGenerate();
    }
} 