package com.paob.tms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.SubjectBalanceDetailQueryReq;
import com.paob.tms.dto.resp.SubjectBalanceDetailResp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SubjectBalanceDetailServiceTest {

    @InjectMocks
    private SubjectBalanceDetailService subjectBalanceDetailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("查询科目余额详情 - 正常场景")
    void querySubjectBalanceDetailPage_ShouldReturnPage() {
        // Arrange
        SubjectBalanceDetailQueryReq query = new SubjectBalanceDetailQueryReq();
        query.setSetOfBooksId("HK_SOB");
        query.setPeriodName("2024-04");
        query.setSegment1("800000");
        query.setSegment2("0000");
        query.setSegment3("0000");
        query.setSegment4("000000");
        query.setSegment5("1001");
        query.setSegment6("000000");
        query.setSegment7("0000");
        query.setSegment8("0000");
        query.setCurNo("HKD");
        query.setPageNum(1);
        query.setPageSize(10);
        
        // Act
        Page<SubjectBalanceDetailResp> result = subjectBalanceDetailService.querySubjectBalanceDetailPage(query);
        
        // Assert
        assertNotNull(result);
        verify(subjectBalanceDetailService, times(1)).querySubjectBalanceDetailPage(query);
    }

    @Test
    @DisplayName("查询科目余额详情 - 空查询条件")
    void querySubjectBalanceDetailPage_WithEmptyQuery_ShouldReturnPage() {
        // Arrange
        SubjectBalanceDetailQueryReq query = new SubjectBalanceDetailQueryReq();
        
        // Act
        Page<SubjectBalanceDetailResp> result = subjectBalanceDetailService.querySubjectBalanceDetailPage(query);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.getCurrent());
        assertEquals(10, result.getSize());
    }

    @Test
    @DisplayName("查询科目余额详情 - 空结果")
    void querySubjectBalanceDetailPage_ShouldReturnEmptyPage() {
        // Arrange
        SubjectBalanceDetailQueryReq query = new SubjectBalanceDetailQueryReq();
        query.setSetOfBooksId("NON_EXISTENT_BOOK");
        query.setPeriodName("2024-04");
        
        // Act
        Page<SubjectBalanceDetailResp> result = subjectBalanceDetailService.querySubjectBalanceDetailPage(query);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getRecords().isEmpty());
    }
} 