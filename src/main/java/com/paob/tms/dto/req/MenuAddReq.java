package com.paob.tms.dto.req;

import lombok.Data;

import java.util.List;

@Data
public class MenuAddReq {
    private String menuName;
    private String menuPath;
    private String menuIcon;
    private Integer menuStatus;
    private Integer menuSort;
    private Long parentId;
    private List<MenuButtonReq> buttons;
} 