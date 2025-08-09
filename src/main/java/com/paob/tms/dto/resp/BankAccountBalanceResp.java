package com.paob.tms.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Schema(description = "银行账号余额对账响应")
public class BankAccountBalanceResp {
    
    @Schema(description = "记录ID")
    private Long id;
    
    @Schema(description = "对账日期")
    private LocalDate date;
    
    @Schema(description = "银行名称")
    private String bankName;
    
    @Schema(description = "账户名称")
    private String accountName;
    
    @Schema(description = "银行账号")
    private String accountNumber;
    
    @Schema(description = "币种")
    private String currency;
    
    @Schema(description = "银行账户余额")
    private BigDecimal accountBalance;
    
    @Schema(description = "会计科目")
    private String subject;
    
    @Schema(description = "会计子目")
    private String subSubject;
    
    @Schema(description = "科目期末余额")
    private BigDecimal endBalance;
    
    @Schema(description = "差异金额（银行账户余额-科目期末余额）")
    private BigDecimal differenceAmount;
} 