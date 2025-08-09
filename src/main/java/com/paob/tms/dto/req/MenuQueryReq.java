package com.paob.tms.dto.req;

import lombok.Data;

@Data
public class MenuQueryReq {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String menuName;
    private Integer menuStatus;
} 