package com.paob.tms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.UserQueryReq;
import com.paob.tms.dto.resp.UserResp;
import com.paob.tms.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    Page<UserResp> selectUserList(Page<SysUser> page, @Param("req") UserQueryReq req);
} 