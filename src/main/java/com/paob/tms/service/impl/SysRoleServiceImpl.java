package com.paob.tms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paob.tms.dto.req.RoleAddReq;
import com.paob.tms.dto.req.RoleQueryReq;
import com.paob.tms.dto.req.RoleUpdateReq;
import com.paob.tms.dto.resp.RoleResp;
import com.paob.tms.mapper.SysRoleMapper;
import com.paob.tms.mapper.SysRoleMenuMapper;
import com.paob.tms.model.SysRole;
import com.paob.tms.model.SysRoleMenu;
import com.paob.tms.service.SysRoleService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SysRoleMenuMapper roleMenuMapper;

    @Override
    public Page<RoleResp> getRoleList(RoleQueryReq req) {
        Page<SysRole> page = new Page<>(req.getPageNum(), req.getPageSize());
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(req.getRoleName())) {
            wrapper.like(SysRole::getRoleName, req.getRoleName());
        }
        if (req.getRoleStatus() != null) {
            wrapper.eq(SysRole::getRoleStatus, req.getRoleStatus());
        }
        
        page = this.page(page, wrapper);
        
        Page<RoleResp> respPage = new Page<>();
        BeanUtils.copyProperties(page, respPage, "records");
        
        List<RoleResp> respList = new ArrayList<>();
        for (SysRole role : page.getRecords()) {
            RoleResp resp = new RoleResp();
            BeanUtils.copyProperties(role, resp);
            respList.add(resp);
        }
        respPage.setRecords(respList);
        
        return respPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(RoleAddReq req) {
        // 1. 保存角色基本信息
        SysRole role = new SysRole();
        BeanUtils.copyProperties(req, role);
        role.setCreatedTime(LocalDateTime.now());
        this.save(role);

        // 2. 保存角色-菜单关系
        if (req.getMenuPermissions() != null && !req.getMenuPermissions().isEmpty()) {
            List<SysRoleMenu> roleMenus = req.getMenuPermissions().stream()
                .map(menu -> {
                    SysRoleMenu roleMenu = new SysRoleMenu();
                    roleMenu.setRoleId(role.getId());
                    roleMenu.setMenuId(menu.getMenuId());
                    roleMenu.setCreatedTime(LocalDateTime.now());
                    return roleMenu;
                })
                .collect(Collectors.toList());
            roleMenuMapper.insertBatch(roleMenus);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(RoleUpdateReq req) {
        // 1. 更新角色基本信息
        SysRole role = new SysRole();
        BeanUtils.copyProperties(req, role);
        role.setUpdatedTime(LocalDateTime.now());
        this.updateById(role);

        // 2. 删除原有的角色-菜单关系
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId, role.getId());
        roleMenuMapper.delete(wrapper);

        // 3. 保存新的角色-菜单关系
        if (req.getMenuPermissions() != null && !req.getMenuPermissions().isEmpty()) {
            List<SysRoleMenu> roleMenus = req.getMenuPermissions().stream()
                .map(menu -> {
                    SysRoleMenu roleMenu = new SysRoleMenu();
                    roleMenu.setRoleId(role.getId());
                    roleMenu.setMenuId(menu.getMenuId());
                    roleMenu.setCreatedTime(LocalDateTime.now());
                    return roleMenu;
                })
                .collect(Collectors.toList());
            roleMenuMapper.insertBatch(roleMenus);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long id) {
        // 1. 删除角色-菜单关系
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId, id);
        roleMenuMapper.delete(wrapper);

        // 2. 删除角色
        this.removeById(id);
    }
} 