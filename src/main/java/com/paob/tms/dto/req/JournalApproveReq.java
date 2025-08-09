package com.paob.tms.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "凭证复核通过请求")
public class JournalApproveReq {
    
    @Schema(description = "凭证编号")
    private String journalSequence;
} 