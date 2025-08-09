package com.paob.tms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paob.tms.dto.req.SegmentValueReq;
import com.paob.tms.dto.resp.SelectOptionResp;
import com.paob.tms.mapper.SegmentOptionMapper;
import com.paob.tms.service.SegmentOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SegmentOptionServiceImpl extends ServiceImpl<SegmentOptionMapper, SelectOptionResp> implements SegmentOptionService {
    @Autowired
    private SegmentOptionMapper segmentOptionMapper;

    @Override
    public List<SelectOptionResp> querySegmentList(SegmentValueReq req) {
        return segmentOptionMapper.querySegmentList(req);
    }
}