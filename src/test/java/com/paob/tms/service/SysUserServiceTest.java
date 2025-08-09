package com.paob.tms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.UserAddReq;
import com.paob.tms.dto.req.UserQueryReq;
import com.paob.tms.dto.req.UserUpdateReq;
import com.paob.tms.dto.resp.UserResp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;

class SysUserServiceTest {

    @InjectMocks
    private SysUserService sysUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("查询用户列表 - 正常场景")
    void queryUserList_ShouldReturnPage() {
        // Arrange
        UserQueryReq req = new UserQueryReq();
        req.setPageNum(1);
        req.setPageSize(10);
        req.setUserName("testUser");
        req.setUmCode("TEST001");
        req.setEmail("test@example.com");
        req.setDepartment("技术部");
        req.setUserStatus(1);
        
        // Act
        Page<UserResp> result = sysUserService.queryUserList(req);
        
        // Assert
        assertNotNull(result);
        verify(sysUserService, times(1)).queryUserList(req);
    }

    @Test
    @DisplayName("查询用户列表 - 空查询条件")
    void queryUserList_WithEmptyQuery_ShouldReturnPage() {
        // Arrange
        UserQueryReq req = new UserQueryReq();
        
        // Act
        Page<UserResp> result = sysUserService.queryUserList(req);
        
        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("查询用户列表 - 无效用户名")
    void queryUserList_WithInvalidUserName_ShouldReturnEmptyPage() {
        // Arrange
        UserQueryReq req = new UserQueryReq();
        req.setPageNum(1);
        req.setPageSize(10);
        req.setUserName("不存在的用户");
        
        // Act
        Page<UserResp> result = sysUserService.queryUserList(req);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getRecords().isEmpty());
    }

    @Test
    @DisplayName("添加用户 - 正常场景")
    void addUser_ShouldAddSuccessfully() {
        // Arrange
        UserAddReq req = new UserAddReq();
        req.setUmCode("newUser");
        req.setUserName("新用户");
        req.setEmail("newuser@example.com");
        req.setDepartment("技术部");
        req.setUserStatus(1);
        req.setRoleIds(List.of(1L, 2L));
        
        // Act & Assert
        assertDoesNotThrow(() -> sysUserService.addUser(req));
        verify(sysUserService, times(1)).addUser(req);
    }

    @Test
    @DisplayName("添加用户 - 用户编号为空")
    void addUser_WithEmptyUmCode_ShouldThrowException() {
        // Arrange
        UserAddReq req = new UserAddReq();
        req.setUserName("新用户");
        req.setEmail("newuser@example.com");
        req.setDepartment("技术部");
        req.setUserStatus(1);
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sysUserService.addUser(req));
    }

    @Test
    @DisplayName("添加用户 - 用户名为空")
    void addUser_WithEmptyUserName_ShouldThrowException() {
        // Arrange
        UserAddReq req = new UserAddReq();
        req.setUmCode("newUser");
        req.setEmail("newuser@example.com");
        req.setDepartment("技术部");
        req.setUserStatus(1);
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sysUserService.addUser(req));
    }

    @Test
    @DisplayName("添加用户 - 空角色列表")
    void addUser_WithEmptyRoleIds_ShouldAddSuccessfully() {
        // Arrange
        UserAddReq req = new UserAddReq();
        req.setUmCode("newUser");
        req.setUserName("新用户");
        req.setEmail("newuser@example.com");
        req.setDepartment("技术部");
        req.setUserStatus(1);
        req.setRoleIds(new ArrayList<>());
        
        // Act & Assert
        assertDoesNotThrow(() -> sysUserService.addUser(req));
    }

    @Test
    @DisplayName("更新用户 - 正常场景")
    void updateUser_ShouldUpdateSuccessfully() {
        // Arrange
        UserUpdateReq req = new UserUpdateReq();
        req.setId(1L);
        req.setUmCode("updatedUser");
        req.setUserName("更新后的用户");
        req.setEmail("updated@example.com");
        req.setDepartment("技术部");
        req.setUserStatus(1);
        req.setRoleIds(List.of(1L, 2L));
        
        // Act & Assert
        assertDoesNotThrow(() -> sysUserService.updateUser(req));
        verify(sysUserService, times(1)).updateUser(req);
    }

    @Test
    @DisplayName("更新用户 - ID为空")
    void updateUser_WithEmptyId_ShouldThrowException() {
        // Arrange
        UserUpdateReq req = new UserUpdateReq();
        req.setUmCode("updatedUser");
        req.setUserName("更新后的用户");
        req.setEmail("updated@example.com");
        req.setDepartment("技术部");
        req.setUserStatus(1);
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sysUserService.updateUser(req));
    }

    @Test
    @DisplayName("更新用户 - 用户名为空")
    void updateUser_WithEmptyUserName_ShouldThrowException() {
        // Arrange
        UserUpdateReq req = new UserUpdateReq();
        req.setId(1L);
        req.setUmCode("updatedUser");
        req.setEmail("updated@example.com");
        req.setDepartment("技术部");
        req.setUserStatus(1);
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sysUserService.updateUser(req));
    }

    @Test
    @DisplayName("更新用户 - 空角色列表")
    void updateUser_WithEmptyRoleIds_ShouldUpdateSuccessfully() {
        // Arrange
        UserUpdateReq req = new UserUpdateReq();
        req.setId(1L);
        req.setUmCode("updatedUser");
        req.setUserName("更新后的用户");
        req.setEmail("updated@example.com");
        req.setDepartment("技术部");
        req.setUserStatus(1);
        req.setRoleIds(new ArrayList<>());
        
        // Act & Assert
        assertDoesNotThrow(() -> sysUserService.updateUser(req));
    }

    @Test
    @DisplayName("查询用户详情 - 正常场景")
    void getUserDetail_ShouldReturnDetail() {
        // Arrange
        Long id = 1L;
        
        // Act
        UserResp result = sysUserService.getUserDetail(id);
        
        // Assert
        assertNotNull(result);
        verify(sysUserService, times(1)).getUserDetail(id);
    }

    @Test
    @DisplayName("查询用户详情 - 无效用户ID")
    void getUserDetail_WithInvalidId_ShouldReturnNull() {
        // Arrange
        Long id = -1L;
        
        // Act
        UserResp result = sysUserService.getUserDetail(id);
        
        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("删除用户 - 正常场景")
    void deleteUser_ShouldDeleteSuccessfully() {
        // Arrange
        Long id = 1L;
        
        // Act & Assert
        assertDoesNotThrow(() -> sysUserService.deleteUser(id));
        verify(sysUserService, times(1)).deleteUser(id);
    }

    @Test
    @DisplayName("删除用户 - 无效用户ID")
    void deleteUser_WithInvalidId_ShouldThrowException() {
        // Arrange
        Long id = -1L;
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sysUserService.deleteUser(id));
    }
} 