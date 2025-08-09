package com.paob.tms.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "凭证模板明细响应")
public class JournalTemplateDetailResp {
    
    @ApiModelProperty("账户贷方金额")
    private String accountedCr;
    
    @ApiModelProperty("账户借方金额")
    private String accountedDr;
    
    @ApiModelProperty("账套编号")
    private String booksNo;
    
    @ApiModelProperty("凭证模板ID")
    private String citJournalTempId;
    
    @ApiModelProperty("创建人")
    private String createdBy;
    
    @ApiModelProperty("创建时间")
    private LocalDateTime createdDate;
    
    @ApiModelProperty("数据状态")
    private String dataState;
    
    @ApiModelProperty("方向：D-贷方，G-借方")
    private String direction;
    
    @ApiModelProperty("生效日期")
    private LocalDateTime effectiveDate;
    
    @ApiModelProperty("贷方金额")
    private String enteredCr;
    
    @ApiModelProperty("借方金额")
    private String enteredDr;
    
    @ApiModelProperty("分录描述")
    private String journalLineDescription;
    
    @ApiModelProperty("支付方向")
    private String payDirection;
    
    @ApiModelProperty("报告币贷方金额")
    private BigDecimal pEnteredCr;
    
    @ApiModelProperty("报告币借方金额")
    private BigDecimal pEnteredDr;
    
    @ApiModelProperty("公司段")
    private String segment1;
    
    @ApiModelProperty("业务段")
    private String segment2;
    
    @ApiModelProperty("成本中心")
    private String segment3;
    
    @ApiModelProperty("产品段")
    private String segment4;
    
    @ApiModelProperty("科目")
    private String segment5;
    
    @ApiModelProperty("子目")
    private String segment6;
    
    @ApiModelProperty("备用段1")
    private String segment7;
    
    @ApiModelProperty("备用段2（关联方）")
    private String segment8;
    
    @ApiModelProperty("流水类型")
    private String streamType;
    
    @ApiModelProperty("模板类别")
    private String tempCategory;
    
    @ApiModelProperty("模板名称")
    private String tempName;
    
    @ApiModelProperty("模板编号")
    private Long tempNo;
    
    @ApiModelProperty("更新人")
    private String updatedBy;
    
    @ApiModelProperty("更新时间")
    private LocalDateTime updatedDate;
} 