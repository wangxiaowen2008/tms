package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.RoleAddReq;
import com.paob.tms.dto.req.RoleQueryReq;
import com.paob.tms.dto.req.RoleUpdateReq;
import com.paob.tms.dto.resp.RoleResp;
import com.paob.tms.service.SysRoleService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/role")
public class SysRoleController {
    @Resource
    private SysRoleService roleService;

    @GetMapping("/list")
    public Page<RoleResp> queryRoleList(RoleQueryReq req) {
        return roleService.getRoleList(req);
    }

    @PostMapping("/add")
    public void addRole(@RequestBody RoleAddReq req) {
        roleService.addRole(req);
    }

    @PostMapping("/update")
    public void updateRole(@RequestBody RoleUpdateReq req) {
        roleService.updateRole(req);
    }
} 