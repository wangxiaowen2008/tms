package com.paob.tms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.SubjectBalanceQueryReq;
import com.paob.tms.model.TmsSubjectBalance;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TmsSubjectBalanceServiceTest {

    @InjectMocks
    private TmsSubjectBalanceService tmsSubjectBalanceService;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void querySubjectBalancePage_ShouldReturnPage() {
        // Arrange
        SubjectBalanceQueryReq queryDTO = new SubjectBalanceQueryReq();
        
        // Act
        IPage<TmsSubjectBalance> result = tmsSubjectBalanceService.querySubjectBalancePage(queryDTO);
        
        // Assert
        assertNotNull(result);
    }

    @Test
    void exportSubjectBalance_ShouldExportSuccessfully() {
        // Arrange
        SubjectBalanceQueryReq queryDTO = new SubjectBalanceQueryReq();
        
        // Act & Assert
        assertDoesNotThrow(() -> tmsSubjectBalanceService.exportSubjectBalance(queryDTO, response));
    }

    @Test
    void getSubjectBalanceDetail_ShouldReturnDetail() {
        // Arrange
        String codeCombinationId = "TEST001";
        
        // Act
        TmsSubjectBalance result = tmsSubjectBalanceService.getSubjectBalanceDetail(codeCombinationId);
        
        // Assert
        assertNotNull(result);
    }
} 