package com.paob.tms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.RoleAddReq;
import com.paob.tms.dto.req.RoleQueryReq;
import com.paob.tms.dto.req.RoleUpdateReq;
import com.paob.tms.dto.resp.RoleResp;
import com.paob.tms.dto.req.RoleMenuReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SysRoleServiceTest {

    @InjectMocks
    private SysRoleService sysRoleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("获取角色列表 - 正常场景")
    void getRoleList_ShouldReturnPage() {
        // Arrange
        RoleQueryReq req = new RoleQueryReq();
        req.setPageNum(1);
        req.setPageSize(10);
        
        // Act
        Page<RoleResp> result = sysRoleService.getRoleList(req);
        
        // Assert
        assertNotNull(result);
        verify(sysRoleService, times(1)).getRoleList(req);
    }

    @Test
    @DisplayName("获取角色列表 - 空查询条件")
    void getRoleList_WithEmptyQuery_ShouldReturnPage() {
        // Arrange
        RoleQueryReq req = new RoleQueryReq();
        
        // Act
        Page<RoleResp> result = sysRoleService.getRoleList(req);
        
        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("添加角色 - 正常场景")
    void addRole_ShouldAddSuccessfully() {
        // Arrange
        RoleAddReq req = new RoleAddReq();
        req.setRoleName("测试角色");
        req.setRoleStatus(1);
        
        List<RoleMenuReq> menuPermissions = new ArrayList<>();
        RoleMenuReq menuPermission = new RoleMenuReq();
        menuPermission.setMenuId(1L);
        menuPermission.setButtonIds(List.of(1L, 2L));
        menuPermissions.add(menuPermission);
        req.setMenuPermissions(menuPermissions);
        
        // Act & Assert
        assertDoesNotThrow(() -> sysRoleService.addRole(req));
        verify(sysRoleService, times(1)).addRole(req);
    }

    @Test
    @DisplayName("添加角色 - 空角色名称")
    void addRole_WithEmptyRoleName_ShouldThrowException() {
        // Arrange
        RoleAddReq req = new RoleAddReq();
        req.setRoleStatus(1);
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sysRoleService.addRole(req));
    }

    @Test
    @DisplayName("添加角色 - 空菜单权限")
    void addRole_WithEmptyMenuPermissions_ShouldAddSuccessfully() {
        // Arrange
        RoleAddReq req = new RoleAddReq();
        req.setRoleName("测试角色");
        req.setRoleStatus(1);
        req.setMenuPermissions(new ArrayList<>());
        
        // Act & Assert
        assertDoesNotThrow(() -> sysRoleService.addRole(req));
    }

    @Test
    @DisplayName("更新角色 - 正常场景")
    void updateRole_ShouldUpdateSuccessfully() {
        // Arrange
        RoleUpdateReq req = new RoleUpdateReq();
        req.setId(1L);
        req.setRoleName("更新后的角色");
        req.setRoleStatus(1);
        
        List<RoleMenuReq> menuPermissions = new ArrayList<>();
        RoleMenuReq menuPermission = new RoleMenuReq();
        menuPermission.setMenuId(1L);
        menuPermission.setButtonIds(List.of(1L, 2L));
        menuPermissions.add(menuPermission);
        req.setMenuPermissions(menuPermissions);
        
        // Act & Assert
        assertDoesNotThrow(() -> sysRoleService.updateRole(req));
        verify(sysRoleService, times(1)).updateRole(req);
    }

    @Test
    @DisplayName("更新角色 - ID为空")
    void updateRole_WithEmptyId_ShouldThrowException() {
        // Arrange
        RoleUpdateReq req = new RoleUpdateReq();
        req.setRoleName("更新后的角色");
        req.setRoleStatus(1);
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sysRoleService.updateRole(req));
    }

    @Test
    @DisplayName("更新角色 - 角色名称为空")
    void updateRole_WithEmptyRoleName_ShouldThrowException() {
        // Arrange
        RoleUpdateReq req = new RoleUpdateReq();
        req.setId(1L);
        req.setRoleStatus(1);
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sysRoleService.updateRole(req));
    }

    @Test
    @DisplayName("更新角色 - 空菜单权限")
    void updateRole_WithEmptyMenuPermissions_ShouldUpdateSuccessfully() {
        // Arrange
        RoleUpdateReq req = new RoleUpdateReq();
        req.setId(1L);
        req.setRoleName("更新后的角色");
        req.setRoleStatus(1);
        req.setMenuPermissions(new ArrayList<>());
        
        // Act & Assert
        assertDoesNotThrow(() -> sysRoleService.updateRole(req));
    }

    @Test
    @DisplayName("删除角色 - 正常场景")
    void deleteRole_ShouldDeleteSuccessfully() {
        // Arrange
        Long id = 1L;
        
        // Act & Assert
        assertDoesNotThrow(() -> sysRoleService.deleteRole(id));
        verify(sysRoleService, times(1)).deleteRole(id);
    }

    @Test
    @DisplayName("删除角色 - ID为空")
    void deleteRole_WithEmptyId_ShouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sysRoleService.deleteRole(null));
    }

    @Test
    @DisplayName("删除角色 - ID不存在")
    void deleteRole_WithNonExistentId_ShouldThrowException() {
        // Arrange
        Long id = 999L;
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sysRoleService.deleteRole(id));
    }
} 