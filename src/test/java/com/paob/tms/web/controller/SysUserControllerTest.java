package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.UserAddReq;
import com.paob.tms.dto.req.UserQueryReq;
import com.paob.tms.dto.req.UserUpdateReq;
import com.paob.tms.dto.resp.UserResp;
import com.paob.tms.service.SysUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SysUserControllerTest {

    @Mock
    private SysUserService userService;

    @InjectMocks
    private SysUserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void queryUserList_ShouldReturnUserList() throws Exception {
        // 准备测试数据
        UserQueryReq req = new UserQueryReq();
        Page<UserResp> expectedPage = new Page<>();
        when(userService.queryUserList(any(UserQueryReq.class))).thenReturn(expectedPage);

        // 执行测试
        mockMvc.perform(get("/api/user/list")
                .param("pageNum", "1")
                .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        // 验证service方法被调用
        verify(userService).queryUserList(any(UserQueryReq.class));
    }

    @Test
    void addUser_ShouldSucceed() throws Exception {
        // 准备测试数据
        UserAddReq req = new UserAddReq();
        doNothing().when(userService).addUser(any(UserAddReq.class));

        // 执行测试
        mockMvc.perform(post("/api/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(userService).addUser(any(UserAddReq.class));
    }

    @Test
    void updateUser_ShouldSucceed() throws Exception {
        // 准备测试数据
        UserUpdateReq req = new UserUpdateReq();
        doNothing().when(userService).updateUser(any(UserUpdateReq.class));

        // 执行测试
        mockMvc.perform(post("/api/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(userService).updateUser(any(UserUpdateReq.class));
    }

    @Test
    void getUserDetail_ShouldReturnUserDetail() throws Exception {
        // 准备测试数据
        Long userId = 1L;
        UserResp expectedUser = new UserResp();
        when(userService.getUserDetail(userId)).thenReturn(expectedUser);

        // 执行测试
        mockMvc.perform(get("/api/user/detail/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        // 验证service方法被调用
        verify(userService).getUserDetail(userId);
    }

    @Test
    void deleteUser_ShouldSucceed() throws Exception {
        // 准备测试数据
        Long userId = 1L;
        doNothing().when(userService).deleteUser(userId);

        // 执行测试
        mockMvc.perform(delete("/api/user/{id}", userId))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(userService).deleteUser(userId);
    }
} 