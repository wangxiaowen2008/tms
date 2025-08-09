package com.paob.tms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.AttachmentQueryReq;
import com.paob.tms.dto.resp.AttachmentResp;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 附件服务接口
 */
public interface AttachmentService {
    
    /**
     * 分页查询附件列表
     */
    IPage<AttachmentResp> queryAttachmentList(AttachmentQueryReq req);
    
    /**
     * 上传附件
     */
    AttachmentResp uploadAttachment(MultipartFile file, String attachmentType, String businessKey, String uploadBy) throws IOException;
    
    /**
     * 删除附件
     */
    void deleteAttachment(Long id, String updatedBy);
    
    /**
     * 下载附件
     */
    void downloadAttachment(Long id, HttpServletResponse response) throws IOException;
    
    /**
     * 打包下载附件
     */
    void downloadAttachmentPackage(String businessKey, HttpServletResponse response) throws IOException;
} 