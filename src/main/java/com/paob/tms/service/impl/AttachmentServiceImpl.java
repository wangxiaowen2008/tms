package com.paob.tms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paob.tms.dto.req.AttachmentQueryReq;
import com.paob.tms.dto.resp.AttachmentResp;
import com.paob.tms.mapper.AttachmentMapper;
import com.paob.tms.model.Attachment;
import com.paob.tms.service.AttachmentService;
import com.paob.tms.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 附件服务实现类
 */
@Slf4j
@Service
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements AttachmentService {

    @Value("${file.upload.path}")
    private String uploadPath;

    @Override
    public IPage<AttachmentResp> queryAttachmentList(AttachmentQueryReq req) {
        Page<Attachment> page = new Page<>(req.getPageNum(), req.getPageSize());
        IPage<Attachment> attachmentPage = baseMapper.selectPage(page,
                req.getAttachmentType(),
                req.getBusinessKey(),
                req.getUploadBy(),
                req.getCreatedTimeStart(),
                req.getCreatedTimeEnd());
        
        return attachmentPage.convert(attachment -> {
            AttachmentResp resp = new AttachmentResp();
            BeanUtil.copyProperties(attachment, resp);
            return resp;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AttachmentResp uploadAttachment(MultipartFile file, String attachmentType, String businessKey, String uploadBy) throws IOException {
        // 创建上传目录
        Path uploadDir = Paths.get(uploadPath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // 生成文件名和路径
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + fileExtension;
        Path filePath = uploadDir.resolve(fileName);

        // 保存文件
        Files.copy(file.getInputStream(), filePath);

        // 保存附件信息
        Attachment attachment = new Attachment();
        attachment.setDocumentName(originalFilename);
        attachment.setFilePath(filePath.toString());
        attachment.setAttachmentType(attachmentType);
        attachment.setBusinessKey(businessKey);
        attachment.setUploadBy(uploadBy);
        attachment.setUploadTime(LocalDateTime.now());
        attachment.setIsDeleted(0);
        attachment.setCreatedBy(uploadBy);
        attachment.setCreatedTime(LocalDateTime.now());
        baseMapper.insert(attachment);

        // 转换为响应对象
        AttachmentResp resp = new AttachmentResp();
        BeanUtil.copyProperties(attachment, resp);
        return resp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAttachment(Long id, String updatedBy) {
        Attachment attachment = baseMapper.selectById(id);
        if (attachment != null) {
            // 删除文件
            Path filePath = Paths.get(attachment.getFilePath());
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                log.error("删除文件失败", e);
            }
            
            // 更新记录为已删除
            attachment.setIsDeleted(1);
            attachment.setUpdatedBy(updatedBy);
            attachment.setUpdatedTime(LocalDateTime.now());
            baseMapper.updateById(attachment);
        }
    }

    @Override
    public void downloadAttachment(Long id, HttpServletResponse response) throws IOException {
        Attachment attachment = baseMapper.selectById(id);
        if (attachment != null && attachment.getIsDeleted() == 0) {
            Path filePath = Paths.get(attachment.getFilePath());
            if (Files.exists(filePath)) {
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + attachment.getDocumentName());
                
                try (InputStream is = Files.newInputStream(filePath);
                     OutputStream os = response.getOutputStream()) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        os.write(buffer, 0, len);
                    }
                }
            }
        }
    }

    @Override
    public void downloadAttachmentPackage(String businessKey, HttpServletResponse response) throws IOException {
        List<Attachment> attachments = lambdaQuery()
                .eq(Attachment::getBusinessKey, businessKey)
                .eq(Attachment::getIsDeleted, 0)
                .list();
        
        if (!attachments.isEmpty()) {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=attachments.zip");
            
            try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
                for (Attachment attachment : attachments) {
                    Path filePath = Paths.get(attachment.getFilePath());
                    if (Files.exists(filePath)) {
                        ZipEntry zipEntry = new ZipEntry(attachment.getDocumentName());
                        zos.putNextEntry(zipEntry);
                        
                        try (InputStream is = Files.newInputStream(filePath)) {
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = is.read(buffer)) != -1) {
                                zos.write(buffer, 0, len);
                            }
                        }
                        zos.closeEntry();
                    }
                }
            }
        }
    }
} 