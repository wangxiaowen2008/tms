package com.paob.tms.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@TableName("tms_trade_blotter")
public class TradeBlotter {
    /**
     * 交易编号
     */
    @TableId(value = "trade_number", type = IdType.INPUT)
    @TableField("trade_number")
    private String tradeNumber;

    /**
     * 项目编号
     */
    @TableField("item")
    private Integer item;

    /**
     * 交易日期
     */
    @TableField("trade_date")
    private LocalDate tradeDate;

    /**
     * 交易时间
     */
    @TableField("trade_time")
    private LocalTime tradeTime;

    /**
     * 交易对手方
     */
    @TableField("counterpart")
    private String counterpart;

    /**
     * 买卖方向
     */
    @TableField("buy_sell_borrow_lend")
    private String buySellBorrowLend;

    /**
     * 收益率
     */
    @TableField("yield")
    private BigDecimal yield;

    /**
     * 货币
     */
    @TableField("currency")
    private String currency;

    /**
     * 结算金额
     */
    @TableField("settlement_amount")
    private BigDecimal settlementAmount;

    /**
     * 起息日
     */
    @TableField("value_date")
    private LocalDate valueDate;

    /**
     * 到期日
     */
    @TableField("maturity_date")
    private LocalDate maturityDate;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 天数计算
     */
    @TableField("day_count")
    private Integer dayCount;

    /**
     * 利息金额
     */
    @TableField("interest")
    private BigDecimal interest;

    /**
     * 价格
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 债务证券名称
     */
    @TableField("debt_security_name")
    private String debtSecurityName;

    /**
     * 国际证券识别码
     */
    @TableField("isin")
    private String isin;

    /**
     * 面值
     */
    @TableField("face_amount")
    private BigDecimal faceAmount;

    /**
     * 到期收到金额
     */
    @TableField("amount_received_at_maturity")
    private BigDecimal amountReceivedAtMaturity;

    /**
     * 预付利息
     */
    @TableField("interest_paid_upfront")
    private BigDecimal interestPaidUpfront;

    /**
     * 净利息
     */
    @TableField("net_interest")
    private BigDecimal netInterest;

    /**
     * 息票类型
     */
    @TableField("coupon_type")
    private String couponType;

    /**
     * 息票支付频率
     */
    @TableField("coupon_frequent")
    private String couponFrequent;

    /**
     * 交易渠道
     */
    @TableField("channel")
    private String channel;

    /**
     * 结算账户
     */
    @TableField("settlement_account")
    private String settlementAccount;

    /**
     * 经纪人
     */
    @TableField("broker")
    private String broker;

    /**
     * 利息accrual日期
     */
    @TableField("interest_accrual_date")
    private LocalDate interestAccrualDate;

    /**
     * 全称
     */
    @TableField("full_name")
    private String fullName;

    /**
     * 第二全称
     */
    @TableField("full_name_2")
    private String fullName2;

    /**
     * 分支机构
     */
    @TableField("branch")
    private String branch;

    /**
     * 第二分支机构
     */
    @TableField("branch2")
    private String branch2;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 系列
     */
    @TableField("series")
    private String series;

    /**
     * 匹配标识
     */
    @TableField("match")
    private String match;

    /**
     * 到期期限
     */
    @TableField("maturity")
    private String maturity;

    /**
     * 集团母公司
     */
    @TableField("group_parent")
    private String groupParent;

    /**
     * 港元金额
     */
    @TableField("hkd_amount")
    private BigDecimal hkdAmount;

    /**
     * 美元金额
     */
    @TableField("usd_amount")
    private BigDecimal usdAmount;

    /**
     * 交易日期与起息日对比
     */
    @TableField("trade_date_vs_value_date")
    private Integer tradeDateVsValueDate;

    /**
     * 英文名称
     */
    @TableField("english_name")
    private String englishName;

    /**
     * 隔离标识
     */
    @TableField("ring_fenced")
    private String ringFenced;

    /**
     * 距离上月末天数
     */
    @TableField("days_to_last_month_end")
    private Integer daysToLastMonthEnd;

    /**
     * 距离到期天数
     */
    @TableField("days_to_maturity")
    private Integer daysToMaturity;

    /**
     * 期限区间
     */
    @TableField("tenor_bucket")
    private String tenorBucket;

    /**
     * 利率期限区间
     */
    @TableField("ir_tenor_bucket")
    private String irTenorBucket;

    /**
     * 持有期限
     */
    @TableField("holdings_period")
    private String holdingsPeriod;

    /**
     * 排名
     */
    @TableField("rank")
    private String rank;

    /**
     * 港元利息
     */
    @TableField("interest_in_hkd")
    private BigDecimal interestInHkd;

