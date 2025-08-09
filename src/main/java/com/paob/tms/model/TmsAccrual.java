package com.paob.tms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("tms_accrual")
public class TmsAccrual {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 日期
     */
    private LocalDate accrualDate;
    
    /**
     * 备注类型：Bond、CD、EFB、UST、Interbank、Fixed Deposit
     */
    private String remark;
    
    /**
     * 交易编号
     */
    private String tradeNumber;
    
    /**
     * Entity ID
     */
    private String entityId;
    
    /**
     * 交易对手方
     */
    private String counterpart;
    
    /**
     * 币种
     */
    private String currency;
    
    /**
     * 价格
     */
    private BigDecimal price;
    
    /**
     * HKD金额汇总
     */
    private BigDecimal sumOfHkdAmount;
    
    /**
     * 基础货币面值汇总
     */
    private BigDecimal sumOfNotionalInBaseCurrency;
    
    /**
     * 最终价格
     */
    private BigDecimal marketValue;
    
    /**
     * 债务证券名称
     */
    private String debtSecurityName;
    
    /**
     * ISIN代码
     */
    private String isin;
    
    /**
     * 当日应计利息
     */
    private BigDecimal dailyAccruedInterest;
    
    /**
     * 当日分摊溢折价
     */
    private BigDecimal dailyAllocPremDisc;
    
    /**
     * 已分摊溢折价
     */
    private BigDecimal allocPremDisc;
    
    /**
     * 未分摊溢折价
     */
    private BigDecimal noAllocPremDisc;
    
    /**
     * 已计提利息
     */
    private BigDecimal accruedInterest;
    
    /**
     * 计提利息制证状态
     */
    private String interestJournalStatus;
    
    /**
     * 计提利息凭证编号
     */
    private String interestJournalNo;
    
    /**
     * 分摊溢折价制证状态
     */
    private String allocPremDiscJournalStatus;
    
    /**
     * 分摊溢折价凭证编号
     */
    private String allocPremDiscJournalNo;
    
    /**
     * 当日估值变动
     */
    private BigDecimal dailyValuationChange;
    
    /**
     * 已计提估值变动
     */
    private BigDecimal accruedValuationChange;
    
    /**
     * 估值变动制证状态
     */
    private String valuationJournalStatus;
    
    /**
     * 估值变动凭证编号
     */
    private String valuationJournalNo;
    
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