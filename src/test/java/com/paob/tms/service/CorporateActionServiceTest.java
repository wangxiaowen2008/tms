package com.paob.tms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.CorporateActionQueryReq;
import com.paob.tms.dto.resp.CorporateActionResp;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CorporateActionServiceTest {

    @InjectMocks
    private CorporateActionService corporateActionService;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("分页查询公司行为列表 - 正常场景")
    void queryList_ShouldReturnPage() {
        // Arrange
        CorporateActionQueryReq req = new CorporateActionQueryReq();
        req.setPageNum(1);
        req.setPageSize(10);
        req.setValueDateStart(LocalDate.of(2024, 1, 1));
        req.setValueDateEnd(LocalDate.of(2024, 12, 31));
        
        // Act
        IPage<CorporateActionResp> result = corporateActionService.queryList(req);
        
        // Assert
        assertNotNull(result);
        verify(corporateActionService, times(1)).queryList(req);
    }

    @Test
    @DisplayName("分页查询公司行为列表 - 空查询条件")
    void queryList_WithEmptyQuery_ShouldReturnPage() {
        // Arrange
        CorporateActionQueryReq req = new CorporateActionQueryReq();
        
        // Act
        IPage<CorporateActionResp> result = corporateActionService.queryList(req);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getRecords().isEmpty());
    }

    @Test
    @DisplayName("分页查询公司行为列表 - 无效日期范围")
    void queryList_WithInvalidDateRange_ShouldReturnEmptyPage() {
        // Arrange
        CorporateActionQueryReq req = new CorporateActionQueryReq();
        req.setPageNum(1);
        req.setPageSize(10);
        req.setValueDateStart(LocalDate.of(2024, 12, 31));
        req.setValueDateEnd(LocalDate.of(2024, 1, 1));
        
        // Act
        IPage<CorporateActionResp> result = corporateActionService.queryList(req);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getRecords().isEmpty());
    }

    @Test
    @DisplayName("导出公司行为列表 - 正常场景")
    void exportList_ShouldExportSuccessfully() throws IOException {
        // Arrange
        CorporateActionQueryReq req = new CorporateActionQueryReq();
        req.setValueDateStart(LocalDate.of(2024, 1, 1));
        req.setValueDateEnd(LocalDate.of(2024, 12, 31));
        
        // Act & Assert
        assertDoesNotThrow(() -> corporateActionService.exportList(req, response));
        verify(corporateActionService, times(1)).exportList(req, response);
        verify(response, times(1)).setContentType("application/vnd.ms-excel");
    }

    @Test
    @DisplayName("导出公司行为列表 - 空查询条件")
    void exportList_WithEmptyQuery_ShouldExportSuccessfully() {
        // Arrange
        CorporateActionQueryReq req = new CorporateActionQueryReq();
        
        // Act & Assert
        assertDoesNotThrow(() -> corporateActionService.exportList(req, response));
        verify(response, times(1)).setContentType("application/vnd.ms-excel");
    }

    @Test
    @DisplayName("导出公司行为列表 - 无效日期范围")
    void exportList_WithInvalidDateRange_ShouldExportSuccessfully() {
        // Arrange
        CorporateActionQueryReq req = new CorporateActionQueryReq();
        req.setValueDateStart(LocalDate.of(2024, 12, 31));
        req.setValueDateEnd(LocalDate.of(2024, 1, 1));
        
        // Act & Assert
        assertDoesNotThrow(() -> corporateActionService.exportList(req, response));
        verify(response, times(1)).setContentType("application/vnd.ms-excel");
    }

    @Test
    @DisplayName("批量制证 - 正常场景")
    void batchVoucher_ShouldProcessSuccessfully() {
        // Arrange
        List<String> actionNos = Arrays.asList("EFB00001", "EFB00002");
        
        // Act
        String result = corporateActionService.batchVoucher(actionNos);
        
        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(corporateActionService, times(1)).batchVoucher(actionNos);
    }

    @Test
    @DisplayName("批量制证 - 空列表")
    void batchVoucher_WithEmptyList_ShouldProcessSuccessfully() {
        // Arrange
        List<String> actionNos = List.of();
        
        // Act
        String result = corporateActionService.batchVoucher(actionNos);
        
        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("查询公司行为详情 - 正常场景")
    void getDetail_ShouldReturnDetail() {
        // Arrange
        String actionNo = "EFB00001";
        
        // Act
        CorporateActionResp result = corporateActionService.getDetail(actionNo);
        
        // Assert
        assertNotNull(result);
        assertEquals(actionNo, result.getActionNo());
        verify(corporateActionService, times(1)).getDetail(actionNo);
    }

    @Test
    @DisplayName("查询公司行为详情 - 无效编号")
    void getDetail_WithInvalidActionNo_ShouldReturnNull() {
        // Arrange
        String actionNo = "INVALID_ACTION_NO";
        
        // Act
        CorporateActionResp result = corporateActionService.getDetail(actionNo);
        
        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("计算公司行为 - 正常场景")
    void calculateCorporateAction_ShouldCalculateSuccessfully() {
        // Act & Assert
        assertDoesNotThrow(() -> corporateActionService.calculateCorporateAction());
        verify(corporateActionService, times(1)).calculateCorporateAction();
    }
} 