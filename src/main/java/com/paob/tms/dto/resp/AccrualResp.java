package com.paob.tms.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 计提数据响应
 */
@Data
@ApiModel("计提数据响应")
public class AccrualResp {
    
    @ApiModelProperty("日期")
    private LocalDate date;
    
    @ApiModelProperty("备注类型")
    private String remark;
    
    @ApiModelProperty("交易编号")
    private String tradeNumber;
    
    @ApiModelProperty("实体ID")
    private String entityId;
    
    @ApiModelProperty("交易对手方")
    private String counterpart;
    
    @ApiModelProperty("币种")
    private String currency;
    
    @ApiModelProperty("价格")
    private BigDecimal price;
    
    @ApiModelProperty("HKD金额汇总")
    private BigDecimal sumOfHkdAmount;
    
    @ApiModelProperty("基础货币面值汇总")
    private BigDecimal sumOfNotionalInBaseCurrency;
    
    @ApiModelProperty("市场价值")
    private BigDecimal marketValue;
    
    @ApiModelProperty("债务证券名称")
    private String debtSecurityName;
    
    @ApiModelProperty("ISIN代码")
    private String isin;
    
    @ApiModelProperty("当日应计利息")
    private BigDecimal dailyAccruedInterest;
    
    @ApiModelProperty("已计提利息")
    private BigDecimal accruedInterest;
    
    @ApiModelProperty("计提利息制证状态")
    private String interestJournalStatus;
    
    @ApiModelProperty("计提利息凭证编号")
    private String interestJournalNo;
    
    @ApiModelProperty("当日估值变动")
    private BigDecimal dailyValuationChange;
    
    @ApiModelProperty("已计提估值变动")
    private BigDecimal accruedValuationChange;
    
    @ApiModelProperty("估值变动制证状态")
    private String valuationJournalStatus;
    
    @ApiModelProperty("估值变动凭证编号")
    private String valuationJournalNo;
    
    @ApiModelProperty("分摊溢折价制证状态")
    private String allocPremDiscJournalStatus;
    
    @ApiModelProperty("分摊溢折价凭证编号")
    private String allocPremDiscJournalNo;
    
    @ApiModelProperty("当日分摊溢折价")
    private BigDecimal dailyAllocPremDisc;
    
    @ApiModelProperty("已分摊溢折价")
    private BigDecimal allocPremDisc;
} 