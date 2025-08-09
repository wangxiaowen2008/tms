package com.paob.tms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.MenuQueryReq;
import com.paob.tms.dto.resp.MenuResp;
import com.paob.tms.model.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    Page<MenuResp> selectMenuList(Page<SysMenu> page, @Param("req") MenuQueryReq req);
} 