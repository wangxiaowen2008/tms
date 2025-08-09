package com.paob.tms.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Schema(description = "银行账号余额对账查询请求")
public class BankAccountBalanceQueryReq {
    
    @Schema(description = "查询日期，格式：yyyy-MM-dd，默认昨天")
    private LocalDate date;
    
    @Schema(description = "银行名称")
    private String bankName;
    
    @Schema(description = "银行账号")
    private String accountNumber;
    
    @Schema(description = "币种")
    private String currency;
    
    @Schema(description = "金额")
    private BigDecimal amount;
    
    @Schema(description = "对账差异：YES-有差异，NO-无差异")
    private String hasDifference;
    
    @Schema(description = "页码，默认1")
    private Integer pageNum = 1;
    
    @Schema(description = "每页大小，默认10")
    private Integer pageSize = 10;
} 