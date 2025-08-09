package com.paob.tms.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Schema(description = "公司行为查询请求")
public class CorporateActionQueryReq {
    
    @Schema(description = "公司行为类型，枚举值：Bond-债券、CD-存单、EFB-欧洲金融债券、UST-美国国债、Interbank-银行间、Fixed Deposit-定期存款、Repo-回购")
    private String remark;
    
    @Schema(description = "到期/利息类型，枚举值：支付利息、支付本金&利息、收取利息、收取本金&利息")
    private String actionType;
    
    @Schema(description = "交易编号，支持模糊搜索")
    private String tradeNumber;
    
    @Schema(description = "公司行为编号，支持模糊搜索")
    private String actionNumber;
    
    @Schema(description = "起息日开始日期，格式：yyyy-MM-dd")
    private LocalDate valueDateStart;
    
    @Schema(description = "起息日结束日期，格式：yyyy-MM-dd")
    private LocalDate valueDateEnd;
    
    @Schema(description = "币种，如USD、HKD等")
    private String currency;
    
    @Schema(description = "结算金额最小值")
    private BigDecimal amountMin;
    
    @Schema(description = "结算金额最大值")
    private BigDecimal amountMax;
    
    @Schema(description = "ISIN代码，国际证券识别编码")
    private String isin;
    
    @Schema(description = "付/收款状态，枚举值：未付款、付款中、已付款、未收款、已收款")
    private String paymentStatus;
    
    @Schema(description = "制证状态，枚举值：未制证、待复核、复核通过&待上传、复核通过&上传失败、复核通过&上传成功")
    private String journalStatus;
    
    @Schema(description = "凭证编号，支持模糊搜索")
    private String journalSequence;
    
    @Schema(description = "页码，从1开始")
    private Integer pageNum = 1;
    
    @Schema(description = "每页记录数")
    private Integer pageSize = 10;
} 