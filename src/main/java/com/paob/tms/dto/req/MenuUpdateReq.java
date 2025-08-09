package com.paob.tms.dto.req;

import lombok.Data;

@Data
public class MenuUpdateReq {
    private Long id;
    private Long parentId;
    private String menuName;
    private String menuUrl;
    private String menuIcon;
    private Integer menuStatus;
} 