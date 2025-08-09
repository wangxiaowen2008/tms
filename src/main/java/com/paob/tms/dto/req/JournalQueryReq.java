package com.paob.tms.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 凭证查询请求对象
 */
@Data
@Schema(description = "凭证查询请求对象")
public class JournalQueryReq {
    @Schema(description = "账套，默认HK_SOB")
    private String booksNo;

    @Schema(description = "公司段，默认800000")
    private String segment1;

    @Schema(description = "凭证日期开始，默认上月1日")
    private Date effectiveDateStart;

    @Schema(description = "凭证日期结束，默认今日")
    private Date effectiveDateEnd;

    @Schema(description = "币种")
    private String curNo;

    @Schema(description = "金额最小值")
    private BigDecimal amountMin;

    @Schema(description = "金额最大值")
    private BigDecimal amountMax;

    @Schema(description = "凭证编号，支持模糊搜索")
    private String journalSequence;

    @Schema(description = "凭证状态：未制证、待复核、复核通过&待上传、复核通过&上传失败、复核通过&上传成功")
    private String journalStatus;

    @Schema(description = "复核人")
    private String checkBy;

    @Schema(description = "页码，默认1")
    private Integer pageNum = 1;

    @Schema(description = "每页条数，默认10")
    private Integer pageSize = 10;
} 