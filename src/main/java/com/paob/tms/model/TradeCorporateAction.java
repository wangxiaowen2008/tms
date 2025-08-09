package com.paob.tms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tms_corporate_action")
@Schema(description = "公司行为表")
public class TradeCorporateAction {

    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "公司行为编号，格式：EFB+5位序号，如EFB00001")
    private String actionNo;

    @Schema(description = "Trade Number，多条记录用换行符分隔")
    private String tradeNumbers;

    @Schema(description = "Entity ID")
    private String entityId;

    @Schema(description = "Counterpart")
    private String counterpart;

    @Schema(description = "公司行为类型：到期/利息")
    private String actionType;

    @Schema(description = "货币")
    private String currency;

    @Schema(description = "结算金额")
    private BigDecimal settlementAmount;

    @Schema(description = "结算日")
    private LocalDate settlementDate;

    @Schema(description = "备注，如EFB")
    private String remark;

    @Schema(description = "债券名称")
    private String debtSecurityName;

    @Schema(description = "ISIN代码")
    private String isin;

    @Schema(description = "结算账户")
    private String settlementAccount;

    @Schema(description = "经纪商")
    private String broker;

    @Schema(description = "付款状态：未收款/已收款")
    private String paymentStatus;

    @Schema(description = "制证状态：未制证/已制证")
    private String journalStatus;

    @Schema(description = "凭证编号")
    private String journalSequence;

    @Schema(description = "订单号")
    private String orderId;

    @Schema(description = "流水号")
    private String streamNo;

    @Schema(description = "本金金额")
    private BigDecimal principalAmount;

    @Schema(description = "利息金额")
    private BigDecimal interestAmount;

    @Schema(description = "本金+利息总金额")
    private BigDecimal totalAmount;

    @Schema(description = "已计提利息")
    private BigDecimal accruedInterest;

    @Schema(description = "已分摊溢折价")
    private BigDecimal allocPremDisc;

    @Schema(description = "已计提估值变动")
    private BigDecimal accruedValuationChange;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "更新人")
    private String updateBy;
} 