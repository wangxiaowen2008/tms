package com.paob.tms.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.BankAccountBalanceQueryReq;
import com.paob.tms.dto.resp.BankAccountBalanceExcelResp;
import com.paob.tms.dto.resp.BankAccountBalanceResp;
import com.paob.tms.mapper.BankAccountBalanceMapper;
import com.paob.tms.service.BankAccountBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountBalanceServiceImpl implements BankAccountBalanceService {

    private final BankAccountBalanceMapper bankAccountBalanceMapper;

    @Override
    public Page<BankAccountBalanceResp> queryBankAccountBalancePage(BankAccountBalanceQueryReq query) {
        // 设置默认查询日期为昨天
        if (query.getDate() == null) {
            query.setDate(LocalDate.now().minusDays(1));
        }
        
        // 创建分页对象
        Page<BankAccountBalanceResp> page = new Page<>(query.getPageNum(), query.getPageSize());
        
        // 执行分页查询
        return bankAccountBalanceMapper.selectBankAccountBalancePage(page, query);
    }

    @Override
    public void exportBankAccountBalance(BankAccountBalanceQueryReq query, HttpServletResponse response) throws IOException {
        // 设置默认查询日期为昨天
        if (query.getDate() == null) {
            query.setDate(LocalDate.now().minusDays(1));
        }
        
        // 查询所有数据（不分页）
        Page<BankAccountBalanceResp> page = new Page<>(1, Integer.MAX_VALUE);
        Page<BankAccountBalanceResp> result = bankAccountBalanceMapper.selectBankAccountBalancePage(page, query);
        List<BankAccountBalanceResp> list = result.getRecords();

        // 转换为Excel导出对象
        List<BankAccountBalanceExcelResp> excelList = new ArrayList<>();
        for (BankAccountBalanceResp balance : list) {
            BankAccountBalanceExcelResp excelResp = new BankAccountBalanceExcelResp();
            BeanUtils.copyProperties(balance, excelResp);
            excelList.add(excelResp);
        }

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("银行账号余额对账列表_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")), StandardCharsets.UTF_8.name());
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 导出Excel
        EasyExcel.write(response.getOutputStream(), BankAccountBalanceExcelResp.class)
                .sheet("银行账号余额对账列表")
                .doWrite(excelList);
    }
} 