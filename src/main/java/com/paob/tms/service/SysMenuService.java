package com.paob.tms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.MenuAddReq;
import com.paob.tms.dto.req.MenuQueryReq;
import com.paob.tms.dto.req.MenuUpdateReq;
import com.paob.tms.dto.resp.MenuResp;

public interface SysMenuService {
    Page<MenuResp> queryMenuList(MenuQueryReq req);
    void addMenu(MenuAddReq req);
    void updateMenu(MenuUpdateReq req);
    MenuResp getMenuDetail(Long id);
} 