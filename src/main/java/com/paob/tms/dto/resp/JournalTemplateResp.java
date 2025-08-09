package com.paob.tms.dto.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(description = "凭证模板响应")
public class JournalTemplateResp {
    
    @ExcelProperty("凭证编号")
    private String journalSequence;
    
    @ExcelProperty("凭证日期")
    private String effectiveDate;
    
    @ExcelProperty("账簿编号")
    private String booksNo;
    
    @ExcelProperty("凭证名称")
    private String journalName;
    
    @ExcelProperty("币种")
    private String curNo;
    
    @ExcelProperty("借方金额")
    private String enteredDr;
    
    @ExcelProperty("贷方金额")
    private String enteredCr;
    
    @ApiModelProperty("模板明细列表")
    private List<JournalTemplateDetailResp> subList;
    
    @ApiModelProperty("备注类型")
    private String remark;
    
    @ApiModelProperty("模板类型")
    private String tempType;
    
    @ApiModelProperty("流水类型")
    private String streamType;
    
    @ApiModelProperty("模板名称")
    private String tempName;

    @ApiModelProperty("公司段")
    private String segment1;

    @ApiModelProperty("模板编号")
    private Long tempNo;
    
    @ApiModelProperty("更新人")
    private String updatedBy;
    
    @ApiModelProperty("更新时间")
    private LocalDateTime updatedDate;
} 