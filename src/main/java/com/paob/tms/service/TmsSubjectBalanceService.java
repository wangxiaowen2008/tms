package com.paob.tms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.paob.tms.model.TmsSubjectBalance;
import com.paob.tms.dto.req.SubjectBalanceQueryReq;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface TmsSubjectBalanceService extends IService<TmsSubjectBalance> {
    
    /**
     * 分页查询科目余额
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    IPage<TmsSubjectBalance> querySubjectBalancePage(SubjectBalanceQueryReq queryDTO);

    /**
     * 导出科目余额列表
     * @param queryDTO 查询条件
     * @param response HTTP响应对象
     * @throws IOException IO异常
     */
    void exportSubjectBalance(SubjectBalanceQueryReq queryDTO, HttpServletResponse response) throws IOException;

    /**
     * 查询科目余额详情
     * @param codeCombinationId 组合代码ID
     * @return 科目余额详情
     */
    TmsSubjectBalance getSubjectBalanceDetail(String codeCombinationId);
} 