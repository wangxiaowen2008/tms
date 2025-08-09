package com.paob.tms.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "批量复核通过请求")
public class JournalBatchApproveReq {
    
    @Schema(description = "凭证编号列表")
    private List<String> journalSequence;
} 