package com.paob.tms.dto.resp;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RoleResp {
    private Long id;
    private String roleName;
    private Integer roleStatus;
    private String updatedBy;
    private LocalDateTime updatedTime;
    private List<RoleMenuResp> menuPermissions;
}

@Data
class RoleMenuResp {
    private Long menuId;
    private String menuName;
    private List<RoleButtonResp> buttons;
}

@Data
class RoleButtonResp {
    private Long buttonId;
    private String buttonName;
    private String buttonCode;
} 