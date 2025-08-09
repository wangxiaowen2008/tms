package com.paob.tms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.RoleAddReq;
import com.paob.tms.dto.req.RoleQueryReq;
import com.paob.tms.dto.req.RoleUpdateReq;
import com.paob.tms.dto.resp.RoleResp;

public interface SysRoleService {
    Page<RoleResp> getRoleList(RoleQueryReq req);
    void addRole(RoleAddReq req);
    void updateRole(RoleUpdateReq req);
    void deleteRole(Long id);
} 