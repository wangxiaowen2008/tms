package com.paob.tms.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "凭证模板查询请求")
public class JournalTemplateQueryReq {
    
    @ApiModelProperty("备注类型：Bond-债券、CD-存单、EFB-欧洲金融债券、UST-美国国债、Interbank-银行间、Fixed Deposit-定期存款、Repo-回购、FX-外汇")
    private String remark;
    
    @ApiModelProperty("模板类型：PAOB Buy-PAOB买入、PAOB Sell-PAOB卖出、PAOB Borrow-PAOB借入、PAOB Lend-PAOB借出、支付利息、支付本金&利息、收取本金、收取本金&利息、Repo-回购、FX-外汇、计提利息、计提估值变动")
    private String tempType;
    
    @ApiModelProperty("流水类型：付款、收款")
    private String flowType;
    
    @ApiModelProperty("模板名称，支持模糊搜索")
    private String templateName;
    
    @ApiModelProperty("页码")
    private Integer pageNum = 1;
    
    @ApiModelProperty("每页大小")
    private Integer pageSize = 10;
} 