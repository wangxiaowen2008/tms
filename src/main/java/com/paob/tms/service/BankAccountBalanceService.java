package com.paob.tms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.BankAccountBalanceQueryReq;
import com.paob.tms.dto.resp.BankAccountBalanceResp;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface BankAccountBalanceService {
    
    /**
     * 分页查询银行账号余额对账列表
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<BankAccountBalanceResp> queryBankAccountBalancePage(BankAccountBalanceQueryReq query);

    /**
     * 导出银行账号余额对账列表
     *
     * @param query 查询条件
     * @param response HTTP响应
     * @throws IOException IO异常
     */
    void exportBankAccountBalance(BankAccountBalanceQueryReq query, HttpServletResponse response) throws IOException;
} 