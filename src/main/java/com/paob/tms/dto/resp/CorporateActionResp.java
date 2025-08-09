package com.paob.tms.dto.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "公司行为响应对象")
public class CorporateActionResp {
    
    @ExcelProperty("公司行为编号")
    @ColumnWidth(20)
    @Schema(description = "公司行为编号")
    private String actionNo;
    
    @ExcelProperty("Trade Number")
    @ColumnWidth(20)
    @Schema(description = "Trade Number列表")
    private String tradeNumbers;
    
    @ExcelProperty("Entity ID")
    @ColumnWidth(20)
    @Schema(description = "Entity ID")
    private String entityId;
    
    @ExcelProperty("Counterpart")
    @ColumnWidth(20)
    @Schema(description = "Counterpart")
    private String counterpart;
    
    @ExcelProperty("公司行为类型")
    @ColumnWidth(20)
    @Schema(description = "公司行为类型：到期/利息")
    private String actionType;
    
    @ExcelProperty("货币")
    @ColumnWidth(20)
    @Schema(description = "货币")
    private String currency;
    
    @ExcelProperty("结算金额")
    @ColumnWidth(20)
    @Schema(description = "结算金额")
    private BigDecimal settlementAmount;
    
    @ExcelProperty("起息日")
    @ColumnWidth(20)
    @Schema(description = "起息日")
    private LocalDate valueDate;
    
    @ExcelProperty("备注")
    @ColumnWidth(20)
    @Schema(description = "备注，如EFB")
    private String remark;
    
    @ExcelProperty("债券名称")
    @ColumnWidth(20)
    @Schema(description = "债券名称")
    private String debtSecurityName;
    
    @ExcelProperty("ISIN代码")
    @ColumnWidth(20)
    @Schema(description = "ISIN代码")
    private String isin;
    
    @ExcelProperty("结算账户")
    @ColumnWidth(20)
    @Schema(description = "结算账户")
    private String settlementAccount;
    
    @ExcelProperty("经纪商")
    @ColumnWidth(20)
    @Schema(description = "经纪商")
    private String broker;
    
    @ExcelProperty("付款状态")
    @ColumnWidth(20)
    @Schema(description = "付款状态：未收款/已收款")
    private String paymentStatus;
    
    @ExcelProperty("制证状态")
    @ColumnWidth(20)
    @Schema(description = "制证状态：未制证/已制证")
    private String journalStatus;
    
    @ExcelProperty("凭证编号")
    @ColumnWidth(20)
    @Schema(description = "凭证编号")
    private String journalSequence;
    
    @ExcelProperty("订单号")
    @ColumnWidth(20)
    @Schema(description = "订单号")
    private String orderId;
    
    @ExcelProperty("本金金额")
    @ColumnWidth(20)
    @Schema(description = "本金金额")
    private BigDecimal principalAmount;
    
    @ExcelProperty("利息金额")
    @ColumnWidth(20)
    @Schema(description = "利息金额")
    private BigDecimal interestAmount;
    
    @ExcelProperty("总金额")
    @ColumnWidth(20)
    @Schema(description = "本金+利息总金额")
    private BigDecimal totalAmount;
    
    @ExcelProperty("创建时间")
    @ColumnWidth(20)
    private LocalDateTime createTime;
    
    @ExcelProperty("更新时间")
    @ColumnWidth(20)
    private LocalDateTime updateTime;
    
    @ExcelProperty("创建人")
    @ColumnWidth(20)
    private String createBy;
    
    @ExcelProperty("更新人")
    @ColumnWidth(20)
    private String updateBy;
} 