package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.UserAddReq;
import com.paob.tms.dto.req.UserQueryReq;
import com.paob.tms.dto.req.UserUpdateReq;
import com.paob.tms.dto.resp.UserResp;
import com.paob.tms.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/user")
public class SysUserController {
    @Resource
    private SysUserService userService;

    @GetMapping("/list")
    public Page<UserResp> queryUserList(UserQueryReq req) {
        return userService.queryUserList(req);
    }

    @PostMapping("/add")
    public void addUser(@RequestBody UserAddReq req) {
        userService.addUser(req);
    }

    @PostMapping("/update")
    public void updateUser(@RequestBody UserUpdateReq req) {
        userService.updateUser(req);
    }

    @GetMapping("/detail/{id}")
    public UserResp getUserDetail(@PathVariable Long id) {
        return userService.getUserDetail(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
} 