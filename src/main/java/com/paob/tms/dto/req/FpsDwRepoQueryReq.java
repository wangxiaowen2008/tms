package com.paob.tms.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDate;
import java.math.BigDecimal;

/**
 * REPO交易查询请求DTO
 */
@Data
@ApiModel(description = "REPO交易查询请求")
public class FpsDwRepoQueryReq {
    @ApiModelProperty(value = "交易编号")
    private String tradeNumber;          // 交易编号

    @ApiModelProperty(value = "币种")
    private String currency;             // 币种

    @ApiModelProperty(value = "金额最小值")
    private BigDecimal amountMin;        // 金额最小值

    @ApiModelProperty(value = "金额最大值")
    private BigDecimal amountMax;        // 金额最大值

    @ApiModelProperty(value = "交易日期开始")
    private LocalDate tradeDateStart;    // 交易日期开始

    @ApiModelProperty(value = "交易日期结束")
    private LocalDate tradeDateEnd;      // 交易日期结束

    @ApiModelProperty(value = "起息日期开始")
    private LocalDate valueDateStart;    // 起息日期开始

    @ApiModelProperty(value = "起息日期结束")
    private LocalDate valueDateEnd;      // 起息日期结束

    @ApiModelProperty(value = "回购日期开始")
    private LocalDate repurchaseDateStart; // 回购日期开始

    @ApiModelProperty(value = "回购日期结束")
    private LocalDate repurchaseDateEnd;   // 回购日期结束

    @ApiModelProperty(value = "付款状态")
    private String paymentStatus;        // 付款状态

    @ApiModelProperty(value = "制证状态")
    private String journalStatus;        // 制证状态

    @ApiModelProperty(value = "凭证编号")
    private String journalSequence;      // 凭证编号

    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNum = 1;         // 页码

    @ApiModelProperty(value = "每页大小", example = "10")
    private Integer pageSize = 10;       // 每页大小
} 