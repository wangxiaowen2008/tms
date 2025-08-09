package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.AttachmentQueryReq;
import com.paob.tms.dto.resp.AttachmentResp;
import com.paob.tms.service.AttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 附件控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/attachment")
@Tag(name = "附件管理", description = "附件相关接口")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/list")
    @Operation(summary = "查询附件列表")
    public IPage<AttachmentResp> queryAttachmentList(@RequestBody AttachmentQueryReq req) {
        return attachmentService.queryAttachmentList(req);
    }

    @PostMapping("/upload")
    @Operation(summary = "上传附件")
    public AttachmentResp uploadAttachment(@RequestParam("file") MultipartFile file,
                                         @RequestParam String attachmentType,
                                         @RequestParam String businessKey,
                                         @RequestParam String uploadBy) throws IOException {
        return attachmentService.uploadAttachment(file, attachmentType, businessKey, uploadBy);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除附件")
    public void deleteAttachment(@PathVariable Long id, @RequestParam String updatedBy) {
        attachmentService.deleteAttachment(id, updatedBy);
    }

    @GetMapping("/download/{id}")
    @Operation(summary = "下载附件")
    public void downloadAttachment(@PathVariable Long id, HttpServletResponse response) throws IOException {
        attachmentService.downloadAttachment(id, response);
    }

    @GetMapping("/{businessKey}")
    @Operation(summary = "打包下载附件")
    public void downloadAttachmentPackage(@PathVariable String businessKey, HttpServletResponse response) throws IOException {
        attachmentService.downloadAttachmentPackage(businessKey, response);
    }
} 