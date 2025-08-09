package com.paob.tms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.FxListReq;
import com.paob.tms.dto.resp.FxListResp;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TmsFxServiceTest {

    @InjectMocks
    private TmsFxService tmsFxService;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("分页查询外汇交易列表 - 正常场景")
    void listFx_ShouldReturnPage() {
        // Arrange
        FxListReq req = new FxListReq();
        req.setPageNum(1);
        req.setPageSize(10);
        req.setValueDateStart(LocalDate.of(2024, 1, 1));
        req.setValueDateEnd(LocalDate.of(2024, 12, 31));
        req.setCurrency("USD");
        req.setAmountMin(new BigDecimal("1000"));
        req.setAmountMax(new BigDecimal("10000"));
        req.setPaymentStatus("UNPAID");
        
        // Act
        IPage<FxListResp> result = tmsFxService.listFx(req);
        
        // Assert
        assertNotNull(result);
        verify(tmsFxService, times(1)).listFx(req);
    }

    @Test
    @DisplayName("分页查询外汇交易列表 - 空查询条件")
    void listFx_WithEmptyQuery_ShouldReturnPage() {
        // Arrange
        FxListReq req = new FxListReq();
        
        // Act
        IPage<FxListResp> result = tmsFxService.listFx(req);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.getCurrent());
        assertEquals(10, result.getSize());
    }

    @Test
    @DisplayName("分页查询外汇交易列表 - 空结果")
    void listFx_ShouldReturnEmptyPage() {
        // Arrange
        FxListReq req = new FxListReq();
        req.setTradeNumber("NON_EXISTENT_TRADE");
        req.setValueDateStart(LocalDate.of(2024, 1, 1));
        req.setValueDateEnd(LocalDate.of(2024, 12, 31));
        
        // Act
        IPage<FxListResp> result = tmsFxService.listFx(req);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getRecords().isEmpty());
    }

    @Test
    @DisplayName("分页查询外汇交易列表 - 无效币种")
    void listFx_WithInvalidCurrency_ShouldReturnEmptyPage() {
        // Arrange
        FxListReq req = new FxListReq();
        req.setPageNum(1);
        req.setPageSize(10);
        req.setCurrency("INVALID_CURRENCY");
        
        // Act
        IPage<FxListResp> result = tmsFxService.listFx(req);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getRecords().isEmpty());
    }

    @Test
    @DisplayName("分页查询外汇交易列表 - 无效日期范围")
    void listFx_WithInvalidDateRange_ShouldReturnEmptyPage() {
        // Arrange
        FxListReq req = new FxListReq();
        req.setPageNum(1);
        req.setPageSize(10);
        req.setValueDateStart(LocalDate.of(2024, 12, 31));
        req.setValueDateEnd(LocalDate.of(2024, 1, 1));
        
        // Act
        IPage<FxListResp> result = tmsFxService.listFx(req);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getRecords().isEmpty());
    }

    @Test
    @DisplayName("导出外汇交易列表 - 正常场景")
    void exportFx_ShouldExportSuccessfully() {
        // Arrange
        FxListReq req = new FxListReq();
        req.setValueDateStart(LocalDate.of(2024, 1, 1));
        req.setValueDateEnd(LocalDate.of(2024, 12, 31));
        req.setCurrency("USD");
        
        // Act & Assert
        assertDoesNotThrow(() -> tmsFxService.exportFx(req, response));
        verify(tmsFxService, times(1)).exportFx(req, response);
    }

    @Test
    @DisplayName("导出外汇交易列表 - 空查询条件")
    void exportFx_WithEmptyQuery_ShouldExportSuccessfully() {
        // Arrange
        FxListReq req = new FxListReq();
        
        // Act & Assert
        assertDoesNotThrow(() -> tmsFxService.exportFx(req, response));
    }

    @Test
    @DisplayName("导出外汇交易列表 - 无效币种")
    void exportFx_WithInvalidCurrency_ShouldExportSuccessfully() {
        // Arrange
        FxListReq req = new FxListReq();
        req.setCurrency("INVALID_CURRENCY");
        
        // Act & Assert
        assertDoesNotThrow(() -> tmsFxService.exportFx(req, response));
    }

    @Test
    @DisplayName("导出外汇交易列表 - 无效日期范围")
    void exportFx_WithInvalidDateRange_ShouldExportSuccessfully() {
        // Arrange
        FxListReq req = new FxListReq();
        req.setValueDateStart(LocalDate.of(2024, 12, 31));
        req.setValueDateEnd(LocalDate.of(2024, 1, 1));
        
        // Act & Assert
        assertDoesNotThrow(() -> tmsFxService.exportFx(req, response));
    }
} 