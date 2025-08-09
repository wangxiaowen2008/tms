package com.paob.tms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.model.TmsSubjectBalance;
import com.paob.tms.dto.req.SubjectBalanceQueryReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 科目余额Mapper接口
 */
@Mapper
public interface TmsSubjectBalanceMapper extends BaseMapper<TmsSubjectBalance> {
    
    /**
     * 分页查询科目余额
     * @param page 分页参数
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    IPage<TmsSubjectBalance> selectSubjectBalancePage(Page<TmsSubjectBalance> page, @Param("query") SubjectBalanceQueryReq queryDTO);

    /**
     * 根据组合代码ID查询科目余额详情
     * @param codeCombinationId 组合代码ID
     * @return 科目余额详情
     */
    TmsSubjectBalance selectByCodeCombinationId(@Param("codeCombinationId") String codeCombinationId);
} 