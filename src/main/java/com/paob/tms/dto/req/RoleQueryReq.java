package com.paob.tms.dto.req;

import lombok.Data;

@Data
public class RoleQueryReq {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String roleName;
    private String roleCode;
    private Integer roleStatus;
} 