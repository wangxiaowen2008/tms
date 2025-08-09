package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.JournalTemplateAddReq;
import com.paob.tms.dto.req.JournalTemplateQueryReq;
import com.paob.tms.dto.resp.JournalTemplateResp;
import com.paob.tms.service.JournalTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/journal-template")
@Tag(name = "凭证模板管理")
public class JournalTemplateController {

    @Autowired
    private JournalTemplateService journalTemplateService;

    @GetMapping("/list")
    @Operation(summary = "查询凭证模板列表")
    public IPage<JournalTemplateResp> queryJournalTemplateList(JournalTemplateQueryReq req) {
        return journalTemplateService.queryJournalTemplateList(req);
    }

    @PostMapping("/add")
    @Operation(summary = "新增凭证模板")
    public void addJournalTemplate(@RequestBody JournalTemplateAddReq req) {
        log.info("新增凭证模板，请求参数：{}", req);
        journalTemplateService.addJournalTemplate(req);
    }

    @PostMapping("/update")
    @Operation(summary = "编辑凭证模板")
    public void updateJournalTemplate(@RequestBody JournalTemplateAddReq req) {
        log.info("编辑凭证模板，请求参数：{}", req);
        journalTemplateService.updateJournalTemplate(req);
    }

    @GetMapping("/{tempName}")
    @Operation(summary = "获取凭证模板详情")
    public List<JournalTemplateResp> getJournalTemplateDetail(@PathVariable String tempName) {
        log.info("获取凭证模板详情，模板名称：{}", tempName);
        return journalTemplateService.getJournalTemplateDetail(tempName);
    }

    @PostMapping("/{tempName}/submit")
    @Operation(summary = "提交凭证模板")
    public void submitJournalTemplate(@PathVariable String tempName) {
        log.info("提交凭证模板，模板名称：{}", tempName);
        journalTemplateService.submitJournalTemplate(tempName);
    }

    @PostMapping("/{tempName}/review")
    @Operation(summary = "复核通过凭证模板")
    public void approveJournalTemplate(@PathVariable String tempName) {
        log.info("复核凭证模板，模板名称：{}", tempName);
        journalTemplateService.approveJournalTemplate(tempName);
    }

    @PostMapping("/{tempName}/reject")
    @Operation(summary = "复核拒绝凭证模板")
    public void rejectJournalTemplate(@PathVariable String tempName) {
        log.info("复核拒绝凭证模板，模板名称：{}", tempName);
        journalTemplateService.rejectJournalTemplate(tempName);
    }

    @Operation(summary = "获取复核通过状态的模板名称列表")
    @GetMapping("/approved-names")
    public List<String> getApprovedTemplateNames() {
        log.info("获取复核通过状态的模板名称列表");
        return journalTemplateService.getApprovedTemplateNames();
    }
} 