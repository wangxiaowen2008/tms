package com.paob.tms.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 计提数据查询请求
 */
@Data
@ApiModel("计提数据查询请求")
public class AccrualQueryReq {
    
    @ApiModelProperty("备注类型：Bond-债券、CD-存单、EFB-欧洲金融债券、UST-美国国债、Interbank-银行间、Fixed Deposit-定期存款")
    private String remark;
    
    @ApiModelProperty("交易编号，支持模糊搜索")
    private String tradeNumber;
    
    @ApiModelProperty("日期范围开始，格式：yyyy-MM-dd")
    private LocalDate dateStart;
    
    @ApiModelProperty("日期范围结束，格式：yyyy-MM-dd")
    private LocalDate dateEnd;
    
    @ApiModelProperty("币种，如USD、HKD等")
    private String currency;
    
    @ApiModelProperty("市场价值最小值")
    private BigDecimal marketValueMin;
    
    @ApiModelProperty("市场价值最大值")
    private BigDecimal marketValueMax;
    
    @ApiModelProperty("ISIN代码，国际证券识别编码")
    private String isin;
    
    @ApiModelProperty("计提利息制证状态")
    private String interestJournalStatus;
    
    @ApiModelProperty("估值变动制证状态")
    private String valuationJournalStatus;
    
    @ApiModelProperty("分摊溢折价制证状态")
    private String allocPremDiscJournalStatus;
    
    @ApiModelProperty("凭证编号，支持模糊搜索")
    private String journalSequence;
    
    @ApiModelProperty("页码，从1开始")
    private Integer pageNum = 1;
    
    @ApiModelProperty("每页大小")
    private Integer pageSize = 10;
} 