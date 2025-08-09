package com.paob.tms.dto.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 计提数据导出响应
 */
@Data
public class AccrualExportResp {
    
    @ExcelProperty("日期")
    @ColumnWidth(15)
    private LocalDate date;
    
    @ExcelProperty("备注类型")
    @ColumnWidth(15)
    private String remark;
    
    @ExcelProperty("交易编号")
    @ColumnWidth(20)
    private String tradeNumber;
    
    @ExcelProperty("实体ID")
    @ColumnWidth(15)
    private String entityId;
    
    @ExcelProperty("交易对手方")
    @ColumnWidth(20)
    private String counterpart;
    
    @ExcelProperty("币种")
    @ColumnWidth(10)
    private String currency;
    
    @ExcelProperty("价格")
    @ColumnWidth(15)
    private BigDecimal price;
    
    @ExcelProperty("HKD金额汇总")
    @ColumnWidth(15)
    private BigDecimal sumOfHkdAmount;
    
    @ExcelProperty("基础货币面值汇总")
    @ColumnWidth(20)
    private BigDecimal sumOfNotionalInBaseCurrency;
    
    @ExcelProperty("市场价值")
    @ColumnWidth(15)
    private BigDecimal marketValue;
    
    @ExcelProperty("债务证券名称")
    @ColumnWidth(20)
    private String debtSecurityName;
    
    @ExcelProperty("ISIN代码")
    @ColumnWidth(20)
    private String isin;
    
    @ExcelProperty("当日应计利息")
    @ColumnWidth(15)
    private BigDecimal dailyAccruedInterest;
    
    @ExcelProperty("已计提利息")
    @ColumnWidth(15)
    private BigDecimal accruedInterest;
    
    @ExcelProperty("计提利息制证状态")
    @ColumnWidth(20)
    private String interestJournalStatus;
    
    @ExcelProperty("计提利息凭证编号")
    @ColumnWidth(20)
    private String interestJournalNo;
    
    @ExcelProperty("当日估值变动")
    @ColumnWidth(15)
    private BigDecimal dailyValuationChange;
    
    @ExcelProperty("已计提估值变动")
    @ColumnWidth(15)
    private BigDecimal accruedValuationChange;
    
    @ExcelProperty("估值变动制证状态")
    @ColumnWidth(20)
    private String valuationJournalStatus;
    
    @ExcelProperty("估值变动凭证编号")
    @ColumnWidth(20)
    private String valuationJournalNo;
    
    @ExcelProperty("分摊溢折价制证状态")
    @ColumnWidth(20)
    private String allocPremDiscJournalStatus;
    
    @ExcelProperty("分摊溢折价凭证编号")
    @ColumnWidth(20)
    private String allocPremDiscJournalNo;
    
    @ExcelProperty("当日分摊溢折价")
    @ColumnWidth(15)
    private BigDecimal dailyAllocPremDisc;
    
    @ExcelProperty("已分摊溢折价")
    @ColumnWidth(15)
    private BigDecimal allocPremDisc;
} 