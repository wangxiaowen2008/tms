package com.paob.tms.dto.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@ContentRowHeight(20)
@HeadRowHeight(25)
@ApiModel("外汇交易列表查询响应")
public class FxListResp {
    
    @ExcelProperty(value = "交易编号", index = 0)
    @ColumnWidth(20)
    @ApiModelProperty("交易编号")
    private String tradeNumber;
    
    @ExcelProperty(value = "交易对手方", index = 1)
    @ColumnWidth(20)
    @ApiModelProperty("交易对手方")
    private String counterpart;
    
    @ExcelProperty(value = "货币对", index = 2)
    @ColumnWidth(15)
    @ApiModelProperty("货币对")
    private String currencyPair;
    
    @ExcelProperty(value = "起息日", index = 3)
    @ColumnWidth(15)
    @ApiModelProperty("起息日")
    private LocalDate valueDate;
    
    @ExcelProperty(value = "货币1买卖标识", index = 4)
    @ColumnWidth(15)
    @ApiModelProperty("货币1的买卖标识")
    private String bsCcy1;
    
    @ExcelProperty(value = "货币1", index = 5)
    @ColumnWidth(10)
    @ApiModelProperty("第一种货币")
    private String ccy1;
    
    @ExcelProperty(value = "货币1金额", index = 6)
    @ColumnWidth(20)
    @ApiModelProperty("第一种货币的交易金额")
    private BigDecimal ccy1Amount;
    
    @ExcelProperty(value = "货币2买卖标识", index = 7)
    @ColumnWidth(15)
    @ApiModelProperty("货币2的买卖标识")
    private String bsCcy2;
    
    @ExcelProperty(value = "货币2", index = 8)
    @ColumnWidth(10)
    @ApiModelProperty("第二种货币")
    private String ccy2;
    
    @ExcelProperty(value = "货币2金额", index = 9)
    @ColumnWidth(20)
    @ApiModelProperty("第二种货币的交易金额")
    private BigDecimal ccy2Amount;
    
    @ExcelProperty(value = "汇率", index = 10)
    @ColumnWidth(15)
    @ApiModelProperty("汇率")
    private BigDecimal rate;
    
    @ExcelProperty(value = "交易渠道", index = 11)
    @ColumnWidth(15)
    @ApiModelProperty("交易渠道")
    private String channel;
    
    @ExcelProperty(value = "交易时间", index = 12)
    @ColumnWidth(15)
    @ApiModelProperty("交易时间")
    private LocalTime tradeTime;
    
    @ExcelProperty(value = "付款状态", index = 13)
    @ColumnWidth(15)
    @ApiModelProperty("付款状态")
    private String paymentStatus;
    
    @ExcelProperty(value = "制证状态", index = 14)
    @ColumnWidth(15)
    @ApiModelProperty("制证状态")
    private String journalStatus;
    
    @ExcelProperty(value = "凭证编号", index = 15)
    @ColumnWidth(20)
    @ApiModelProperty("凭证编号")
    private String journalSequence;
    
    @ExcelProperty(value = "订单号", index = 16)
    @ColumnWidth(20)
    @ApiModelProperty("订单号")
    private String orderId;
    
    @ExcelProperty(value = "流水号", index = 17)
    @ColumnWidth(20)
    @ApiModelProperty("流水号")
    private String streamNo;
    
    @ExcelProperty(value = "更新人", index = 18)
    @ColumnWidth(15)
    @ApiModelProperty("更新人")
    private String updatedBy;
    
    @ExcelProperty(value = "更新时间", index = 19)
    @ColumnWidth(20)
    @ApiModelProperty("更新时间")
    private LocalDateTime updatedTime;
} 