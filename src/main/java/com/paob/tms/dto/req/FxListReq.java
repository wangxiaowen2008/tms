package com.paob.tms.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@ApiModel("外汇交易列表查询请求")
public class FxListReq {
    
    @ApiModelProperty("交易编号")
    private String tradeNumber;
    
    @ApiModelProperty("币种")
    private String currency;
    
    @ApiModelProperty("金额最小值")
    private BigDecimal amountMin;
    
    @ApiModelProperty("金额最大值")
    private BigDecimal amountMax;
    
    @ApiModelProperty("起息日期开始")
    private LocalDate valueDateStart;
    
    @ApiModelProperty("起息日期结束")
    private LocalDate valueDateEnd;
    
    @ApiModelProperty("付款状态")
    private String paymentStatus;
    
    @ApiModelProperty("页码")
    private Integer pageNum = 1;
    
    @ApiModelProperty("每页大小")
    private Integer pageSize = 10;
} 