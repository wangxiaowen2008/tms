package com.paob.tms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.BankAccountBalanceQueryReq;
import com.paob.tms.dto.resp.BankAccountBalanceResp;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountBalanceServiceTest {

    @InjectMocks
    private BankAccountBalanceService bankAccountBalanceService;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("分页查询银行账号余额对账 - 正常场景")
    void queryBankAccountBalancePage_ShouldReturnPage() {
        // Arrange
        BankAccountBalanceQueryReq query = new BankAccountBalanceQueryReq();
        query.setAccountNumber("6222021234567890123");
        query.setDate(LocalDate.of(2024, 1, 1));
        
        // Act
        Page<BankAccountBalanceResp> result = bankAccountBalanceService.queryBankAccountBalancePage(query);
        
        // Assert
        assertNotNull(result);
        verify(bankAccountBalanceService, times(1)).queryBankAccountBalancePage(query);
    }

    @Test
    @DisplayName("分页查询银行账号余额对账 - 空查询条件")
    void queryBankAccountBalancePage_WithEmptyQuery_ShouldReturnPage() {
        // Arrange
        BankAccountBalanceQueryReq query = new BankAccountBalanceQueryReq();
        
        // Act
        Page<BankAccountBalanceResp> result = bankAccountBalanceService.queryBankAccountBalancePage(query);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getRecords().isEmpty());
    }

    @Test
    @DisplayName("分页查询银行账号余额对账 - 无效账号")
    void queryBankAccountBalancePage_WithInvalidAccount_ShouldReturnEmptyPage() {
        // Arrange
        BankAccountBalanceQueryReq query = new BankAccountBalanceQueryReq();
        query.setAccountNumber("invalid_account");
        query.setDate(LocalDate.of(2024, 1, 1));
        
        // Act
        Page<BankAccountBalanceResp> result = bankAccountBalanceService.queryBankAccountBalancePage(query);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getRecords().isEmpty());
    }

    @Test
    @DisplayName("导出银行账号余额对账 - 正常场景")
    void exportBankAccountBalance_ShouldExportSuccessfully() throws IOException {
        // Arrange
        BankAccountBalanceQueryReq query = new BankAccountBalanceQueryReq();
        query.setAccountNumber("6222021234567890123");
        query.setDate(LocalDate.of(2024, 1, 1));
        
        // Act & Assert
        assertDoesNotThrow(() -> bankAccountBalanceService.exportBankAccountBalance(query, response));
        verify(bankAccountBalanceService, times(1)).exportBankAccountBalance(query, response);
        verify(response, times(1)).setContentType("application/vnd.ms-excel");
    }

    @Test
    @DisplayName("导出银行账号余额对账 - 空查询条件")
    void exportBankAccountBalance_WithEmptyQuery_ShouldExportSuccessfully() {
        // Arrange
        BankAccountBalanceQueryReq query = new BankAccountBalanceQueryReq();
        
        // Act & Assert
        assertDoesNotThrow(() -> bankAccountBalanceService.exportBankAccountBalance(query, response));
        verify(response, times(1)).setContentType("application/vnd.ms-excel");
    }

    @Test
    @DisplayName("导出银行账号余额对账 - 无效账号")
    void exportBankAccountBalance_WithInvalidAccount_ShouldExportSuccessfully() {
        // Arrange
        BankAccountBalanceQueryReq query = new BankAccountBalanceQueryReq();
        query.setAccountNumber("invalid_account");
        query.setDate(LocalDate.of(2024, 1, 1));
        
        // Act & Assert
        assertDoesNotThrow(() -> bankAccountBalanceService.exportBankAccountBalance(query, response));
        verify(response, times(1)).setContentType("application/vnd.ms-excel");
    }
} 