package com.paob.tms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 计提数据实体
 */
@Data
@TableName("tms_accrual")
public class Accrual {
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 计提日期
     */
    @TableField("accrual_date")
    private LocalDate accrualDate;
    
    /**
     * 备注类型：Bond、CD、EFB、UST、Interbank、Fixed Deposit
     */
    @TableField("remark")
    private String remark;
    
    /**
     * 交易编号
     */
    @TableField("trade_number")
    private String tradeNumber;
    
    /**
     * Entity ID
     */
    @TableField("entity_id")
    private String entityId;
    
    /**
     * 交易对手方
     */
    @TableField("counterpart")
    private String counterpart;
    
    /**
     * 币种
     */
    @TableField("currency")
    private String currency;
    
    /**
     * 价格
     */
    @TableField("price")
    private BigDecimal price;
    
    /**
     * HKD金额汇总
     */
    @TableField("sum_of_hkd_amount")
    private BigDecimal sumOfHkdAmount;
    
    /**
     * 基础货币面值汇总
     */
    @TableField("sum_of_notional_in_base_currency")
    private BigDecimal sumOfNotionalInBaseCurrency;
    
    /**
     * 最终价格
     */
    @TableField("market_value")
    private BigDecimal marketValue;
    
    /**
     * 债务证券名称
     */
    @TableField("debt_security_name")
    private String debtSecurityName;
    
    /**
     * ISIN代码
     */
    @TableField("isin")
    private String isin;
    
    /**
     * 当日应计利息
     */
    @TableField("daily_accrued_interest")
    private BigDecimal dailyAccruedInterest;
    
    /**
     * 当日分摊溢折价
     */
    @TableField("daily_alloc_prem_disc")
    private BigDecimal dailyAllocPremDisc;
    
    /**
     * 已分摊溢折价
     */
    @TableField("alloc_prem_disc")
    private BigDecimal allocPremDisc;

    /**
     * 未分摊溢折价
     */
    @TableField("no_alloc_prem_disc")
    private BigDecimal noAllocPremDisc;
    
    /**
     * 已计提利息
     */
    @TableField("accrued_interest")
    private BigDecimal accruedInterest;
    
    /**
     * 计提利息制证状态
     */
    @TableField("interest_journal_status")
    private String interestJournalStatus;
    
    /**
     * 计提利息凭证编号
     */
    @TableField("interest_journal_no")
    private String interestJournalNo;
    
    /**
     * 分摊溢折价制证状态
     */
    @TableField("alloc_prem_disc_journal_status")
    private String allocPremDiscJournalStatus;
    
    /**
     * 分摊溢折价凭证编号
     */
    @TableField("alloc_prem_disc_journal_no")
    private String allocPremDiscJournalNo;
    
    /**
     * 当日估值变动
     */
    @TableField("daily_valuation_change")
    private BigDecimal dailyValuationChange;
    
    /**
     * 已计提估值变动
     */
    @TableField("accrued_valuation_change")
    private BigDecimal accruedValuationChange;
    
    /**
     * 估值变动制证状态
     */
    @TableField("valuation_journal_status")
    private String valuationJournalStatus;
    
    /**
     * 估值变动凭证编号
     */
    @TableField("valuation_journal_no")
    private String valuationJournalNo;
    
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