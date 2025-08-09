package com.paob.tms.dto.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class JournalExportResp {
    
    @ExcelProperty("凭证编号")
    private String journalSequence;
    
    @ExcelProperty("凭证日期")
    @DateTimeFormat("yyyy-MM-dd")
    private Date effectiveDate;
    
    @ExcelProperty("账簿编号")
    private String booksNo;
    
    @ExcelProperty("科目")
    private String segment1;
    
    @ExcelProperty("凭证名称")
    private String journalName;
    
    @ExcelProperty("凭证状态")
    private String journalStatus;
    
    @ExcelProperty("币种")
    private String curNo;
    
    @ExcelProperty("借方金额")
    private BigDecimal enteredDr;
    
    @ExcelProperty("贷方金额")
    private BigDecimal enteredCr;
    
    @ExcelProperty("创建人")
    private String createdBy;
    
    @ExcelProperty("创建时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    
    @ExcelProperty("审核人")
    private String checkBy;
    
    @ExcelProperty("审核时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date checkDate;
} 