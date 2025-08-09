package com.paob.tms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.UserAddReq;
import com.paob.tms.dto.req.UserQueryReq;
import com.paob.tms.dto.req.UserUpdateReq;
import com.paob.tms.dto.resp.UserResp;
import com.paob.tms.dto.resp.UserRoleResp;
import com.paob.tms.mapper.SysUserMapper;
import com.paob.tms.mapper.SysUserRoleMapper;
import com.paob.tms.model.SysUser;
import com.paob.tms.model.SysUserRole;
import com.paob.tms.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserMapper userMapper;
    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Override
    public Page<UserResp> queryUserList(UserQueryReq req) {
        Page<SysUser> page = new Page<>(req.getPageNum(), req.getPageSize());
        Page<UserResp> resultPage = userMapper.selectUserList(page, req);
        
        // 查询每个用户的角色
        resultPage.getRecords().forEach(userResp -> {
            List<UserRoleResp> roles = userRoleMapper.selectUserRoles(userResp.getId());
            userResp.setRoles(roles);
        });
        
        return resultPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(UserAddReq req) {
        // 添加用户
        SysUser user = new SysUser();
        BeanUtils.copyProperties(req, user);
        user.setCreatedTime(LocalDateTime.now());
        user.setUpdatedTime(LocalDateTime.now());
        userMapper.insert(user);

        // 添加用户角色关联
        if (req.getRoleIds() != null) {
            req.getRoleIds().forEach(roleId -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(roleId);
                userRole.setCreatedTime(LocalDateTime.now());
                userRoleMapper.insert(userRole);
            });
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserUpdateReq req) {
        // 更新用户
        SysUser user = new SysUser();
        BeanUtils.copyProperties(req, user);
        user.setUpdatedTime(LocalDateTime.now());
        userMapper.updateById(user);

        // 删除原有的用户角色关联
        userRoleMapper.delete(new QueryWrapper<SysUserRole>().eq("user_id", user.getId()));

        // 重新添加用户角色关联
        if (req.getRoleIds() != null) {
            req.getRoleIds().forEach(roleId -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(roleId);
                userRole.setCreatedTime(LocalDateTime.now());
                userRoleMapper.insert(userRole);
            });
        }
    }

    @Override
    public UserResp getUserDetail(Long id) {
        SysUser user = userMapper.selectById(id);
        if (user == null) {
            return null;
        }
        
        UserResp resp = new UserResp();
        BeanUtils.copyProperties(user, resp);
        
        // 查询用户角色
        List<UserRoleResp> roles = userRoleMapper.selectUserRoles(id);
        resp.setRoles(roles);
        
        return resp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        // 删除用户角色关联
        userRoleMapper.delete(new QueryWrapper<SysUserRole>().eq("user_id", id));
        
        // 删除用户
        userMapper.deleteById(id);
    }
} 