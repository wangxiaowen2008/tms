package com.paob.tms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.JournalTemplateAddReq;
import com.paob.tms.dto.req.JournalTemplateQueryReq;
import com.paob.tms.dto.resp.JournalTemplateResp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JournalTemplateServiceTest {

    @InjectMocks
    private JournalTemplateService journalTemplateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("查询凭证模板列表 - 正常场景")
    void queryJournalTemplateList_ShouldReturnPage() {
        // Arrange
        JournalTemplateQueryReq req = new JournalTemplateQueryReq();
        req.setTemplateName("测试模板");
        req.setPageNum(1);
        req.setPageSize(10);
        
        // Act
        IPage<JournalTemplateResp> result = journalTemplateService.queryJournalTemplateList(req);
        
        // Assert
        assertNotNull(result);
        verify(journalTemplateService, times(1)).queryJournalTemplateList(req);
    }

    @Test
    @DisplayName("查询凭证模板列表 - 空查询条件")
    void queryJournalTemplateList_WithEmptyQuery_ShouldReturnPage() {
        // Arrange
        JournalTemplateQueryReq req = new JournalTemplateQueryReq();
        
        // Act
        IPage<JournalTemplateResp> result = journalTemplateService.queryJournalTemplateList(req);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getRecords().isEmpty());
    }

    @Test
    @DisplayName("新增凭证模板 - 正常场景")
    void addJournalTemplate_ShouldAddSuccessfully() {
        // Arrange
        JournalTemplateAddReq req = new JournalTemplateAddReq();
        JournalTemplateAddReq.JournalTemp temp = new JournalTemplateAddReq.JournalTemp();
        temp.setTempName("测试模板");
        temp.setBooksNo("HK_SOB");
        temp.setRemark("Bond");
        temp.setTempType("PAOB Buy");
        temp.setStreamType("付款");
        temp.setDirection("G");
        temp.setSegment1("800000");
        temp.setSegment2("0000");
        temp.setSegment3("0000");
        temp.setSegment4("000000");
        temp.setSegment5("1001000000");
        temp.setSegment6("000000");
        temp.setSegment8("0000");
        req.setJournalTempList(Collections.singletonList(temp));
        
        // Act & Assert
        assertDoesNotThrow(() -> journalTemplateService.addJournalTemplate(req));
        verify(journalTemplateService, times(1)).addJournalTemplate(req);
    }

    @Test
    @DisplayName("新增凭证模板 - 模板名称为空")
    void addJournalTemplate_WithEmptyTempName_ShouldThrowException() {
        // Arrange
        JournalTemplateAddReq req = new JournalTemplateAddReq();
        JournalTemplateAddReq.JournalTemp temp = new JournalTemplateAddReq.JournalTemp();
        temp.setBooksNo("HK_SOB");
        temp.setRemark("Bond");
        temp.setTempType("PAOB Buy");
        temp.setStreamType("付款");
        req.setJournalTempList(Collections.singletonList(temp));
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> journalTemplateService.addJournalTemplate(req));
    }

    @Test
    @DisplayName("新增凭证模板 - 模板列表为空")
    void addJournalTemplate_WithEmptyList_ShouldThrowException() {
        // Arrange
        JournalTemplateAddReq req = new JournalTemplateAddReq();
        req.setJournalTempList(Collections.emptyList());
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> journalTemplateService.addJournalTemplate(req));
    }

    @Test
    @DisplayName("编辑凭证模板 - 正常场景")
    void updateJournalTemplate_ShouldUpdateSuccessfully() {
        // Arrange
        JournalTemplateAddReq req = new JournalTemplateAddReq();
        JournalTemplateAddReq.JournalTemp temp = new JournalTemplateAddReq.JournalTemp();
        temp.setTmsCitJournalTempId("TEST_ID");
        temp.setTempName("测试模板");
        temp.setBooksNo("HK_SOB");
        temp.setRemark("Bond");
        temp.setTempType("PAOB Buy");
        temp.setStreamType("付款");
        temp.setDirection("G");
        temp.setSegment1("800000");
        temp.setSegment2("0000");
        temp.setSegment3("0000");
        temp.setSegment4("000000");
        temp.setSegment5("1001000000");
        temp.setSegment6("000000");
        temp.setSegment8("0000");
        req.setJournalTempList(Collections.singletonList(temp));
        
        // Act & Assert
        assertDoesNotThrow(() -> journalTemplateService.updateJournalTemplate(req));
        verify(journalTemplateService, times(1)).updateJournalTemplate(req);
    }

    @Test
    @DisplayName("编辑凭证模板 - 模板名称为空")
    void updateJournalTemplate_WithEmptyTempName_ShouldThrowException() {
        // Arrange
        JournalTemplateAddReq req = new JournalTemplateAddReq();
        JournalTemplateAddReq.JournalTemp temp = new JournalTemplateAddReq.JournalTemp();
        temp.setTmsCitJournalTempId("TEST_ID");
        temp.setBooksNo("HK_SOB");
        temp.setRemark("Bond");
        temp.setTempType("PAOB Buy");
        temp.setStreamType("付款");
        req.setJournalTempList(Collections.singletonList(temp));
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> journalTemplateService.updateJournalTemplate(req));
    }

    @Test
    @DisplayName("编辑凭证模板 - 模板列表为空")
    void updateJournalTemplate_WithEmptyList_ShouldThrowException() {
        // Arrange
        JournalTemplateAddReq req = new JournalTemplateAddReq();
        req.setJournalTempList(Collections.emptyList());
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> journalTemplateService.updateJournalTemplate(req));
    }

    @Test
    @DisplayName("获取凭证模板详情 - 正常场景")
    void getJournalTemplateDetail_ShouldReturnDetail() {
        // Arrange
        String templateName = "测试模板";
        
        // Act
        List<JournalTemplateResp> result = journalTemplateService.getJournalTemplateDetail(templateName);
        
        // Assert
        assertNotNull(result);
        verify(journalTemplateService, times(1)).getJournalTemplateDetail(templateName);
    }

    @Test
    @DisplayName("获取凭证模板详情 - 无效模板名称")
    void getJournalTemplateDetail_WithInvalidName_ShouldThrowException() {
        // Arrange
        String templateName = "INVALID_TEMPLATE";
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> journalTemplateService.getJournalTemplateDetail(templateName));
    }

    @Test
    @DisplayName("提交凭证模板 - 正常场景")
    void submitJournalTemplate_ShouldSubmitSuccessfully() {
        // Arrange
        String templateName = "测试模板";
        
        // Act & Assert
        assertDoesNotThrow(() -> journalTemplateService.submitJournalTemplate(templateName));
        verify(journalTemplateService, times(1)).submitJournalTemplate(templateName);
    }

    @Test
    @DisplayName("提交凭证模板 - 无效模板名称")
    void submitJournalTemplate_WithInvalidName_ShouldThrowException() {
        // Arrange
        String templateName = "INVALID_TEMPLATE";
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> journalTemplateService.submitJournalTemplate(templateName));
    }

    @Test
    @DisplayName("复核凭证模板 - 正常场景")
    void approveJournalTemplate_ShouldApproveSuccessfully() {
        // Arrange
        String templateName = "测试模板";
        
        // Act & Assert
        assertDoesNotThrow(() -> journalTemplateService.approveJournalTemplate(templateName));
        verify(journalTemplateService, times(1)).approveJournalTemplate(templateName);
    }

    @Test
    @DisplayName("复核凭证模板 - 无效模板名称")
    void approveJournalTemplate_WithInvalidName_ShouldThrowException() {
        // Arrange
        String templateName = "INVALID_TEMPLATE";
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> journalTemplateService.approveJournalTemplate(templateName));
    }

    @Test
    @DisplayName("复核拒绝凭证模板 - 正常场景")
    void rejectJournalTemplate_ShouldRejectSuccessfully() {
        // Arrange
        String templateName = "测试模板";
        
        // Act & Assert
        assertDoesNotThrow(() -> journalTemplateService.rejectJournalTemplate(templateName));
        verify(journalTemplateService, times(1)).rejectJournalTemplate(templateName);
    }

    @Test
    @DisplayName("复核拒绝凭证模板 - 无效模板名称")
    void rejectJournalTemplate_WithInvalidName_ShouldThrowException() {
        // Arrange
        String templateName = "INVALID_TEMPLATE";
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> journalTemplateService.rejectJournalTemplate(templateName));
    }

    @Test
    @DisplayName("获取复核通过状态的模板名称列表 - 正常场景")
    void getApprovedTemplateNames_ShouldReturnList() {
        // Act
        List<String> result = journalTemplateService.getApprovedTemplateNames();
        
        // Assert
        assertNotNull(result);
        verify(journalTemplateService, times(1)).getApprovedTemplateNames();
    }
} 