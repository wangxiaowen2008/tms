package com.paob.tms.dto.resp;

import lombok.Data;

@Data
public class UserRoleResp {
    private Long roleId;
    private String roleName;
    private Integer roleStatus;
} 