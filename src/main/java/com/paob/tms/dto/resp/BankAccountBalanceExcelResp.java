package com.paob.tms.dto.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BankAccountBalanceExcelResp {
    
    @ExcelProperty("对账日期")
    @ColumnWidth(15)
    private LocalDate date;
    
    @ExcelProperty("银行名称")
    @ColumnWidth(20)
    private String bankName;
    
    @ExcelProperty("账户名称")
    @ColumnWidth(20)
    private String accountName;
    
    @ExcelProperty("银行账号")
    @ColumnWidth(20)
    private String accountNumber;
    
    @ExcelProperty("币种")
    @ColumnWidth(10)
    private String currency;
    
    @ExcelProperty("银行账户余额")
    @ColumnWidth(15)
    private BigDecimal accountBalance;
    
    @ExcelProperty("会计科目")
    @ColumnWidth(15)
    private String subject;
    
    @ExcelProperty("会计子目")
    @ColumnWidth(15)
    private String subSubject;
    
    @ExcelProperty("科目期末余额")
    @ColumnWidth(15)
    private BigDecimal endBalance;
    
    @ExcelProperty("差异金额")
    @ColumnWidth(15)
    private BigDecimal differenceAmount;
} 