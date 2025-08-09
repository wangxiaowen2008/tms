package com.paob.tms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.CorporateActionQueryReq;
import com.paob.tms.dto.resp.CorporateActionResp;
import com.paob.tms.model.CorporateAction;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface CorporateActionService {
    
    /**
     * 分页查询公司行为列表
     *
     * @param req 查询条件
     * @return 分页结果
     */
    IPage<CorporateActionResp> queryList(CorporateActionQueryReq req);
    
    /**
     * 导出公司行为列表
     *
     * @param req 查询条件
     * @param response 响应对象
     */
    void exportList(CorporateActionQueryReq req, HttpServletResponse response);
    
    /**
     * 批量制证
     *
     * @param actionNos 公司行为编号列表
     * @return 处理结果
     */
    String batchVoucher(List<String> actionNos);
    
    /**
     * 查询公司行为详情
     *
     * @param actionNo 公司行为编号
     * @return 详情信息
     */
    CorporateActionResp getDetail(String actionNo);

    /**
     * 计算公司行为
     */
    void calculateCorporateAction();
} 