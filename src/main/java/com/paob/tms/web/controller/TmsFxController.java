package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.FxListReq;
import com.paob.tms.dto.resp.FxListResp;
import com.paob.tms.service.TmsFxService;
import com.paob.tms.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/fx")
@Api(tags = "外汇交易管理")
public class TmsFxController {

    private final TmsFxService tmsFxService;

    public TmsFxController(TmsFxService tmsFxService) {
        this.tmsFxService = tmsFxService;
    }

    @GetMapping("/list")
    @ApiOperation("查询外汇交易列表")
    public IPage<FxListResp> listFx(@ApiParam("查询条件") FxListReq req) {
        log.info("查询外汇交易列表，查询条件：{}", req);
        return tmsFxService.listFx(req);
    }

    @GetMapping("/export")
    @ApiOperation("导出外汇交易列表")
    public void exportFx(@ApiParam("查询条件") FxListReq req, HttpServletResponse response) {
        log.info("导出外汇交易列表，查询条件：{}", req);
        
        // 设置响应头
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String fileName = DateUtil.getTimestampFileName("FX_", ".xlsx");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);

        // 导出数据
        tmsFxService.exportFx(req, response);
    }
} 