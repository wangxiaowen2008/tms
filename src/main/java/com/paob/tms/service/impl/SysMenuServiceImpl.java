package com.paob.tms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.MenuAddReq;
import com.paob.tms.dto.req.MenuQueryReq;
import com.paob.tms.dto.req.MenuUpdateReq;
import com.paob.tms.dto.resp.MenuButtonResp;
import com.paob.tms.dto.resp.MenuResp;
import com.paob.tms.mapper.SysMenuMapper;
import com.paob.tms.mapper.SysMenuButtonMapper;
import com.paob.tms.model.SysMenu;
import com.paob.tms.model.SysMenuButton;
import com.paob.tms.service.SysMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Resource
    private SysMenuMapper menuMapper;
    @Resource
    private SysMenuButtonMapper menuButtonMapper;

    @Override
    public Page<MenuResp> queryMenuList(MenuQueryReq req) {
        Page<SysMenu> page = new Page<>(req.getPageNum(), req.getPageSize());
        Page<MenuResp> resultPage = menuMapper.selectMenuList(page, req);
        
        // 查询每个菜单的按钮
        resultPage.getRecords().forEach(menuResp -> {
            List<MenuButtonResp> buttons = menuButtonMapper.selectButtonsByMenuId(menuResp.getId());
            menuResp.setButtons(buttons);
        });
        
        return resultPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMenu(MenuAddReq req) {
        // 添加菜单
        SysMenu menu = new SysMenu();
        BeanUtils.copyProperties(req, menu);
        menu.setCreatedTime(LocalDateTime.now());
        menu.setUpdatedTime(LocalDateTime.now());
        menuMapper.insert(menu);

        // 添加按钮
        if (req.getButtons() != null) {
            req.getButtons().forEach(buttonReq -> {
                SysMenuButton button = new SysMenuButton();
                BeanUtils.copyProperties(buttonReq, button);
                button.setMenuId(menu.getId());
                button.setCreatedTime(LocalDateTime.now());
                button.setUpdatedTime(LocalDateTime.now());
                menuButtonMapper.insert(button);
            });
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(MenuUpdateReq req) {
        SysMenu menu = new SysMenu();
        BeanUtils.copyProperties(req, menu);
        menu.setUpdatedTime(LocalDateTime.now());
        menuMapper.updateById(menu);
    }

    @Override
    public MenuResp getMenuDetail(Long id) {
        // 查询菜单基本信息
        SysMenu menu = menuMapper.selectById(id);
        if (menu == null) {
            return null;
        }

        // 转换为响应对象
        MenuResp resp = new MenuResp();
        BeanUtils.copyProperties(menu, resp);

        // 查询菜单按钮
        List<MenuButtonResp> buttons = menuButtonMapper.selectButtonsByMenuId(id);
        resp.setButtons(buttons);

        return resp;
    }
} 