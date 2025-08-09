package com.paob.tms.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 科目余额实体
 */
@Data
@TableName("tms_subject_balance")
public class TmsSubjectBalance {
    
    /**
     * 组合代码ID
     */
    private String codeCombinationId;
    
    /**
     * 账套代码
     */
    private String setOfBooksId;
    
    /**
     * 账套名称
     */
    private String booksName;
    
    /**
     * 会计期间
     */
    private String periodName;
    
    /**
     * 币种
     */
    private String currencyCode;
    
    /**
     * 实际数标记
     */
    private String actualFlag;
    
    /**
     * 公司段
     */
    private String segment1;
    
    /**
     * 公司段描述
     */
    private String segment1Description;
    
    /**
     * 业务段
     */
    private String segment2;
    
    /**
     * 业务段描述
     */
    private String segment2Description;
    
    /**
     * 成本中心
     */
    private String segment3;
    
    /**
     * 成本中心描述
     */
    private String segment3Description;
    
    /**
     * 产品段
     */
    private String segment4;
    
    /**
     * 产品段描述
     */
    private String segment4Description;
    
    /**
     * 一级会计科目
     */
    private String accCode1;
    
    /**
     * 一级会计科目名称
     */
    private String accCode1Name;
    
    /**
     * 二级会计科目
     */
    private String accCode2;
    
    /**
     * 二级会计科目名称
     */
    private String accCode2Name;
    
    /**
     * 三级会计科目
     */
    private String accCode3;
    
    /**
     * 三级会计科目名称
     */
    private String accCode3Name;
    
    /**
     * 子目段
     */
    private String segment6;
    
    /**
     * 子目段描述
     */
    private String segment6Description;
    
    /**
     * 备用1
     */
    private String segment7;
    
    /**
     * 备用1描述
     */
    private String segment7Description;
    
    /**
     * 关联方
     */
    private String segment8;
    
    /**
     * 关联方描述
     */
    private String segment8Description;
    
    /**
     * 期初借方余额
     */
    private BigDecimal beginBalanceDr;
    
    /**
     * 期初贷方余额
     */
    private BigDecimal beginBalanceCr;
    
    /**
     * 本期借方发生额
     */
    private BigDecimal periodNetDr;
    
    /**
     * 本期贷方发生额
     */
    private BigDecimal periodNetCr;
    
    /**
     * 期末借方余额
     */
    private BigDecimal endBalanceDr;
    
    /**
     * 期末贷方余额
     */
    private BigDecimal endBalanceCr;
    
    /**
     * 期末余额
     */
    private BigDecimal endBalance;

    /**
     * 期初余额
     */
    private BigDecimal beginBalance;

    /**
     * 本期发生额
     */
    private BigDecimal periodNet;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;
} 