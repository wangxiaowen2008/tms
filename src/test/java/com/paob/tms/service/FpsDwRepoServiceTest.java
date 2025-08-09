package com.paob.tms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.FpsDwRepoQueryReq;
import com.paob.tms.dto.req.FpsDwRepoMakeVoucherReq;
import com.paob.tms.dto.resp.FpsDwRepoResp;
import com.paob.tms.model.FpsDwRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FpsDwRepoServiceTest {

    @InjectMocks
    private FpsDwRepoService fpsDwRepoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("分页查询REPO交易 - 正常场景")
    void queryList_ShouldReturnPage() {
        // Arrange
        FpsDwRepoQueryReq condition = new FpsDwRepoQueryReq();
        condition.setPageNum(1);
        condition.setPageSize(10);
        condition.setTradeDateStart(LocalDate.of(2024, 1, 1));
        condition.setTradeDateEnd(LocalDate.of(2024, 12, 31));
        condition.setTradeNumber("REPO001");
        
        // Act
        IPage<FpsDwRepoResp> result = fpsDwRepoService.queryList(condition);
        
        // Assert
        assertNotNull(result);
        verify(fpsDwRepoService, times(1)).queryList(condition);
    }

    @Test
    @DisplayName("分页查询REPO交易 - 空查询条件")
    void queryList_WithEmptyQuery_ShouldReturnPage() {
        // Arrange
        FpsDwRepoQueryReq condition = new FpsDwRepoQueryReq();
        
        // Act
        IPage<FpsDwRepoResp> result = fpsDwRepoService.queryList(condition);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getRecords().isEmpty());
    }

    @Test
    @DisplayName("分页查询REPO交易 - 无效交易编号")
    void queryList_WithInvalidTradeNumber_ShouldReturnEmptyPage() {
        // Arrange
        FpsDwRepoQueryReq condition = new FpsDwRepoQueryReq();
        condition.setPageNum(1);
        condition.setPageSize(10);
        condition.setTradeNumber("INVALID_NUMBER");
        
        // Act
        IPage<FpsDwRepoResp> result = fpsDwRepoService.queryList(condition);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getRecords().isEmpty());
    }

    @Test
    @DisplayName("分页查询REPO交易 - 无效日期范围")
    void queryList_WithInvalidDateRange_ShouldReturnEmptyPage() {
        // Arrange
        FpsDwRepoQueryReq condition = new FpsDwRepoQueryReq();
        condition.setPageNum(1);
        condition.setPageSize(10);
        condition.setTradeDateStart(LocalDate.of(2024, 12, 31));
        condition.setTradeDateEnd(LocalDate.of(2024, 1, 1));
        
        // Act
        IPage<FpsDwRepoResp> result = fpsDwRepoService.queryList(condition);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getRecords().isEmpty());
    }

    @Test
    @DisplayName("查询REPO交易详情 - 正常场景")
    void getDetail_ShouldReturnDetail() {
        // Arrange
        String tradeNumber = "REPO001";
        
        // Act
        FpsDwRepoResp result = fpsDwRepoService.getDetail(tradeNumber);
        
        // Assert
        assertNotNull(result);
        verify(fpsDwRepoService, times(1)).getDetail(tradeNumber);
    }

    @Test
    @DisplayName("查询REPO交易详情 - 无效交易编号")
    void getDetail_WithInvalidTradeNumber_ShouldReturnNull() {
        // Arrange
        String tradeNumber = "INVALID_TRADE_NUMBER";
        
        // Act
        FpsDwRepoResp result = fpsDwRepoService.getDetail(tradeNumber);
        
        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("批量制证 - 正常场景")
    void makeVoucher_ShouldProcessSuccessfully() {
        // Arrange
        FpsDwRepoMakeVoucherReq req = new FpsDwRepoMakeVoucherReq();
        req.setTradeNumbers(Arrays.asList("REPO001", "REPO002"));
        
        // Act
        boolean result = fpsDwRepoService.makeVoucher(req);
        
        // Assert
        assertTrue(result);
        verify(fpsDwRepoService, times(1)).makeVoucher(req);
    }

    @Test
    @DisplayName("批量制证 - 空交易编号列表")
    void makeVoucher_WithEmptyTradeNumbers_ShouldReturnFalse() {
        // Arrange
        FpsDwRepoMakeVoucherReq req = new FpsDwRepoMakeVoucherReq();
        req.setTradeNumbers(Collections.emptyList());
        
        // Act
        boolean result = fpsDwRepoService.makeVoucher(req);
        
        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("导出REPO交易数据 - 正常场景")
    void export_ShouldExportSuccessfully() {
        // Arrange
        FpsDwRepoQueryReq condition = new FpsDwRepoQueryReq();
        condition.setTradeDateStart(LocalDate.of(2024, 1, 1));
        condition.setTradeDateEnd(LocalDate.of(2024, 12, 31));
        condition.setTradeNumber("REPO001");
        
        // Act
        List<FpsDwRepoResp> result = fpsDwRepoService.export(condition);
        
        // Assert
        assertNotNull(result);
        verify(fpsDwRepoService, times(1)).export(condition);
    }

    @Test
    @DisplayName("导出REPO交易数据 - 空查询条件")
    void export_WithEmptyQuery_ShouldExportSuccessfully() {
        // Arrange
        FpsDwRepoQueryReq condition = new FpsDwRepoQueryReq();
        
        // Act
        List<FpsDwRepoResp> result = fpsDwRepoService.export(condition);
        
        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("导出REPO交易数据 - 无效交易编号")
    void export_WithInvalidTradeNumber_ShouldReturnEmptyList() {
        // Arrange
        FpsDwRepoQueryReq condition = new FpsDwRepoQueryReq();
        condition.setTradeNumber("INVALID_NUMBER");
        
        // Act
        List<FpsDwRepoResp> result = fpsDwRepoService.export(condition);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("导出REPO交易数据 - 无效日期范围")
    void export_WithInvalidDateRange_ShouldReturnEmptyList() {
        // Arrange
        FpsDwRepoQueryReq condition = new FpsDwRepoQueryReq();
        condition.setTradeDateStart(LocalDate.of(2024, 12, 31));
        condition.setTradeDateEnd(LocalDate.of(2024, 1, 1));
        
        // Act
        List<FpsDwRepoResp> result = fpsDwRepoService.export(condition);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
} 