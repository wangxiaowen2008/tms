package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.BankAccountBalanceQueryReq;
import com.paob.tms.dto.resp.BankAccountBalanceResp;
import com.paob.tms.service.BankAccountBalanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "银行账号余额对账")
@RestController
@RequestMapping("/api/bank-account-balance")
@RequiredArgsConstructor
public class BankAccountBalanceController {

    private final BankAccountBalanceService bankAccountBalanceService;

    @Operation(summary = "查询银行账号余额对账列表")
    @GetMapping("/list")
    public Page<BankAccountBalanceResp> list(BankAccountBalanceQueryReq query) {
        return bankAccountBalanceService.queryBankAccountBalancePage(query);
    }

    @Operation(summary = "导出银行账号余额对账列表")
    @GetMapping("/export")
    public void export(BankAccountBalanceQueryReq query, HttpServletResponse response) throws IOException {
        bankAccountBalanceService.exportBankAccountBalance(query, response);
    }
} 