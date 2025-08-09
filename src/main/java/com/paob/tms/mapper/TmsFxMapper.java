package com.paob.tms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.FxListReq;
import com.paob.tms.model.TmsFx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TmsFxMapper extends BaseMapper<TmsFx> {
    
    /**
     * 分页查询外汇交易列表
     *
     * @param page 分页参数
     * @param req 查询条件
     * @return 分页结果
     */
    IPage<TmsFx> selectFxList(Page<TmsFx> page, @Param("req") FxListReq req);
} 