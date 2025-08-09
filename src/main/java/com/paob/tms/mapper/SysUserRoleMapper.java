package com.paob.tms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paob.tms.dto.resp.UserRoleResp;
import com.paob.tms.model.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    List<UserRoleResp> selectUserRoles(@Param("userId") Long userId);
} 