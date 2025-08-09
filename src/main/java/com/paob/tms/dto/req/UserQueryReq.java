package com.paob.tms.dto.req;

import lombok.Data;

@Data
public class UserQueryReq {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String userName;
    private String umCode;
    private String email;
    private String department;
    private Integer userStatus;
} 