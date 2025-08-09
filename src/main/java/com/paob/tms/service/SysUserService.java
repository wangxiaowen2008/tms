package com.paob.tms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.UserAddReq;
import com.paob.tms.dto.req.UserQueryReq;
import com.paob.tms.dto.req.UserUpdateReq;
import com.paob.tms.dto.resp.UserResp;

public interface SysUserService {
    Page<UserResp> queryUserList(UserQueryReq req);
    void addUser(UserAddReq req);
    void updateUser(UserUpdateReq req);
    UserResp getUserDetail(Long id);
    void deleteUser(Long id);
} 