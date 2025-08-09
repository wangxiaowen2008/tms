package com.paob.tms.dto.resp;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MenuButtonResp {
    private Long id;
    private Long menuId;
    private String buttonName;
    private String buttonCode;
    private Integer buttonStatus;
    private String updatedBy;
    private LocalDateTime updatedTime;
} 