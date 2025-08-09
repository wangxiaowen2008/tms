package com.paob.tms.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 附件查询请求DTO
 */
@Data
@ApiModel(description = "附件查询请求")
public class AttachmentQueryReq {
    
    @ApiModelProperty(value = "附件类型")
    private String attachmentType;
    
    @ApiModelProperty(value = "业务主键")
    private String businessKey;
    
    @ApiModelProperty(value = "上传人")
    private String uploadBy;
    
    @ApiModelProperty(value = "创建时间开始")
    private LocalDateTime createdTimeStart;
    
    @ApiModelProperty(value = "创建时间结束")
    private LocalDateTime createdTimeEnd;
    
    @ApiModelProperty(value = "当前页码")
    private Integer pageNum;
    
    @ApiModelProperty(value = "每页条数")
    private Integer pageSize;
} 