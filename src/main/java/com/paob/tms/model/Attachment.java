package com.paob.tms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 附件实体类
 */
@Data
@TableName("tms_attachment")
public class Attachment {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 文档名称
     */
    @TableField("document_name")
    private String documentName;
    
    /**
     * 附件存放路径
     */
    @TableField("file_path")
    private String filePath;
    
    /**
     * 上传人
     */
    @TableField("upload_by")
    private String uploadBy;
    
    /**
     * 上传时间
     */
    @TableField("upload_time")
    private LocalDateTime uploadTime;
    
    /**
     * 附件类型
     */
    @TableField("attachment_type")
    private String attachmentType;
    
    /**
     * 业务主键
     */
    @TableField("business_key")
    private String businessKey;
    
    /**
     * 是否删除：0-未删除，1-已删除
     */
    @TableField("is_deleted")
    private Integer isDeleted;
    
    /**
     * 创建人
     */
    @TableField("created_by")
    private String createdBy;
    
    /**
     * 创建时间
     */
    @TableField("created_time")
    private LocalDateTime createdTime;
    
    /**
     * 更新人
     */
    @TableField("updated_by")
    private String updatedBy;
    
    /**
     * 更新时间
     */
    @TableField("updated_time")
    private LocalDateTime updatedTime;
} 