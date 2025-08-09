package com.paob.tms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paob.tms.dto.resp.MenuButtonResp;
import com.paob.tms.model.SysMenuButton;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuButtonMapper extends BaseMapper<SysMenuButton> {
    List<MenuButtonResp> selectButtonsByMenuId(@Param("menuId") Long menuId);
} 