package com.paob.tms.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Schema(description = "新增凭证模板请求")
public class JournalTemplateAddReq {

    @Schema(description = "凭证模板列表")
    private List<JournalTemp> journalTempList;

    @Data
    @Schema(description = "凭证模板信息")
    public static class JournalTemp {

        @Schema(description = "凭证模板ID")
        private String tmsCitJournalTempId;

        @Schema(description = "账套编号")
        private String booksNo;

        @Schema(description = "备注类型")
        private String remark;

        @Schema(description = "模板类型")
        private String tempType;

        @Schema(description = "流水类型")
        private String streamType;

        @Schema(description = "方向：G-借方，D-贷方")
        private String direction;

        @Schema(description = "分录描述")
        private String journalLineDescription;

        @Schema(description = "公司段")
        private String segment1;

        @Schema(description = "公司段类型")
        private String segment1Type;

        @Schema(description = "业务段")
        private String segment2;

        @Schema(description = "成本中心")
        private String segment3;

        @Schema(description = "产品段")
        private String segment4;

        @Schema(description = "科目")
        private String segment5;

        @Schema(description = "子目")
        private String segment6;

        @Schema(description = "备用段2（关联方）")
        private String segment8;

        @Schema(description = "模板编号")
        private Long tempNo;

        @Schema(description = "模板名称")
        private String tempName;

        @Schema(description = "制证日期")
        private LocalDate effectiveDate;

        @Schema(description = "币种")
        private String curNo;

        @Schema(description = "借方金额")
        private String enteredDr;

        @Schema(description = "贷方金额")
        private String enteredCr;

        @Schema(description = "帐户借方金额")
        private String accountedDr;

        @Schema(description = "帐户贷方金额")
        private String accountedCr;

        @Schema(description = "凭证分组")
        private long groupFlag;
    }
} 