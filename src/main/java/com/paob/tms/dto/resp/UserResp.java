package com.paob.tms.dto.resp;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserResp {
    private Long id;
    private String umCode;
    private String userName;
    private String email;
    private String department;
    private Integer userStatus;
    private String updatedBy;
    private LocalDateTime updatedTime;
    private List<UserRoleResp> roles;
} 