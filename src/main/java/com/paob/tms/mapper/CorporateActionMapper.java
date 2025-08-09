package com.paob.tms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.CorporateActionQueryReq;
import com.paob.tms.model.CorporateAction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CorporateActionMapper extends BaseMapper<CorporateAction> {
    
    /**
     * 分页查询公司行为列表
     *
     * @param page 分页参数
     * @param req 查询条件
     * @return 分页结果
     */
    IPage<CorporateAction> selectPage(Page<CorporateAction> page, @Param("req") CorporateActionQueryReq req);
} 