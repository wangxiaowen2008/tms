package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.SubjectBalanceQueryReq;
import com.paob.tms.model.TmsSubjectBalance;
import com.paob.tms.service.TmsSubjectBalanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Api(tags = "科目余额管理")
@RestController
@RequestMapping("/api/subject-balance")
public class TmsSubjectBalanceController {

    @Autowired
    private TmsSubjectBalanceService subjectBalanceService;

    @ApiOperation("查询科目余额列表")
    @GetMapping("/list")
    public IPage<TmsSubjectBalance> list(SubjectBalanceQueryReq queryDTO) {
        IPage<TmsSubjectBalance> page = subjectBalanceService.querySubjectBalancePage(queryDTO);
        return page;
    }

    @ApiOperation("查询科目余额详情")
    @GetMapping("/{codeCombinationId}")
    public TmsSubjectBalance detail(@ApiParam("组合代码ID") @PathVariable String codeCombinationId) {
        TmsSubjectBalance detail = subjectBalanceService.getSubjectBalanceDetail(codeCombinationId);
        return detail;
    }

    @ApiOperation("导出科目余额列表")
    @GetMapping("/export")
    public void export(SubjectBalanceQueryReq queryDTO, HttpServletResponse response) throws IOException {
        subjectBalanceService.exportSubjectBalance(queryDTO, response);
    }
} 