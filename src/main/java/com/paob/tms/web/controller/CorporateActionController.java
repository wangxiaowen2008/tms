package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.CorporateActionQueryReq;
import com.paob.tms.dto.resp.CorporateActionResp;
import com.paob.tms.service.CorporateActionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/corporate-action")
@Tag(name = "公司行为管理")
public class CorporateActionController {

    private final CorporateActionService corporateActionService;

    public CorporateActionController(CorporateActionService corporateActionService) {
        this.corporateActionService = corporateActionService;
    }

    @GetMapping("/list")
    @Operation(summary = "查询公司行为列表")
    public IPage<CorporateActionResp> queryList(CorporateActionQueryReq req) {
        log.info("查询公司行为列表，查询条件：{}", req);
        return corporateActionService.queryList(req);
    }

    @GetMapping("/export")
    @Operation(summary = "导出公司行为列表")
    public void exportList(CorporateActionQueryReq req, HttpServletResponse response) {
        log.info("导出公司行为列表，查询条件：{}", req);
        corporateActionService.exportList(req, response);
    }

    @PostMapping("/batch-voucher")
    @Operation(summary = "批量制证")
    public String batchVoucher(@RequestBody List<String> actionNos) {
        return corporateActionService.batchVoucher(actionNos);
    }

    @GetMapping("/detail/{actionNo}")
    @Operation(summary = "查询公司行为详情")
    public CorporateActionResp getDetail(@PathVariable String actionNo) {
        return corporateActionService.getDetail(actionNo);
    }

    @GetMapping("/calculate")
    @Operation(summary = "计算公司行为")
    public void calculateCorporateAction() {
        corporateActionService.calculateCorporateAction();
    }
} 