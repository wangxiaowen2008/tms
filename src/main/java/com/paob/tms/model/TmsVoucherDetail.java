package com.paob.tms.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 提供给OF的凭证明细表
 */
@Data
@TableName("tms_voucher_detail")
public class TmsVoucherDetail {
    
    /**
     * 凭证ID
     */
    @TableId(type = IdType.INPUT)
    private String tmsCitJournalId;
    
    /**
     * 行标记
     */
    private String lineMark;
    
    /**
     * 凭证来源
     */
    private String voucherSource;
    
    /**
     * 凭证类别
     */
    private String voucherCategory;
    
    /**
     * 业务凭证号
     */
    private String businessVoucherNum;
    
    /**
     * 交易日期
     */
    private String transDate;
    
    /**
     * 币种
     */
    private String ccy;
    
    /**
     * 汇率类型
     */
    private String exchangeRateType;
    
    /**
     * 凭证类型
     */
    private String voucherType;
    
    /**
     * 保留类型ID
     */
    private String retentionTypeId;
    
    /**
     * 预算版本ID
     */
    private String budgetVersionId;
    
    /**
     * 账套ID
     */
    private String setOfBooksId;
    
    /**
     * 公司代码
     */
    private String companyCode;
    
    /**
     * 业务代码
     */
    private String businessCode;
    
    /**
     * 成本中心代码
     */
    private String costCenterCode;
    
    /**
     * 产品代码
     */
    private String productionCode;
    
    /**
     * 科目代码
     */
    private String glCode;
    
    /**
     * 子目代码
     */
    private String subCode;
    
    /**
     * 字段1
     */
    private String field1;
    
    /**
     * 字段2
     */
    private String field2;
    
    /**
     * 借方交易金额
     */
    private BigDecimal drTranAmt;
    
    /**
     * 贷方交易金额
     */
    private BigDecimal crTranAmt;
    
    /**
     * 借方余额
     */
    private BigDecimal drBalance;
    
    /**
     * 贷方余额
     */
    private BigDecimal crBalance;
    
    /**
     * 上日借方余额
     */
    private BigDecimal drBalancePrev;
    
    /**
     * 上日贷方余额
     */
    private BigDecimal crBalancePrev;
    
    /**
     * 凭证批次名称
     */
    private String voucherBatchName;
    
    /**
     * 批次描述
     */
    private String batchDesc;
    
    /**
     * 凭证名称
     */
    private String voucherName;
    
    /**
     * 凭证描述
     */
    private String voucherDesc;
    
    /**
     * 凭证行描述
     */
    private String voucherLineDesc;
    
    /**
     * 弹性字段11
     */
    private String flexfield11;
    
    /**
     * 弹性字段12
     */
    private String flexfield12;
    
    /**
     * 弹性字段13
     */
    private String flexfield13;
    
    /**
     * 弹性字段14
     */
    private String flexfield14;
    
    /**
     * 弹性字段15
     */
    private String flexfield15;
    
    /**
     * 弹性字段16
     */
    private String flexfield16;
    
    /**
     * 弹性字段17
     */
    private String flexfield17;
    
    /**
     * 弹性字段18
     */
    private String flexfield18;
    
    /**
     * 弹性字段19
     */
    private String flexfield19;
    
    /**
     * 弹性字段20
     */
    private String flexfield20;
    
    /**
     * 系统ID
     */
    private String systemId;
    
    /**
     * 凭证状态
     */
    private String journalStatus;
    
    /**
     * 上传批次号
     */
    private String batchSeq;
    
    /**
     * 文件序号
     */
    private String fileIndex;
    
    /**
     * 创建人
     */
    private String createdBy;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
    
    /**
     * 更新人
     */
    private String updatedBy;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;
    
    /**
     * 失败内容
     */
    private String msg;
} 