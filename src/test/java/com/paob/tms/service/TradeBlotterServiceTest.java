package com.paob.tms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.TradeBlotterQueryReq;
import com.paob.tms.dto.resp.TradeBlotterResp;
import com.paob.tms.model.TradeBlotter;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TradeBlotterServiceTest {

    @InjectMocks
    private TradeBlotterService tradeBlotterService;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("查询交易清单列表 - 正常场景")
    void queryTradeBlotterList_ShouldReturnPage() {
        // Arrange
        TradeBlotterQueryReq req = new TradeBlotterQueryReq();
        req.setPageNum(1);
        req.setPageSize(10);
        
        // Act
        IPage<TradeBlotterResp> result = tradeBlotterService.queryTradeBlotterList(req);
        
        // Assert
        assertNotNull(result);
        verify(tradeBlotterService, times(1)).queryTradeBlotterList(req);
    }

    @Test
    @DisplayName("查询交易清单列表 - 空查询条件")
    void queryTradeBlotterList_WithEmptyQuery_ShouldReturnPage() {
        // Arrange
        TradeBlotterQueryReq req = new TradeBlotterQueryReq();
        
        // Act
        IPage<TradeBlotterResp> result = tradeBlotterService.queryTradeBlotterList(req);
        
        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("获取交易清单详情 - 正常场景")
    void getTradeBlotterDetail_ShouldReturnDetail() {
        // Arrange
        String tradeNumber = "TEST001";
        
        // Act
        TradeBlotterResp result = tradeBlotterService.getTradeBlotterDetail(tradeNumber);
        
        // Assert
        assertNotNull(result);
        verify(tradeBlotterService, times(1)).getTradeBlotterDetail(tradeNumber);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("获取交易清单详情 - 无效交易编号")
    void getTradeBlotterDetail_WithInvalidTradeNumber_ShouldReturnNull(String tradeNumber) {
        // Act
        TradeBlotterResp result = tradeBlotterService.getTradeBlotterDetail(tradeNumber);
        
        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("上传交易清单文件 - 正常场景")
    void uploadTradeBlotterFile_ShouldUploadSuccessfully() throws IOException {
        // Arrange
        MultipartFile file = new MockMultipartFile(
            "file",
            "test.xlsx",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "test data".getBytes()
        );
        
        // Act & Assert
        assertDoesNotThrow(() -> tradeBlotterService.uploadTradeBlotterFile(file));
        verify(tradeBlotterService, times(1)).uploadTradeBlotterFile(file);
    }

    @Test
    @DisplayName("上传交易清单文件 - 空文件")
    void uploadTradeBlotterFile_WithEmptyFile_ShouldThrowException() {
        // Arrange
        MultipartFile file = new MockMultipartFile(
            "file",
            "test.xlsx",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            new byte[0]
        );
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> tradeBlotterService.uploadTradeBlotterFile(file));
    }

    @Test
    @DisplayName("上传其他附件 - 正常场景")
    void uploadOtherAttachment_ShouldUploadSuccessfully() throws IOException {
        // Arrange
        MultipartFile file = new MockMultipartFile(
            "file",
            "test.pdf",
            "application/pdf",
            "test data".getBytes()
        );
        
        // Act & Assert
        assertDoesNotThrow(() -> tradeBlotterService.uploadOtherAttachment(file));
        verify(tradeBlotterService, times(1)).uploadOtherAttachment(file);
    }

    @Test
    @DisplayName("确认上传 - 正常场景")
    void confirmUpload_ShouldConfirmSuccessfully() {
        // Act & Assert
        assertDoesNotThrow(() -> tradeBlotterService.confirmUpload());
        verify(tradeBlotterService, times(1)).confirmUpload();
    }

    @Test
    @DisplayName("批量制证 - 正常场景")
    void makeVoucher_ShouldMakeSuccessfully() {
        // Arrange
        List<String> tradeNumbers = Arrays.asList("TEST001", "TEST002");
        
        // Act & Assert
        assertDoesNotThrow(() -> tradeBlotterService.makeVoucher(tradeNumbers));
        verify(tradeBlotterService, times(1)).makeVoucher(tradeNumbers);
    }

    @Test
    @DisplayName("批量制证 - 空列表")
    void makeVoucher_WithEmptyList_ShouldNotThrowException() {
        // Arrange
        List<String> tradeNumbers = Collections.emptyList();
        
        // Act & Assert
        assertDoesNotThrow(() -> tradeBlotterService.makeVoucher(tradeNumbers));
    }

    @Test
    @DisplayName("导出交易清单 - 正常场景")
    void exportTradeBlotter_ShouldExportSuccessfully() throws IOException {
        // Arrange
        TradeBlotterQueryReq req = new TradeBlotterQueryReq();
        
        // Act & Assert
        assertDoesNotThrow(() -> tradeBlotterService.exportTradeBlotter(req, response));
        verify(tradeBlotterService, times(1)).exportTradeBlotter(req, response);
    }

    @Test
    @DisplayName("导出交易清单 - 空查询条件")
    void exportTradeBlotter_WithEmptyQuery_ShouldExportSuccessfully() throws IOException {
        // Arrange
        TradeBlotterQueryReq req = new TradeBlotterQueryReq();
        
        // Act & Assert
        assertDoesNotThrow(() -> tradeBlotterService.exportTradeBlotter(req, response));
    }

    @Test
    @DisplayName("查询所有ISIN - 正常场景")
    void queryAllIsin_ShouldReturnList() {
        // Act
        List<String> result = tradeBlotterService.queryAllIsin();
        
        // Assert
        assertNotNull(result);
        verify(tradeBlotterService, times(1)).queryAllIsin();
    }

    @Test
    @DisplayName("查询所有ISIN - 空结果")
    void queryAllIsin_ShouldReturnEmptyList() {
        // Act
        List<String> result = tradeBlotterService.queryAllIsin();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
} 