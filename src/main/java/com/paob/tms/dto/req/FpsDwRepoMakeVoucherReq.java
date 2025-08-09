package com.paob.tms.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

/**
 * REPO交易批量制证请求DTO
 */
@Data
@ApiModel(description = "REPO交易批量制证请求")
public class FpsDwRepoMakeVoucherReq {
    /**
     * 交易编号列表
     */
    @ApiModelProperty(value = "交易编号列表")
    private List<String> tradeNumbers;
} 