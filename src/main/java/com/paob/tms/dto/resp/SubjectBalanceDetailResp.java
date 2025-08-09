package com.paob.tms.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "科目余额详情响应")
public class SubjectBalanceDetailResp {
    
    @Schema(description = "组合代码ID")
    private String codeCombinationId;
    
    @Schema(description = "账套代码")
    private String setOfBooksId;
    
    @Schema(description = "账套名称")
    private String booksName;
    
    @Schema(description = "会计期间")
    private String periodName;
    
    @Schema(description = "币种")
    private String currencyCode;
    
    @Schema(description = "实际数标记：Y-实际数")
    private String actualFlag;
    
    @Schema(description = "公司段")
    private String segment1;
    
    @Schema(description = "公司段描述")
    private String segment1Description;
    
    @Schema(description = "业务段")
    private String segment2;
    
    @Schema(description = "业务段描述")
    private String segment2Description;
    
    @Schema(description = "成本中心")
    private String segment3;
    
    @Schema(description = "成本中心描述")
    private String segment3Description;
    
    @Schema(description = "产品段")
    private String segment4;
    
    @Schema(description = "产品段描述")
    private String segment4Description;
    
    @Schema(description = "一级会计科目")
    private String accCode1;
    
    @Schema(description = "一级会计科目名称")
    private String accCode1Name;
    
    @Schema(description = "二级会计科目")
    private String accCode2;
    
    @Schema(description = "二级会计科目名称")
    private String accCode2Name;
    
    @Schema(description = "三级会计科目")
    private String accCode3;
    
    @Schema(description = "三级会计科目名称")
    private String accCode3Name;
    
    @Schema(description = "子目段")
    private String segment6;
    
    @Schema(description = "子目段描述")
    private String segment6Description;
    
    @Schema(description = "备用段1")
    private String segment7;
    
    @Schema(description = "备用段1描述")
    private String segment7Description;
    
    @Schema(description = "关联方")
    private String segment8;
    
    @Schema(description = "关联方描述")
    private String segment8Description;
    
    @Schema(description = "期初借方余额")
    private BigDecimal beginBalanceDr;
    
    @Schema(description = "期初贷方余额")
    private BigDecimal beginBalanceCr;
    
    @Schema(description = "本期借方发生额")
    private BigDecimal periodNetDr;
    
    @Schema(description = "本期贷方发生额")
    private BigDecimal periodNetCr;
    
    @Schema(description = "期末借方余额")
    private BigDecimal endBalanceDr;
    
    @Schema(description = "期末贷方余额")
    private BigDecimal endBalanceCr;
    
    @Schema(description = "期末余额")
    private BigDecimal endBalance;
    
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;
    
    @Schema(description = "更新时间")
    private LocalDateTime updatedTime;
    
    @Schema(description = "凭证列表")
    private List<JournalResp> journalList;
    
    @Data
    @Schema(description = "凭证信息")
    public static class JournalResp {
        
        @Schema(description = "凭证编号")
        private String journalSequence;
        
        @Schema(description = "制证日期")
        private String effectiveDate;
        
        @Schema(description = "凭证摘要")
        private String journalLineDescription;
        
        @Schema(description = "借方金额")
        private BigDecimal enteredDr;
        
        @Schema(description = "贷方金额")
        private BigDecimal enteredCr;
        
        @Schema(description = "本位币借方金额")
        private BigDecimal accountedDr;
        
        @Schema(description = "本位币贷方金额")
        private BigDecimal accountedCr;
        
        @Schema(description = "报告币借方金额")
        private BigDecimal pEnteredDr;
        
        @Schema(description = "报告币贷方金额")
        private BigDecimal pEnteredCr;
        
        @Schema(description = "创建人")
        private String createdBy;
        
        @Schema(description = "创建时间")
        private String createdDate;
        
        @Schema(description = "复核人")
        private String checkBy;
        
        @Schema(description = "复核时间")
        private String checkDate;
    }
} 