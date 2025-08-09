package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.MenuAddReq;
import com.paob.tms.dto.req.MenuQueryReq;
import com.paob.tms.dto.req.MenuUpdateReq;
import com.paob.tms.dto.resp.MenuResp;
import com.paob.tms.service.SysMenuService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/menu")
public class SysMenuController {
    @Resource
    private SysMenuService menuService;

    @GetMapping("/list")
    public Page<MenuResp> queryMenuList(MenuQueryReq req) {
        return menuService.queryMenuList(req);
    }

    @PostMapping("/add")
    public void addMenu(@RequestBody MenuAddReq req) {
        menuService.addMenu(req);
    }

    @PostMapping("/update")
    public void updateMenu(@RequestBody MenuUpdateReq req) {
        menuService.updateMenu(req);
    }

    @GetMapping("/detail/{id}")
    public MenuResp getMenuDetail(@PathVariable Long id) {
        return menuService.getMenuDetail(id);
    }
} 