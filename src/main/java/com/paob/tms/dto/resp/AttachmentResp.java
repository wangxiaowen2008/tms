package com.paob.tms.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 附件响应DTO
 */
@Data
@ApiModel(description = "附件响应")
public class AttachmentResp {
    
    @ApiModelProperty(value = "附件ID")
    private Long id;
    
    @ApiModelProperty(value = "文档名称")
    private String documentName;
    
    @ApiModelProperty(value = "附件存放路径")
    private String filePath;
    
    @ApiModelProperty(value = "上传人")
    private String uploadBy;
    
    @ApiModelProperty(value = "上传时间")
    private LocalDateTime uploadTime;
    
    @ApiModelProperty(value = "附件类型")
    private String attachmentType;
    
    @ApiModelProperty(value = "业务主键")
    private String businessKey;
    
    @ApiModelProperty(value = "是否删除：0-未删除，1-已删除")
    private Integer isDeleted;
    
    @ApiModelProperty(value = "创建人")
    private String createdBy;
    
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdTime;
    
    @ApiModelProperty(value = "更新人")
    private String updatedBy;
    
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedTime;
} 