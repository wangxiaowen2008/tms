package com.paob.tms.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Schema(description = "凭证导入请求对象")
public class JournalImportReq {
    @Schema(description = "Excel文件")
    private MultipartFile file;
} 