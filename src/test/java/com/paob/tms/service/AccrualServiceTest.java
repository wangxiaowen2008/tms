package com.paob.tms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.AccrualQueryReq;
import com.paob.tms.dto.req.BatchInterestJournalReq;
import com.paob.tms.dto.resp.AccrualResp;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AccrualServiceTest {

    @InjectMocks
    private AccrualService accrualService;

    @Mock
    private HttpServletResponse response;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("分页查询计提数据 - 正常场景")
    void queryPage_ShouldReturnPage() {
        // Arrange
        AccrualQueryReq req = new AccrualQueryReq();
        req.setPageNum(1);
        req.setPageSize(10);
        req.setDateStart(LocalDate.of(2024, 1, 1));
        req.setDateEnd(LocalDate.of(2024, 12, 31));
        
        // Act
        IPage<AccrualResp> result = accrualService.queryPage(req);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.getCurrent());
        assertEquals(10, result.getSize());
        verify(accrualService, times(1)).queryPage(req);
    }

    @Test
    @DisplayName("分页查询计提数据 - 空查询条件")
    void queryPage_WithEmptyQuery_ShouldReturnPage() {
        // Arrange
        AccrualQueryReq req = new AccrualQueryReq();
        
        // Act
        IPage<AccrualResp> result = accrualService.queryPage(req);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getRecords().isEmpty());
    }

    @Test
    @DisplayName("分页查询计提数据 - 无效日期范围")
    void queryPage_WithInvalidDateRange_ShouldReturnEmptyPage() {
        // Arrange
        AccrualQueryReq req = new AccrualQueryReq();
        req.setPageNum(1);
        req.setPageSize(10);
        req.setDateStart(LocalDate.of(2024, 12, 31));
        req.setDateEnd(LocalDate.of(2024, 1, 1));
        
        // Act
        IPage<AccrualResp> result = accrualService.queryPage(req);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getRecords().isEmpty());
    }

    @Test
    @DisplayName("导出计提数据 - 正常场景")
    void export_ShouldExportSuccessfully() {
        // Arrange
        AccrualQueryReq req = new AccrualQueryReq();
        req.setDateStart(LocalDate.of(2024, 1, 1));
        req.setDateEnd(LocalDate.of(2024, 12, 31));
        
        // Act & Assert
        assertDoesNotThrow(() -> accrualService.export(req, response));
        verify(accrualService, times(1)).export(req, response);
        verify(response, times(1)).setContentType("application/vnd.ms-excel");
    }

    @Test
    @DisplayName("导出计提数据 - 空查询条件")
    void export_WithEmptyQuery_ShouldExportSuccessfully() {
        // Arrange
        AccrualQueryReq req = new AccrualQueryReq();
        
        // Act & Assert
        assertDoesNotThrow(() -> accrualService.export(req, response));
        verify(response, times(1)).setContentType("application/vnd.ms-excel");
    }

    @Test
    @DisplayName("导出计提数据 - 无效日期范围")
    void export_WithInvalidDateRange_ShouldExportSuccessfully() {
        // Arrange
        AccrualQueryReq req = new AccrualQueryReq();
        req.setDateStart(LocalDate.of(2024, 12, 31));
        req.setDateEnd(LocalDate.of(2024, 1, 1));
        
        // Act & Assert
        assertDoesNotThrow(() -> accrualService.export(req, response));
        verify(response, times(1)).setContentType("application/vnd.ms-excel");
    }

    @Test
    @DisplayName("批量计提利息制证 - 正常场景")
    void batchInterestJournal_ShouldProcessSuccessfully() {
        // Arrange
        BatchInterestJournalReq req = new BatchInterestJournalReq();
        req.setTradeNumbers(List.of("TRADE001", "TRADE002"));
        
        // Act
        String result = accrualService.batchInterestJournal(req);
        
        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(accrualService, times(1)).batchInterestJournal(req);
    }

    @Test
    @DisplayName("批量计提利息制证 - 空请求参数")
    void batchInterestJournal_WithEmptyRequest_ShouldProcessSuccessfully() {
        // Arrange
        BatchInterestJournalReq req = new BatchInterestJournalReq();
        
        // Act
        String result = accrualService.batchInterestJournal(req);
        
        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("生成计提数据 - 正常场景")
    void generate_ShouldGenerateSuccessfully() {
        // Act & Assert
        assertDoesNotThrow(() -> accrualService.generate());
        verify(accrualService, times(1)).generate();
    }

    @Test
    @DisplayName("生成计提数据 - 异常场景")
    void generate_ShouldHandleException() {
        // Arrange
        doThrow(new RuntimeException("生成失败")).when(accrualService).generate();
        
        // Act & Assert
        assertThrows(RuntimeException.class, () -> accrualService.generate());
        verify(accrualService, times(1)).generate();
    }
} 