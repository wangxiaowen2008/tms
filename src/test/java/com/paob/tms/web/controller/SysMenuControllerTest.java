package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.MenuAddReq;
import com.paob.tms.dto.req.MenuQueryReq;
import com.paob.tms.dto.req.MenuUpdateReq;
import com.paob.tms.dto.resp.MenuResp;
import com.paob.tms.service.SysMenuService;
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

class SysMenuControllerTest {

    @Mock
    private SysMenuService menuService;

    @InjectMocks
    private SysMenuController menuController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(menuController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void queryMenuList_ShouldReturnMenuList() throws Exception {
        // 准备测试数据
        MenuQueryReq req = new MenuQueryReq();
        Page<MenuResp> expectedPage = new Page<>();
        when(menuService.queryMenuList(any(MenuQueryReq.class))).thenReturn(expectedPage);

        // 执行测试
        mockMvc.perform(get("/api/menu/list")
                .param("pageNum", "1")
                .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        // 验证service方法被调用
        verify(menuService).queryMenuList(any(MenuQueryReq.class));
    }

    @Test
    void addMenu_ShouldSucceed() throws Exception {
        // 准备测试数据
        MenuAddReq req = new MenuAddReq();
        doNothing().when(menuService).addMenu(any(MenuAddReq.class));

        // 执行测试
        mockMvc.perform(post("/api/menu/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(menuService).addMenu(any(MenuAddReq.class));
    }

    @Test
    void updateMenu_ShouldSucceed() throws Exception {
        // 准备测试数据
        MenuUpdateReq req = new MenuUpdateReq();
        doNothing().when(menuService).updateMenu(any(MenuUpdateReq.class));

        // 执行测试
        mockMvc.perform(post("/api/menu/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        // 验证service方法被调用
        verify(menuService).updateMenu(any(MenuUpdateReq.class));
    }

    @Test
    void getMenuDetail_ShouldReturnMenuDetail() throws Exception {
        // 准备测试数据
        Long menuId = 1L;
        MenuResp expectedMenu = new MenuResp();
        when(menuService.getMenuDetail(menuId)).thenReturn(expectedMenu);

        // 执行测试
        mockMvc.perform(get("/api/menu/detail/{id}", menuId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        // 验证service方法被调用
        verify(menuService).getMenuDetail(menuId);
    }
} 