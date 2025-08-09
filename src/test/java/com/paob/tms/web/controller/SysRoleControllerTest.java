package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.RoleAddReq;
import com.paob.tms.dto.req.RoleQueryReq;
import com.paob.tms.dto.req.RoleUpdateReq;
import com.paob.tms.dto.resp.RoleResp;
import com.paob.tms.service.SysRoleService;
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

class SysRoleControllerTest {

    @Mock
    private SysRoleService roleService;

    @InjectMocks
    private SysRoleController roleController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void queryRoleList_ShouldReturnRoleList() throws Exception {
        // 准备测试数据
        RoleQueryReq req = new RoleQueryReq();
        Page<RoleResp> expectedPage = new Page<>();
        when(roleService.getRoleList(any(RoleQueryReq.class))).thenReturn(expectedPage);

        // 执行测试
        mockMvc.perform(get("/api/role/list")
                .param("pageNum", "1")
                .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        // 验证service方法被调用
        verify(roleService).getRoleList(any(RoleQueryReq.class));
    }

    @Test
    void addRole_ShouldSucceed() throws Exception {
        // 准备测试数据
        RoleAddReq req = new RoleAddReq();
        doNothing().when(roleService).addRole(any(RoleAddReq.class));

        // 执行测试
        mockMvc.perform(post("/api/role/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(roleService).addRole(any(RoleAddReq.class));
    }

    @Test
    void updateRole_ShouldSucceed() throws Exception {
        // 准备测试数据
        RoleUpdateReq req = new RoleUpdateReq();
        doNothing().when(roleService).updateRole(any(RoleUpdateReq.class));

        // 执行测试
        mockMvc.perform(post("/api/role/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(roleService).updateRole(any(RoleUpdateReq.class));
    }
} 