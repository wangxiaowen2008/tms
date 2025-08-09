package com.paob.tms.service;

import com.paob.tms.dto.req.SegmentValueReq;
import com.paob.tms.dto.resp.SelectOptionResp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SegmentOptionServiceTest {

    @InjectMocks
    private SegmentOptionService segmentOptionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("查询分段列表 - 正常场景")
    void querySegmentList_ShouldReturnList() {
        // Arrange
        SegmentValueReq req = new SegmentValueReq();
        req.setBookNo("HK_SOB");
        req.setSegmentName("SEGMENT2");
        req.setCodeOrName("TEST");
        
        // Act
        List<SelectOptionResp> result = segmentOptionService.querySegmentList(req);
        
        // Assert
        assertNotNull(result);
        verify(segmentOptionService, times(1)).querySegmentList(req);
    }

    @Test
    @DisplayName("查询分段列表 - 空查询条件")
    void querySegmentList_WithEmptyQuery_ShouldReturnList() {
        // Arrange
        SegmentValueReq req = new SegmentValueReq();
        
        // Act
        List<SelectOptionResp> result = segmentOptionService.querySegmentList(req);
        
        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("查询分段列表 - 空结果")
    void querySegmentList_ShouldReturnEmptyList() {
        // Arrange
        SegmentValueReq req = new SegmentValueReq();
        req.setBookNo("HK_SOB");
        req.setSegmentName("SEGMENT2");
        req.setCodeOrName("NON_EXISTENT_CODE");
        
        // Act
        List<SelectOptionResp> result = segmentOptionService.querySegmentList(req);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("查询分段列表 - 汇总段值")
    void querySegmentList_WithSummaryFlag_ShouldReturnList() {
        // Arrange
        SegmentValueReq req = new SegmentValueReq();
        req.setBookNo("HK_SOB");
        req.setSegmentName("SEGMENT2");
        req.setSummaryFlag("Y");
        
        // Act
        List<SelectOptionResp> result = segmentOptionService.querySegmentList(req);
        
        // Assert
        assertNotNull(result);
        verify(segmentOptionService, times(1)).querySegmentList(req);
    }
} 