package com.paob.tms.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

/**
 * 批量制证请求对象
 */
@Data
@Schema(description = "批量制证请求对象")
public class JournalBatchCreateReq {
    
    @Schema(description = "数据源，枚举值：TMS_TRADE_BLOTTER/TMS_ACCRUAL/TMS_CORPORATE_ACTION")
    private String dataSource;
    
    @Schema(description = "业务ID列表")
    private List<String> businessIds;

    /**
     * 凭证模板类型
     * 1: 交易日期
     * 2: 起息日
     * 11: 计提利息收入
     * 22: 分摊溢折价
     * 33: 计提估值变动
     */
    @Schema(description = "凭证模板类型：1-交易日期，2-起息日，11-计提利息收入，22-分摊溢折价，33-计提估值变动")
    private String  tempType;
} 