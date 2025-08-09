package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.AccrualQueryReq;
import com.paob.tms.dto.req.BatchInterestJournalReq;
import com.paob.tms.dto.resp.AccrualResp;
import com.paob.tms.service.AccrualService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 计提数据控制器
 */
@RestController
@RequestMapping("/api/accrual")
@RequiredArgsConstructor
@Tag(name = "计提数据管理")
public class AccrualController {
    
    private final AccrualService accrualService;
    
    @GetMapping("/list")
    @ApiOperation("查询计提数据列表")
    public IPage<AccrualResp> list(AccrualQueryReq req) {
        return accrualService.queryPage(req);
    }
    
    @GetMapping("/export")
    @ApiOperation("导出计提数据")
    public void export(AccrualQueryReq req, HttpServletResponse response) {
        accrualService.export(req, response);
    }
    
    @PostMapping("/batch-interest-journal")
    @ApiOperation("批量计提利息制证")
    public String batchInterestJournal(@RequestBody BatchInterestJournalReq req) {
        return accrualService.batchInterestJournal(req);
    }
    
    @PostMapping("/generate")
    @ApiOperation("生成计提数据")
    public void generate() {
        accrualService.generate();
    }
} 