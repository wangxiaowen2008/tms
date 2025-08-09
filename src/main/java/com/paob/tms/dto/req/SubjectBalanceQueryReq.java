package com.paob.tms.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 科目余额查询请求参数
 */
@Data
@ApiModel("科目余额查询请求参数")
public class SubjectBalanceQueryReq {
    
    /**
     * 账套代码
     * 默认值：HK SOB
     */
    @ApiModelProperty(value = "账套代码", example = "HK SOB")
    private String setOfBooksId;
    
    /**
     * 会计期间
     * 格式：YYYY-MM
     */
    @ApiModelProperty(value = "会计期间", example = "2024-04")
    private String periodName;
    
    /**
     * 公司段
     * 默认值：800000
     */
    @ApiModelProperty(value = "公司段", example = "800000")
    private String segment1;
    
    /**
     * 业务段
     * 默认值：0000
     */
    @ApiModelProperty(value = "业务段", example = "0000")
    private String segment2;
    
    /**
     * 成本中心
     * 默认值：0000
     */
    @ApiModelProperty(value = "成本中心", example = "0000")
    private String segment3;
    
    /**
     * 产品段
     * 默认值：000000
     */
    @ApiModelProperty(value = "产品段", example = "000000")
    private String segment4;
    
    /**
     * 科目
     * 对应数据库字段：acc_code3
     */
    @ApiModelProperty(value = "科目", example = "1001")
    private String segment5;
    
    /**
     * 子目
     * 默认值：000000
     */
    @ApiModelProperty(value = "子目", example = "000000")
    private String segment6;
    
    /**
     * 备用段1
     * 默认值：0000
     */
    @ApiModelProperty(value = "备用段1", example = "0000")
    private String segment7;
    
    /**
     * 关联方
     * 默认值：0000
     */
    @ApiModelProperty(value = "关联方", example = "0000")
    private String segment8;
    
    /**
     * 备用段3
     * 默认值：0000
     */
    @ApiModelProperty(value = "备用段3", example = "0000")
    private String segment9;
    
    /**
     * 备用段4
     * 默认值：0000
     */
    @ApiModelProperty(value = "备用段4", example = "0000")
    private String segment10;
    
    /**
     * 备用段5
     * 默认值：0000
     */
    @ApiModelProperty(value = "备用段5", example = "0000")
    private String segment11;
    
    /**
     * 备用段6
     * 默认值：0000
     */
    @ApiModelProperty(value = "备用段6", example = "0000")
    private String segment12;
    
    /**
     * 备用段7
     * 默认值：0000
     */
    @ApiModelProperty(value = "备用段7", example = "0000")
    private String segment13;
    
    /**
     * 备用段8
     * 默认值：0000
     */
    @ApiModelProperty(value = "备用段8", example = "0000")
    private String segment14;
    
    /**
     * 币种
     * 默认值：HKD
     */
    @ApiModelProperty(value = "币种", example = "HKD")
    private String curNo;
    
    /**
     * 页码
     * 默认值：1
     */
    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNum = 1;
    
    /**
     * 每页条数
     * 默认值：10
     */
    @ApiModelProperty(value = "每页条数", example = "10")
    private Integer pageSize = 10;
} 