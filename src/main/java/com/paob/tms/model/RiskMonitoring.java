package com.paob.tms.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 风险监控实体类
 * 用于记录交易风险监控相关信息
 */
@Data
@TableName("tms_risk_monitoring")
public class RiskMonitoring {
    /**
     * 报告日期，相关数据生成报告的日期
     */
    private LocalDate reportingDate;
    
    /**
     * 备注，用于记录相关的额外说明或信息
     */
    private String remark;
    
    /**
     * 国际证券识别码，用于唯一标识证券
     */
    private String isin;
    
    /**
     * 货币种类，交易或数据涉及的货币类型
     */
    private String currency;
    
    /**
     * 价格，证券或交易资产的价格
     */
    private BigDecimal price;
    
    /**
     * 港元金额总和，以港元计量的相关金额总计
     */
    private BigDecimal sumOfHkdAmount;
    
    /**
     * 基础货币名义金额总和，以基础货币计量的名义金额总计
     */
    private BigDecimal sumOfNotionalInBaseCurrency;
    
    /**
     * 到期日，金融工具或交易结束的日期
     */
    private LocalDate maturityDate;
    
    /**
     * BGN计价的价格，可能是按保加利亚列弗（BGN）计算的价格
     */
    private BigDecimal priceBgn;
    
    /**
     * BVAL计价的价格，具体含义需结合业务背景确定计价方式
     */
    private BigDecimal priceBval;
    
    /**
     * BCHK计价的价格，具体计价相关的含义需结合业务背景
     */
    private BigDecimal priceBchk;
    
    /**
     * 最终价格，交易或资产确定的最终成交价格
     */
    private BigDecimal finalPrice;
    
    /**
     * 市场价值，资产在市场上的估值
     */
    private BigDecimal marketValue;
    
    /**
     * 修正久期，衡量债券价格对利率变动敏感性的指标
     */
    private BigDecimal modifiedDuration;
    
    /**
     * 期权调整利差久期（中间值），用于分析含权债券利率风险相关指标
     */
    private BigDecimal oasSpreadDurMid;
    
    /**
     * 基点价值，利率变动一个基点时债券价值的变动量
     */
    private BigDecimal dv01;
    
    /**
     * 信用利差01，衡量信用利差变动一个基点时债券价值的变动量
     */
    private BigDecimal cs01;
    
    /**
     * 当日至今（Day-To-Date）百分比变动，从当天开始到当前的变动比例
     */
    private BigDecimal dtdPercentChange;
    
    /**
     * 当月至今（Month-To-Date）百分比变动，从本月开始到当前的变动比例
     */
    private BigDecimal mtdPercentChange;
    
    /**
     * 年初至今（Year-To-Date）百分比变动，从年初开始到当前的变动比例
     */
    private BigDecimal ytdPercentChange;
    
    /**
     * 创建人
     */
    private String createdBy;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
    
    /**
     * 更新人
     */
    private String updatedBy;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;
} 