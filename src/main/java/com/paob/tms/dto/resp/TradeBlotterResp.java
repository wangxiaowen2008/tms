package com.paob.tms.dto.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 交易流水响应
 */
@ApiModel(description = "交易流水响应")
@Data
public class TradeBlotterResp {
    /**
     * 交易编号
     */
    @ExcelProperty(value = "交易编号", index = 0)
    @ColumnWidth(20)
    @ApiModelProperty(value = "交易编号")
    private String tradeNumber;

    /**
     * 交易日期
     */
    @ExcelProperty(value = "交易日期", index = 1)
    @ColumnWidth(15)
    @DateTimeFormat("yyyy-MM-dd")
    @ApiModelProperty(value = "交易日期")
    private LocalDate tradeDate;

    /**
     * 交易时间
     */
    @ExcelProperty(value = "交易时间", index = 2)
    @ColumnWidth(15)
    @DateTimeFormat("HH:mm:ss")
    @ApiModelProperty(value = "交易时间")
    private LocalTime tradeTime;

    /**
     * 交易对手方
     */
    @ExcelProperty(value = "交易对手方", index = 3)
    @ColumnWidth(20)
    @ApiModelProperty(value = "交易对手方")
    private String counterpart;

    /**
     * 买卖方向
     */
    @ExcelProperty(value = "买卖方向", index = 4)
    @ColumnWidth(15)
    @ApiModelProperty(value = "买卖方向")
    private String buySellBorrowLend;

    /**
     * 货币
     */
    @ExcelProperty(value = "货币", index = 5)
    @ColumnWidth(10)
    @ApiModelProperty(value = "货币")
    private String currency;

    /**
     * 结算金额
     */
    @ExcelProperty(value = "结算金额", index = 6)
    @ColumnWidth(15)
    @ApiModelProperty(value = "结算金额")
    private BigDecimal settlementAmount;

    /**
     * 起息日
     */
    @ExcelProperty(value = "起息日", index = 7)
    @ColumnWidth(15)
    @DateTimeFormat("yyyy-MM-dd")
    @ApiModelProperty(value = "起息日")
    private LocalDate valueDate;

    /**
     * 到期日
     */
    @ExcelProperty(value = "到期日", index = 8)
    @ColumnWidth(15)
    @DateTimeFormat("yyyy-MM-dd")
    @ApiModelProperty(value = "到期日")
    private LocalDate maturityDate;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注", index = 9)
    @ColumnWidth(30)
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 实体ID
     */
    @ExcelProperty(value = "实体ID", index = 10)
    @ColumnWidth(15)
    @ApiModelProperty(value = "实体ID")
    private String entityId;

    /**
     * 交易类型
     */
    @ExcelProperty(value = "交易类型", index = 11)
    @ColumnWidth(15)
    @ApiModelProperty(value = "交易类型")
    private String tradeType;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态", index = 12)
    @ColumnWidth(10)
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 付款状态
     */
    @ExcelProperty(value = "支付状态", index = 13)
    @ColumnWidth(15)
    @ApiModelProperty(value = "支付状态")
    private String paymentStatus;

    /**
     * 制证状态
     */
    @ExcelProperty(value = "制证状态", index = 14)
    @ColumnWidth(15)
    @ApiModelProperty(value = "制证状态")
    private String journalStatus;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", index = 15)
    @ColumnWidth(20)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间", index = 16)
    @ColumnWidth(20)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
} 