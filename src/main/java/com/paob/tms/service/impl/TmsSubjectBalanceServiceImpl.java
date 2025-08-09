package com.paob.tms.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paob.tms.model.TmsSubjectBalance;
import com.paob.tms.mapper.TmsSubjectBalanceMapper;
import com.paob.tms.service.TmsSubjectBalanceService;
import com.paob.tms.dto.req.SubjectBalanceQueryReq;
import com.paob.tms.dto.resp.SubjectBalanceExcelResp;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TmsSubjectBalanceServiceImpl extends ServiceImpl<TmsSubjectBalanceMapper, TmsSubjectBalance> implements TmsSubjectBalanceService {

    @Override
    public IPage<TmsSubjectBalance> querySubjectBalancePage(SubjectBalanceQueryReq queryDTO) {
        // 设置默认值
        if (queryDTO.getPageNum() == null) {
            queryDTO.setPageNum(1);
        }
        if (queryDTO.getPageSize() == null) {
            queryDTO.setPageSize(10);
        }
        if (!StringUtils.hasText(queryDTO.getSetOfBooksId())) {
            queryDTO.setSetOfBooksId("HK SOB");
        }
        if (!StringUtils.hasText(queryDTO.getSegment1())) {
            queryDTO.setSegment1("800000");
        }
        
        Page<TmsSubjectBalance> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        return baseMapper.selectSubjectBalancePage(page, queryDTO);
    }

    @Override
    public void exportSubjectBalance(SubjectBalanceQueryReq queryDTO, HttpServletResponse response) throws IOException {
        // 设置默认值
        if (!StringUtils.hasText(queryDTO.getSetOfBooksId())) {
            queryDTO.setSetOfBooksId("HK SOB");
        }
        if (!StringUtils.hasText(queryDTO.getSegment1())) {
            queryDTO.setSegment1("800000");
        }

        // 查询所有数据（不分页）
        Page<TmsSubjectBalance> page = new Page<>(1, Integer.MAX_VALUE);
        IPage<TmsSubjectBalance> result = baseMapper.selectSubjectBalancePage(page, queryDTO);
        List<TmsSubjectBalance> list = result.getRecords();

        // 转换为Excel导出对象
        List<SubjectBalanceExcelResp> excelList = new ArrayList<>();
        for (TmsSubjectBalance balance : list) {
            SubjectBalanceExcelResp excelResp = new SubjectBalanceExcelResp();
            BeanUtils.copyProperties(balance, excelResp);
            excelList.add(excelResp);
        }

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("科目余额列表_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")), StandardCharsets.UTF_8.name());
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 导出Excel
        EasyExcel.write(response.getOutputStream(), SubjectBalanceExcelResp.class)
                .sheet("科目余额列表")
                .doWrite(excelList);
    }

    @Override
    public TmsSubjectBalance getSubjectBalanceDetail(String codeCombinationId) {
        // 根据组合代码ID查询科目余额详情
        return baseMapper.selectByCodeCombinationId(codeCombinationId);
    }
} 