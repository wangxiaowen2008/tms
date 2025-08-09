package com.paob.tms.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * REPO交易实体类
 */
@Data
@TableName("tms_fps_dw_repo")
public class FpsDwRepo {
    private String tradeNumber;          // 交易编号
    private String counterpart;          // 交易对手方
    private BigDecimal typeOfFacility;   // 融资工具类型
    private BigDecimal repoRate;         // 回购利率
    private String currency;             // 币种
    private BigDecimal amount;           // 金额
    private LocalDate tradeDate;         // 交易日期
    private LocalDate valueDate;         // 起息日
    private LocalDate repurchaseDate;    // 回购日期
    private String collateral;           // 抵押物
    private BigDecimal interest;         // 利息
    private String channel;              // 渠道
    private LocalTime tradeTime;         // 交易时间
    private Integer sortId;              // 排序ID
    private String paymentStatus;        // 付款状态
    private String journalStatus;        // 制证状态
    private String journalSequence;      // 凭证编号
    private String createdBy;            // 创建人
    private LocalDateTime createdTime;   // 创建时间
    private String updatedBy;            // 更新人
    private LocalDateTime updatedTime;   // 更新时间
} 