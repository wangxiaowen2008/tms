package com.paob.tms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("tms_cit_journal_temp")
@ApiModel(description = "凭证模板实体类")
public class TmsCitJournalTemp {
    
    @TableId(type = IdType.INPUT)
    @ApiModelProperty("数据库主键")
    private String tmsCitJournalTempId;
    
    @ApiModelProperty("模板编号：每次新增默认为当前数据库中最大的编号+1")
    private Long tempNo;
    
    @ApiModelProperty("模板名称")
    private String tempName;
    
    @ApiModelProperty("制证日期")
    private String effectiveDate;
    
    @ApiModelProperty("币种")
    private String curNo;
    
    @ApiModelProperty("公司段")
    private String segment1;
    
    @ApiModelProperty("业务段")
    private String segment2;
    
    @ApiModelProperty("成本中心")
    private String segment3;
    
    @ApiModelProperty("产品段")
    private String segment4;
    
    @ApiModelProperty("科目")
    private String segment5;
    
    @ApiModelProperty("子目")
    private String segment6;
    
    @ApiModelProperty("备用段1：0000")
    private String segment7;
    
    @ApiModelProperty("备用段2（关联方）：0000")
    private String segment8;
    
    @ApiModelProperty("方向(借/贷（g代表借，d代表贷）")
    private String direction;
    
    @ApiModelProperty("借方金额")
    private String enteredDr;
    
    @ApiModelProperty("贷方金额")
    private String enteredCr;
    
    @ApiModelProperty("帐户借方金额")
    private String accountedDr;
    
    @ApiModelProperty("帐户贷方金额")
    private String accountedCr;
    
    @ApiModelProperty("报告币借金额")
    private BigDecimal pEnteredDr;
    
    @ApiModelProperty("报告币贷金额")
    private BigDecimal pEnteredCr;
    
    @ApiModelProperty("备注，即摘要")
    private String journalLineDescription;
    
    @ApiModelProperty("数据状态")
    private String dataState;
    
    @ApiModelProperty("创建人")
    private String createdBy;
    
    @ApiModelProperty("创建时间")
    private LocalDateTime createdTime;
    
    @ApiModelProperty("修改人")
    private String updatedBy;
    
    @ApiModelProperty("修改时间")
    private LocalDateTime updatedTime;
    
    @ApiModelProperty("账套编号")
    private String booksNo;
    
    @ApiModelProperty("备用段3")
    private String segment9;
    
    @ApiModelProperty("备用段4")
    private String segment10;
    
    @ApiModelProperty("备用段5")
    private String segment11;
    
    @ApiModelProperty("备用段6")
    private String segment12;
    
    @ApiModelProperty("备用段7")
    private String segment13;
    
    @ApiModelProperty("备用段8")
    private String segment14;
    
    @ApiModelProperty("模板类型(PAOB Buy、PAOB Sell、PAOB Borrow、PAOB Lend、支付利息、支付本金&利息、收取本金、收取本金&利息、Repo、FX、计提利息、计提估值变动)")
    private String tempType;
    
    @ApiModelProperty("流水类型(付款;收款)")
    private String streamType;
    
    @ApiModelProperty("模板描述")
    private String tempDescription;
    
    @ApiModelProperty("投资品种类型(Bond、CD、EFB、UST、Interbank、Fixed Deposit、Repo、FX)")
    private String remark;
} 