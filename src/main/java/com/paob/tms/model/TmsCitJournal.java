package com.paob.tms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 系统凭证业务表
 */
@Data
@TableName("tms_cit_journal")
@Schema(description = "系统凭证业务表")
public class TmsCitJournal {
    @Schema(description = "数据库主键")
    @TableId(type = IdType.INPUT)
    private String tmsCitJournalId;

    @Schema(description = "制证规则编号(记账凭证必填)")
    private String tmsPostingRuleId;

    @Schema(description = "凭证类型：明晰凭证、手工凭证、导入凭证、其它凭证")
    private String categoryName;

    @Schema(description = "凭证编号")
    private String journalSequence;

    @Schema(description = "制证日期")
    private LocalDate effectiveDate;

    @Schema(description = "币种，如：RMB/HKD/USD")
    private String curNo;

    @Schema(description = "公司段")
    private String segment1;

    @Schema(description = "业务段")
    private String segment2;

    @Schema(description = "成本中心")
    private String segment3;

    @Schema(description = "产品段")
    private String segment4;

    @Schema(description = "科目")
    private String segment5;

    @Schema(description = "子目")
    private String segment6;

    @Schema(description = "备用段1：0000")
    private String segment7;

    @Schema(description = "备用段2（关联方）：0000")
    private String segment8;

    @Schema(description = "备用段3")
    private String segment9;

    @Schema(description = "借方金额")
    private BigDecimal enteredDr;

    @Schema(description = "贷方金额")
    private BigDecimal enteredCr;

    @Schema(description = "本位币借方金额")
    private BigDecimal accountedDr;

    @Schema(description = "本位币贷方金额")
    private BigDecimal accountedCr;

    @Schema(description = "报告币借金额")
    private BigDecimal pEnteredDr;

    @Schema(description = "报告币贷金额")
    private BigDecimal pEnteredCr;

    @Schema(description = "备注，即摘要")
    private String journalLineDescription;

    @Schema(description = "凭证状态：1-待上传，2-上传成功")
    private Integer journalStatus;

    @Schema(description = "冲销状态：Y已冲销")
    private String writeOffs;

    @Schema(description = "就绪凭证编号")
    private String readyState;

    @Schema(description = "数据状态：Y进入接口表 N还没进入接口表")
    private String dataState;

    @Schema(description = "创建人")
    private String createdBy;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    @Schema(description = "修改人")
    private String updatedBy;

    @Schema(description = "修改时间")
    private LocalDateTime updatedTime;

    @Schema(description = "制证业务数据来源表")
    private String dataSource;

    @Schema(description = "制证业务数据ID")
    private String dataId;

    @Schema(description = "是否进行了科目余额处理")
    private String balanceStatus;

    @Schema(description = "证券代码")
    private String securNo;

    @Schema(description = "证券类别:1-股票,2-基金,3-债券,4-回购,5-另类金融产品,D-定存")
    private String securType;

    @Schema(description = "证券市场")
    private String marketNo;

    @Schema(description = "交易数量")
    private String dealNumber;

    @Schema(description = "账套")
    private String booksNo;

    @Schema(description = "备用段4")
    private String segment10;

    @Schema(description = "备用段5")
    private String segment11;

    @Schema(description = "备用段6")
    private String segment12;

    @Schema(description = "备用段7")
    private String segment13;

    @Schema(description = "备用段8")
    private String segment14;

    @Schema(description = "默认值:HK_SOB")
    private String msg;

    @Schema(description = "备用字段")
    private String attribute7;

    @Schema(description = "备用字段")
    private String attribute1;

    @Schema(description = "备用字段")
    private String attribute2;

    @Schema(description = "备用字段")
    private String attribute3;

    @Schema(description = "备用字段")
    private String attribute4;

    @Schema(description = "备用字段")
    private String attribute5;

    @Schema(description = "备用字段")
    private String attribute6;

    @Schema(description = "备用字段")
    private String attribute8;

    @Schema(description = "备用字段")
    private String attribute9;

    @Schema(description = "备用字段")
    private String attribute10;

    @Schema(description = "弹性域的校验状态【N-不通过】【Y-通过】")
    private String flexibleStatus;

    @Schema(description = "【0-AVS导入凭证】【1-自动生成凭证】")
    private String journalSource;

    @Schema(description = "【0-默认】【1-PAOB Buy】【2-PAOB Sell】【3-PAOB Borrow】【4-PAOB Lend】【5-支付利息】【6-支付本金&利息】【7-收取本金】【8-收取本金&利息】【9-Repo】【10-FX】【11-计提利息】【12-计提估值变动】")
    private String journalTemp;

    @Schema(description = "模板名称")
    private String journalName;

    @Schema(description = "复核人")
    private String checkBy;

    @Schema(description = "复核时间")
    private LocalDate checkDate;

    @Schema(description = "记录人")
    private String recordPerson;

    @Schema(description = "业务id 审批用")
    private String businessId;
} 