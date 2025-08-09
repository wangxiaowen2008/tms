package com.paob.tms.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 每日汇率表
 */
@Data
@TableName("tms_daily_rates")
public class TmsDailyRate {
    
    /**
     * 源币种
     */
    @TableField("from_currency")
    private String fromCurrency;
    
    /**
     * 目标币种
     */
    @TableField("to_currency")
    private String toCurrency;
    
    /**
     * 转换日期
     */
    @TableField("conversion_date")
    private LocalDate conversionDate;
    
    /**
     * 转换类型
     */
    @TableField("conversion_type")
    private String conversionType;
    
    /**
     * 转换率
     */
    @TableField("conversion_rate")
    private String conversionRate;
    
    /**
     * 状态码
     */
    @TableField("status_code")
    private String statusCode;
    
    /**
     * 创建日期
     */
    @TableField("creation_date")
    private LocalDate creationDate;
    
    /**
     * 创建人
     */
    @TableField("created_by")
    private String createdBy;
    
    /**
     * 最后更新日期
     */
    @TableField("last_update_date")
    private LocalDate lastUpdateDate;
    
    /**
     * 最后更新人
     */
    @TableField("last_updated_by")
    private String lastUpdatedBy;
    
    /**
     * 最后更新登录
     */
    @TableField("last_update_login")
    private String lastUpdateLogin;

    /**
     * 上下文
     */
    @TableField("context")
    private String context;
    
    /**
     * 属性1
     */
    @TableField("attribute1")
    private String attribute1;
    
    /**
     * 属性2
     */
    @TableField("attribute2")
    private String attribute2;
    
    /**
     * 属性3
     */
    @TableField("attribute3")
    private String attribute3;
    
    /**
     * 属性4
     */
    @TableField("attribute4")
    private String attribute4;
    
    /**
     * 属性5
     */
    @TableField("attribute5")
    private String attribute5;
    
    /**
     * 属性6
     */
    @TableField("attribute6")
    private String attribute6;
    
    /**
     * 属性7
     */
    @TableField("attribute7")
    private String attribute7;
    
    /**
     * 属性8
     */
    @TableField("attribute8")
    private String attribute8;
    
    /**
     * 属性9
     */
    @TableField("attribute9")
    private String attribute9;
    
    /**
     * 属性10
     */
    @TableField("attribute10")
    private String attribute10;
    
    /**
     * 属性11
     */
    @TableField("attribute11")
    private String attribute11;
    
    /**
     * 属性12
     */
    @TableField("attribute12")
    private String attribute12;
    
    /**
     * 属性13
     */
    @TableField("attribute13")
    private String attribute13;
    
    /**
     * 属性14
     */
    @TableField("attribute14")
    private String attribute14;
    
    /**
     * 属性15
     */
    @TableField("attribute15")
    private String attribute15;
    
    /**
     * 汇率来源代码
     */
    @TableField("rate_source_code")
    private String rateSourceCode;
} 