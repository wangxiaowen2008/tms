package com.paob.tms.service;

import com.paob.tms.dto.req.SegmentValueReq;
import com.paob.tms.dto.resp.SelectOptionResp;

import java.util.List;

public interface SegmentOptionService {
    List<SelectOptionResp> querySegmentList(SegmentValueReq req);
}