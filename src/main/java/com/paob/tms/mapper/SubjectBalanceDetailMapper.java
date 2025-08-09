package com.paob.tms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.SubjectBalanceDetailQueryReq;
import com.paob.tms.dto.resp.SubjectBalanceDetailResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SubjectBalanceDetailMapper extends BaseMapper<SubjectBalanceDetailResp> {
    
    /**
     * 分页查询科目余额详情
     *
     * @param page 分页参数
     * @param query 查询条件
     * @return 分页结果
     */
    Page<SubjectBalanceDetailResp> selectSubjectBalanceDetailPage(Page<SubjectBalanceDetailResp> page, @Param("query") SubjectBalanceDetailQueryReq query);
} 