package com.paob.tms.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "科目余额详情查询请求")
public class SubjectBalanceDetailQueryReq {
    
    @Schema(description = "账套代码")
    private String setOfBooksId;
    
    @Schema(description = "会计期间")
    private String periodName;
    
    @Schema(description = "公司段")
    private String segment1;
    
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
    
    @Schema(description = "备用段1")
    private String segment7;
    
    @Schema(description = "备用段2（关联方）")
    private String segment8;
    
    @Schema(description = "备用段3")
    private String segment9;
    
    @Schema(description = "备用段4")
    private String segment10;
    
    @Schema(description = "备用段5")
    private String segment11;
    
    @Schema(description = "备用段6")
    private String segment12;
    
    @Schema(description = "备用段7")
    private String segment13;
    
    @Schema(description = "备用段8")
    private String segment14;
    
    @Schema(description = "币种")
    private String curNo;
    
    @Schema(description = "页码，默认1")
    private Integer pageNum = 1;
    
    @Schema(description = "每页大小，默认10")
    private Integer pageSize = 10;
} 