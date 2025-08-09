package com.paob.tms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.FxListReq;
import com.paob.tms.dto.resp.FxListResp;

import jakarta.servlet.http.HttpServletResponse;

public interface TmsFxService {
    
    /**
     * 分页查询外汇交易列表
     *
     * @param req 查询条件
     * @return 分页结果
     */
    IPage<FxListResp> listFx(FxListReq req);
    
    /**
     * 导出外汇交易列表
     *
     * @param req 查询条件
     * @param response HTTP响应对象
     */
    void exportFx(FxListReq req, HttpServletResponse response);
} 