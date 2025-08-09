package com.paob.tms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@TableName("tms_fx")
@ApiModel("外汇交易实体")
public class TmsFx {
    
    @TableId(type = IdType.INPUT)
    @ApiModelProperty("交易编号")
    private String tradeNumber;
    
    @ApiModelProperty("交易对手方")
    private String counterpart;
    
    @ApiModelProperty("货币对")
    private String currencyPair;
    
    @ApiModelProperty("起息日")
    private LocalDate valueDate;
    
    @ApiModelProperty("货币1的买卖标识")
    private String bsCcy1;
    
    @ApiModelProperty("第一种货币")
    private String ccy1;
    
    @ApiModelProperty("第一种货币的交易金额")
    private BigDecimal ccy1Amount;
    
    @ApiModelProperty("货币2的买卖标识")
    private String bsCcy2;
    
    @ApiModelProperty("第二种货币")
    private String ccy2;
    
    @ApiModelProperty("第二种货币的交易金额")
    private BigDecimal ccy2Amount;
    
    @ApiModelProperty("汇率")
    private BigDecimal rate;
    
    @ApiModelProperty("交易渠道")
    private String channel;
    
    @ApiModelProperty("交易时间")
    private LocalTime tradeTime;
    
    @ApiModelProperty("排序ID")
    private Integer sortId;
    
    @ApiModelProperty("付款状态")
    private String paymentStatus;
    
    @ApiModelProperty("制证状态")
    private String journalStatus;
    
    @ApiModelProperty("凭证编号")
    private String journalSequence;
    
    @ApiModelProperty("订单号")
    private String orderId;
    
    @ApiModelProperty("流水号")
    private String streamNo;
    
    @ApiModelProperty("创建人")
    private String createdBy;
    
    @ApiModelProperty("创建时间")
    private LocalDateTime createdTime;
    
    @ApiModelProperty("更新人")
    private String updatedBy;
    
    @ApiModelProperty("更新时间")
    private LocalDateTime updatedTime;
} 