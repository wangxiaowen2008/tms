package com.paob.tms.dto.req;

import lombok.Data;

@Data
public class MenuButtonReq {
    private String buttonName;
    private String buttonCode;
    private Integer buttonStatus;
} 