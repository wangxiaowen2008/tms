package com.paob.tms.dto.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * 科目余额Excel导出响应
 */
@Data
public class SubjectBalanceExcelResp {
    
    @ExcelProperty("账套代码")
    @ColumnWidth(15)
    private String setOfBooksId;
    
    @ExcelProperty("账套名称")
    @ColumnWidth(20)
    private String booksName;
    
    @ExcelProperty("会计期间")
    @ColumnWidth(15)
    private String periodName;
    
    @ExcelProperty("币种")
    @ColumnWidth(10)
    private String currencyCode;
    
    @ExcelProperty("公司段")
    @ColumnWidth(15)
    private String segment1;
    
    @ExcelProperty("公司段描述")
    @ColumnWidth(20)
    private String segment1Description;
    
    @ExcelProperty("业务段")
    @ColumnWidth(15)
    private String segment2;
    
    @ExcelProperty("业务段描述")
    @ColumnWidth(20)
    private String segment2Description;
    
    @ExcelProperty("成本中心")
    @ColumnWidth(15)
    private String segment3;
    
    @ExcelProperty("成本中心描述")
    @ColumnWidth(20)
    private String segment3Description;
    
    @ExcelProperty("产品段")
    @ColumnWidth(15)
    private String segment4;
    
    @ExcelProperty("产品段描述")
    @ColumnWidth(20)
    private String segment4Description;
    
    @ExcelProperty("一级会计科目")
    @ColumnWidth(15)
    private String accCode1;
    
    @ExcelProperty("一级会计科目名称")
    @ColumnWidth(20)
    private String accCode1Name;
    
    @ExcelProperty("二级会计科目")
    @ColumnWidth(15)
    private String accCode2;
    
    @ExcelProperty("二级会计科目名称")
    @ColumnWidth(20)
    private String accCode2Name;
    
    @ExcelProperty("三级会计科目")
    @ColumnWidth(15)
    private String accCode3;
    
    @ExcelProperty("三级会计科目名称")
    @ColumnWidth(20)
    private String accCode3Name;
    
    @ExcelProperty("子目段")
    @ColumnWidth(15)
    private String segment6;
    
    @ExcelProperty("子目段描述")
    @ColumnWidth(20)
    private String segment6Description;
    
    @ExcelProperty("期初借方余额")
    @ColumnWidth(15)
    private String beginBalanceDr;
    
    @ExcelProperty("期初贷方余额")
    @ColumnWidth(15)
    private String beginBalanceCr;
    
    @ExcelProperty("本期借方发生额")
    @ColumnWidth(15)
    private String periodNetDr;
    
    @ExcelProperty("本期贷方发生额")
    @ColumnWidth(15)
    private String periodNetCr;
    
    @ExcelProperty("期末借方余额")
    @ColumnWidth(15)
    private String endBalanceDr;
    
    @ExcelProperty("期末贷方余额")
    @ColumnWidth(15)
    private String endBalanceCr;
    
    @ExcelProperty("期末余额")
    @ColumnWidth(15)
    private String endBalance;
} 