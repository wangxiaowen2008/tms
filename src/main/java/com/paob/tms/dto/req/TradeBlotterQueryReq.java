package com.paob.tms.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDate;
import java.math.BigDecimal;

/**
 * 交易流水查询请求
 */
@ApiModel(description = "交易流水查询请求")
@Data
public class TradeBlotterQueryReq {
    /**
     * 页码
     */
    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    @ApiModelProperty(value = "每页大小", example = "10")
    private Integer pageSize = 10;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 交易日期开始
     */
    @ApiModelProperty(value = "交易日期开始")
    private LocalDate tradeDateStart;

    /**
     * 交易日期结束
     */
    @ApiModelProperty(value = "交易日期结束")
    private LocalDate tradeDateEnd;

    /**
     * 货币
     */
    @ApiModelProperty(value = "货币")
    private String currency;

    /**
     * 实体ID
     */
    @ApiModelProperty(value = "实体ID")
    private String entityId;

    /**
     * 交易类型
     */
    @ApiModelProperty(value = "交易类型")
    private String tradeType;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 买卖方向
     */
    @ApiModelProperty(value = "买卖方向")
    private String buySellBorrowLend;

    /**
     * 交易编号
     */
    @ApiModelProperty(value = "交易编号")
    private String tradeNumber;

    /**
     * 起息日开始
     */
    @ApiModelProperty(value = "起息日开始")
    private LocalDate valueDateStart;

    /**
     * 起息日结束
     */
    @ApiModelProperty(value = "起息日结束")
    private LocalDate valueDateEnd;

    /**
     * 到期日开始
     */
    @ApiModelProperty(value = "到期日开始")
    private LocalDate maturityDateStart;

    /**
     * 到期日结束
     */
    @ApiModelProperty(value = "到期日结束")
    private LocalDate maturityDateEnd;

    /**
     * 结算金额最小值
     */
    @ApiModelProperty(value = "结算金额最小值")
    private BigDecimal settlementAmountMin;

    /**
     * 结算金额最大值
     */
    @ApiModelProperty(value = "结算金额最大值")
    private BigDecimal settlementAmountMax;

    /**
     * 付款状态
     */
    @ApiModelProperty(value = "付款状态")
    private String paymentStatus;

    /**
     * 制证状态
     */
    @ApiModelProperty(value = "制证状态")
    private String journalStatus;

    /**
     * 制证序号
     */
    @ApiModelProperty(value = "制证序号")
    private String journalSequence;
} 