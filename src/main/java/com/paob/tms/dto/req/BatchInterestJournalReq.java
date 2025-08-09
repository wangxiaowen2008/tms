package com.paob.tms.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 批量计提利息制证请求
 */
@Data
@ApiModel("批量计提利息制证请求")
public class BatchInterestJournalReq {
    
    @ApiModelProperty("交易编号列表")
    private List<String> tradeNumbers;
} 