    /**
     * 基础货币名义金额
     */
    @TableField("notional_in_base_currency")
    private BigDecimal notionalInBaseCurrency;

    /**
     * 类型
     */
    @TableField("type")
    private String type;

    /**
     * 中国银行相关标识
     */
    @TableField("boc")
    private String boc;

    /**
     * SCB银行相关标识
     */
    @TableField("scb")
    private String scb;

    /**
     * 汇丰银行相关标识
     */
    @TableField("hsbc")
    private String hsbc;

    /**
     * 中国银行业协会相关标识
     */
    @TableField("ccba")
    private String ccba;

    /**
     * 特定业务标识
     */
    @TableField("des")
    private String des;

    /**
     * 最大差异
     */
    @TableField("max_diff")
    private BigDecimal maxDiff;

    /**
     * 拍卖平均价
     */
    @TableField("auction_ave")
    private BigDecimal auctionAve;

    /**
     * 拍卖最高价
     */
    @TableField("auction_high")
    private BigDecimal auctionHigh;

    /**
     * 交易日
     */
    @TableField("t_day")
    private LocalDate tDay;

    /**
     * 旧交易编号
     */
    @TableField("trade_no_old")
    private String tradeNoOld;

    /**
     * 第二天数计算
     */
    @TableField("day_count_2")
    private Integer dayCount2;

    /**
     * 注册地国家(HD)
     */
    @TableField("country_of_domicile_hd")
    private String countryOfDomicileHd;

    /**
     * 注册地国家
     */
    @TableField("country_of_domicile")
    private String countryOfDomicile;

    /**
     * 彭博发行金额(HD)
     */
    @TableField("bbg_issued_amount_hd")
    private BigDecimal bbgIssuedAmountHd;

    /**
     * 彭博发行金额
     */
    @TableField("bbg_issued_amount")
    private BigDecimal bbgIssuedAmount;

    /**
     * 彭博私募金额(HD)
     */
    @TableField("bbg_private_placement_hd")
    private String bbgPrivatePlacementHd;

    /**
     * 彭博私募金额
     */
    @TableField("bbg_private_placement")
    private String bbgPrivatePlacement;

    /**
     * 下次息票支付日期(HC)
     */
    @TableField("next_coupon_date_hc")
    private LocalDate nextCouponDateHc;

    /**
     * 下次息票支付日期
     */
    @TableField("next_coupon_date")
    private LocalDate nextCouponDate;

    /**
     * 发行人评级
     */
    @TableField("issuer_rating")
    private String issuerRating;

    /**
     * 母公司编号
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 发行人国家
     */
    @TableField("issuer_country")
    private String issuerCountry;

    /**
     * 成立国家
     */
    @TableField("country_of_incorporation")
    private String countryOfIncorporation;

    /**
     * 报告日期
     */
    @TableField("report_date")
    private LocalDate reportDate;

    /**
     * 风险报告日期
     */
    @TableField("risk_report_date")
    private LocalDate riskReportDate;

    /**
     * 港元面值
     */
    @TableField("face_amount_in_hkd")
    private BigDecimal faceAmountInHkd;

    /**
     * 美元面值
     */
    @TableField("face_amount_in_usd")
    private BigDecimal faceAmountInUsd;

    /**
     * 国家评级
     */
    @TableField("country_rating")
    private String countryRating;

    /**
     * 金融工具
     */
    @TableField("instrument")
    private String instrument;

    /**
     * 资产类型排名
     */
    @TableField("asset_type_rank")
    private String assetTypeRank;

    /**
     * 记账
     */
    @TableField("booking")
    private String booking;

    /**
     * 月末记账检查
     */
    @TableField("month_end_booking_check")
    private String monthEndBookingCheck;

    /**
     * 下次利息
     */
    @TableField("next_interest")
    private BigDecimal nextInterest;

    /**
     * 实体ID
     */
    @TableField("entity_id")
    private String entityId;

    /**
     * 交易类型
     */
    @TableField(exist = false)
    private String tradeType;

    /**
     * 状态
     */
    @TableField(exist = false)
    private String status;

    /**
     * 付款状态
     */
    @TableField(exist = false)
    private String paymentStatus;

    /**
     * 制证状态
     */
    @TableField(exist = false)
    private String journalStatus;

    /**
     * 凭证编号
     */
    @TableField(exist = false)
    private String journalSequence;

    /**
     * 订单号
     */
    @TableField("order_id")
    private String orderId;

    /**
     * 流水号
     */
    @TableField("stream_No")
    private String streamNo;

    /**
     * 是否存在公司行为数据标识
     */
    @TableField(exist = false)
    private String existCompany;

    /**
     * 是否存在计提数据标识
     */
    @TableField(exist = false)
    private String existAccrual;

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