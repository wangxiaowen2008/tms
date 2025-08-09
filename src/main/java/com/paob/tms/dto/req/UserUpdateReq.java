package com.paob.tms.dto.req;

import lombok.Data;

import java.util.List;

@Data
public class UserUpdateReq {
    private Long id;
    private String umCode;
    private String userName;
    private String email;
    private String department;
    private Integer userStatus;
    private List<Long> roleIds;
} 