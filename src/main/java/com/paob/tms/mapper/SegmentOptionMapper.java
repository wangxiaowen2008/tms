package com.paob.tms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paob.tms.dto.req.SegmentValueReq;
import com.paob.tms.dto.resp.SelectOptionResp;

import java.util.List;

public interface SegmentOptionMapper extends BaseMapper<SelectOptionResp> {
    /**
     * 导出外汇交易列表
     * @param req 查询条件
     * @return 分页结果
     */
    List<SelectOptionResp> querySegmentList(SegmentValueReq req);
}