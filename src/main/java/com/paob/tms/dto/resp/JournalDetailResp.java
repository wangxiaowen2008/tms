package com.paob.tms.dto.resp;

import com.paob.tms.model.TmsCitJournal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * 凭证明细响应对象
 */
@Data
@Schema(description = "凭证明细响应对象")
public class JournalDetailResp {

    @Schema(description = "账套")
    private String booksNo;

    @Schema(description = "币种")
    private String curNo;

    @Schema(description = "数据状态")
    private String dataState;

    @Schema(description = "制证日期")
    private LocalDate effectiveDate;

    @Schema(description = "凭证编号")
    private String journalSequence;

    @Schema(description = "凭证状态：1-待上传，2-上传成功")
    private Integer journalStatus;

    @Schema(description = "公司段")
    private String segment1;

    @Schema(description = "凭证明细列表")
    private List<TmsCitJournal> segmentList;

    @Data
    public static class FlexibleDetail {
        @Schema(description = "账套")
        private String bookNo;

        @Schema(description = "内容类型")
        private String contentType;

        @Schema(description = "是否启用")
        private String enableFlag;

        @Schema(description = "弹性域值集ID")
        private String flexValueSetId;

        @Schema(description = "弹性域代码")
        private String flexibleCode;

        @Schema(description = "弹性域描述")
        private String flexibleDesc;

        @Schema(description = "是否必填")
        private String requiredFlag;

        @Schema(description = "科目")
        private String segment5;

        @Schema(description = "科目描述")
        private String segment5Desc;

        @Schema(description = "是否汇总")
        private String summaryFlag;

        @Schema(description = "更新时间")
        private String updateDate;
    }
} 