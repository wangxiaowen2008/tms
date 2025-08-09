package com.paob.tms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.MenuAddReq;
import com.paob.tms.dto.req.MenuQueryReq;
import com.paob.tms.dto.req.MenuUpdateReq;
import com.paob.tms.dto.resp.MenuResp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class SysMenuServiceTest {

    @InjectMocks
    private SysMenuService sysMenuService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void queryMenuList_ShouldReturnPage() {
        // Arrange
        MenuQueryReq req = new MenuQueryReq();
        
        // Act
        Page<MenuResp> result = sysMenuService.queryMenuList(req);
        
        // Assert
        assertNotNull(result);
    }

    @Test
    void addMenu_ShouldAddSuccessfully() {
        // Arrange
        MenuAddReq req = new MenuAddReq();
        
        // Act & Assert
        assertDoesNotThrow(() -> sysMenuService.addMenu(req));
    }

    @Test
    void updateMenu_ShouldUpdateSuccessfully() {
        // Arrange
        MenuUpdateReq req = new MenuUpdateReq();
        
        // Act & Assert
        assertDoesNotThrow(() -> sysMenuService.updateMenu(req));
    }

    @Test
    void getMenuDetail_ShouldReturnDetail() {
        // Arrange
        Long id = 1L;
        
        // Act
        MenuResp result = sysMenuService.getMenuDetail(id);
        
        // Assert
        assertNotNull(result);
    }
} 