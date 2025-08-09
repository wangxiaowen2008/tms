package com.paob.tms.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 凭证响应对象
 */
@Data
@Schema(description = "凭证响应对象")
public class JournalResp {
    @Schema(description = "账套")
    private String booksNo;

    @Schema(description = "凭证类型：明晰凭证、手工凭证、导入凭证、其它凭证")
    private String categoryName;

    @Schema(description = "复核人")
    private String checkBy;

    @Schema(description = "创建人")
    private String createdBy;

    @Schema(description = "创建时间")
    private Date createdDate;

    @Schema(description = "币种")
    private String curNo;

    @Schema(description = "数据状态：Y进入接口表 N还没进入接口表")
    private String dataState;

    @Schema(description = "制证日期")
    private Date effectiveDate;

    @Schema(description = "模板名称")
    private String journalName;

    @Schema(description = "弹性域校验状态：Y-通过，N-不通过")
    private String flexibleStatus;

    @Schema(description = "凭证明细")
    private List<JournalDetailResp> journalDetail;

    @Schema(description = "凭证明细数量")
    private Integer journalDetailCount;

    @Schema(description = "凭证编号")
    private String journalSequence;

    @Schema(description = "凭证状态：1-待上传，2-上传成功")
    private Integer journalStatus;

    @Schema(description = "键值")
    private String key;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "操作人")
    private String operator;

    @Schema(description = "就绪凭证编号")
    private String readyState;

    @Schema(description = "公司段")
    private String segment1;

    @Schema(description = "备用段7")
    private String segment13;

    @Schema(description = "备用段8")
    private String segment14;

    @Schema(description = "业务段")
    private String segment2;

    @Schema(description = "修改人")
    private String updatedBy;

    @Schema(description = "修改时间")
    private Date updatedDate;
} 