package com.paob.tms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paob.tms.model.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    void insertBatch(@Param("list") List<SysRoleMenu> list);
} 