package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.*;
import com.paob.tms.dto.resp.JournalBatchCreateResp;
import com.paob.tms.dto.resp.JournalDetailResp;
import com.paob.tms.dto.resp.JournalResp;
import com.paob.tms.service.JournalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/journal")
@Tag(name = "凭证管理")
public class JournalController {

    @Autowired
    private JournalService journalService;

    @GetMapping("/list")
    @Operation(summary = "查询凭证列表")
    public IPage<JournalResp> queryJournalList(JournalQueryReq req) {
        log.info("查询凭证列表，请求参数：{}", req);
        return journalService.queryJournalList(req);
    }

    @GetMapping("/export")
    @Operation(summary = "导出凭证列表")
    public void exportJournalList(JournalQueryReq req, HttpServletResponse response) {
        log.info("导出凭证列表，请求参数：{}", req);
        journalService.exportJournalList(req, response);
    }

    @PostMapping("/approve")
    @Operation(summary = "复核通过")
    public void approveJournal(@RequestBody JournalApproveReq req) {
        log.info("复核通过，请求参数：{}", req);
        journalService.approveJournal(req);
    }

    @PostMapping("/unApprove")
    @Operation(summary = "复核不通过")
    public void unApproveJournal(@RequestBody JournalUnApproveReq req) {
        log.info("复核不通过请求参数：{}", req);
        journalService.unApproveJournal(req);
    }

    @PostMapping("/batch-reject")
    @Operation(summary = "批量复核拒绝")
    public String batchRejectJournal(@RequestBody JournalBatchRejectReq req) {
        log.info("批量复核拒绝请求参数：{}", req);
        return journalService.batchRejectJournal(req);
    }

    @PostMapping("/batch-approve")
    @Operation(summary = "批量复核通过")
    public String batchApproveJournal(@RequestBody JournalBatchApproveReq req) {
        log.info("批量复核通过请求参数：{}", req);
        return journalService.batchApproveJournal(req);
    }

    @GetMapping("/template")
    @Operation(summary = "下载凭证模板")
    public void downloadTemplate(HttpServletResponse response) {
        log.info("下载凭证模板");
        journalService.downloadTemplate(response);
    }

    @Operation(summary = "查询凭证详情")
    @GetMapping("/{journalSequence}")
    public List<JournalDetailResp> getJournalDetail(@PathVariable String journalSequence) {
        return journalService.getJournalDetail(journalSequence);
    }

    @Operation(summary = "编辑凭证")
    @PostMapping("/update")
    public void updateJournal(@RequestBody List<JournalDetailResp> req) {
        log.info("编辑凭证，请求参数：{}", req);
        journalService.updateJournal(req);
    }

    @Operation(summary = "导入凭证")
    @PostMapping("/import")
    public String importJournal(@RequestParam("file") MultipartFile file) {
        log.info("导入凭证，文件名：{}", file.getOriginalFilename());
        return journalService.importJournal(file);
    }

    /**
     * 批量制证
     * 根据传入的计提记录列表，为每条记录生成对应的凭证
     *
     * @param req 批量制证请求
     * @return 制证成功的凭证编号集合（去重）
     */
    @PostMapping("/batchCreate")
    public JournalBatchCreateResp batchCreateJournal(@RequestBody JournalBatchCreateReq req) {
        return journalService.batchCreateJournal(req);
    }

    /**
     * 制证初始化
     * 根据传入的业务ID列表，生成对应的凭证记录（不保存到数据库）
     *
     * @param req 制证初始化请求
     * @return 初始化后的凭证记录列表
     */
    @PostMapping("/init")
    @Operation(summary = "制证初始化")
    public List<JournalDetailResp> initJournal(@RequestBody JournalInitReq req) {
        return journalService.initJournal(req);
    }
} 