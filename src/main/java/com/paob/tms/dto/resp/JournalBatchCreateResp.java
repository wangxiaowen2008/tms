package com.paob.tms.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * 批量制证响应
 */
@Data
public class JournalBatchCreateResp {
    /**
     * 凭证ID列表
     */
    @Schema(description = "凭证ID列表")
    private List<String> journalIds;

    /**
     * 凭证编号列表（去重）
     */
    @Schema(description = "凭证编号列表（去重）")
    private List<String> journalNumbers;

    public JournalBatchCreateResp(List<String> journalIds, List<String> journalNumbers) {
        this.journalIds = journalIds;
        // 使用LinkedHashSet去重并保持顺序
        this.journalNumbers = new ArrayList<>(new LinkedHashSet<>(journalNumbers));
    }
} 