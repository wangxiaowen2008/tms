package com.paob.tms.dto.req;

import lombok.Data;

import java.util.List;

@Data
public class RoleUpdateReq {
    private Long id;
    private String roleName;
    private Integer roleStatus;
    private List<RoleMenuReq> menuPermissions;
} 