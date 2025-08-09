package com.paob.tms.dto.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.paob.tms.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * REPO交易响应DTO
 */
@Data
@ApiModel(description = "REPO交易响应")
public class FpsDwRepoResp {
    @ExcelProperty("交易编号")
    @ColumnWidth(20)
    @ApiModelProperty(value = "交易编号")
    private String tradeNumber;          // 交易编号

    @ExcelProperty("交易对手方")
    @ColumnWidth(15)
    @ApiModelProperty(value = "交易对手方")
    private String counterpart;          // 交易对手方

    @ExcelProperty("融资工具类型")
    @ColumnWidth(15)
    @NumberFormat("#0.0000")
    @ApiModelProperty(value = "融资工具类型")
    private BigDecimal typeOfFacility;   // 融资工具类型

    @ExcelProperty("回购利率")
    @ColumnWidth(10)
    @NumberFormat("#0.0000")
    @ApiModelProperty(value = "回购利率")
    private BigDecimal repoRate;         // 回购利率

    @ExcelProperty("货币")
    @ColumnWidth(8)
    @ApiModelProperty(value = "币种")
    private String currency;             // 币种

    @ExcelProperty("金额")
    @ColumnWidth(15)
    @NumberFormat("#,##0.00")
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;           // 金额

    @ExcelProperty("交易日期")
    @ColumnWidth(12)
    @ApiModelProperty(value = "交易日期")
    private String tradeDate;

    @ExcelProperty("起息日")
    @ColumnWidth(12)
    @ApiModelProperty(value = "起息日")
    private String valueDate;

    @ExcelProperty("回购日期")
    @ColumnWidth(12)
    @ApiModelProperty(value = "回购日期")
    private String repurchaseDate;

    @ExcelProperty("抵押物")
    @ColumnWidth(20)
    @ApiModelProperty(value = "抵押物")
    private String collateral;           // 抵押物

    @ExcelProperty("利息")
    @ColumnWidth(15)
    @NumberFormat("#,##0.00")
    @ApiModelProperty(value = "利息")
    private BigDecimal interest;         // 利息

    @ExcelProperty("交易渠道")
    @ColumnWidth(12)
    @ApiModelProperty(value = "交易渠道")
    private String channel;              // 渠道

    @ExcelProperty("交易时间")
    @ColumnWidth(12)
    @ApiModelProperty(value = "交易时间")
    private String tradeTime;

    @ExcelProperty("付款状态")
    @ColumnWidth(12)
    @ApiModelProperty(value = "付款状态")
    private String paymentStatus;        // 付款状态

    @ExcelProperty("制证状态")
    @ColumnWidth(15)
    @ApiModelProperty(value = "制证状态")
    private String journalStatus;        // 制证状态

    @ExcelProperty("凭证编号")
    @ColumnWidth(15)
    @ApiModelProperty(value = "凭证编号")
    private String journalSequence;      // 凭证编号

    @ExcelProperty("更新人")
    @ColumnWidth(12)
    @ApiModelProperty(value = "更新人")
    private String updatedBy;

    @ExcelProperty("更新时间")
    @ColumnWidth(20)
    @ApiModelProperty(value = "更新时间")
    private String updatedTime;

    /**
     * 设置交易日期
     */
    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = DateUtil.formatDate(tradeDate);
    }

    /**
     * 设置起息日
     */
    public void setValueDate(LocalDate valueDate) {
        this.valueDate = DateUtil.formatDate(valueDate);
    }

    /**
     * 设置回购日期
     */
    public void setRepurchaseDate(LocalDate repurchaseDate) {
        this.repurchaseDate = DateUtil.formatDate(repurchaseDate);
    }

    /**
     * 设置交易时间
     */
    public void setTradeTime(LocalTime tradeTime) {
        this.tradeTime = DateUtil.formatTime(tradeTime);
    }

    /**
     * 设置更新时间
     */
    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = DateUtil.formatDateTime(updatedTime);
    }
} 