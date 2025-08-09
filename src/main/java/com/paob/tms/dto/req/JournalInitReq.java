package com.paob.tms.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

/**
 * 制证初始化请求
 */
@Data
public class JournalInitReq {
    /**
     * 数据源
     */
    @Schema(description = "数据源")
    private String dataSource;

    /**
     * 业务ID列表
     */
    @Schema(description = "业务ID列表")
    private List<String> businessIds;

    /**
     * 凭证模板名称
     */
    @Schema(description = "凭证模板名称")
    private String tempName;

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