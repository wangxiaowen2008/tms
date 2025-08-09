package com.paob.tms.dto.resp;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MenuResp {
    private Long id;
    private String menuName;
    private String menuPath;
    private String menuIcon;
    private Integer menuStatus;
    private Integer menuSort;
    private Long parentId;
    private String updatedBy;
    private LocalDateTime updatedTime;
    private List<MenuButtonResp> buttons;
} 