package com.paob.tms.web.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.FpsDwRepoQueryReq;
import com.paob.tms.dto.req.FpsDwRepoMakeVoucherReq;
import com.paob.tms.dto.resp.FpsDwRepoResp;
import com.paob.tms.service.FpsDwRepoService;
import com.paob.tms.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * REPO交易控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/fps-dw-repo")
@Api(tags = "REPO交易管理")
public class FpsDwRepoController {

    private final FpsDwRepoService fpsDwRepoService;

    public FpsDwRepoController(FpsDwRepoService fpsDwRepoService) {
        this.fpsDwRepoService = fpsDwRepoService;
    }

    /**
     * 分页查询REPO交易列表
     *
     * @param condition 查询条件
     * @return REPO交易分页结果
     */
    @GetMapping("/list")
    @ApiOperation(value = "分页查询REPO交易列表")
    public IPage<FpsDwRepoResp> queryList(@ApiParam("查询条件") FpsDwRepoQueryReq condition) {
        log.info("查询REPO交易列表，查询条件：{}", condition);
        return fpsDwRepoService.queryList(condition);
    }

    /**
     * 获取REPO交易详情
     *
     * @param tradeNumber 交易编号
     * @return REPO交易详情
     */
    @GetMapping("/detail/{tradeNumber}")
    @ApiOperation(value = "获取REPO交易详情")
    public FpsDwRepoResp getDetail(@ApiParam(value = "交易编号", required = true) @PathVariable String tradeNumber) {
        log.info("获取REPO交易详情，交易编号：{}", tradeNumber);
        return fpsDwRepoService.getDetail(tradeNumber);
    }

    /**
     * 批量制证
     *
     * @param req 制证请求
     * @return 制证结果
     */
    @PostMapping("/make-voucher")
    @ApiOperation(value = "批量制证")
    public boolean makeVoucher(@ApiParam(value = "制证请求", required = true) @RequestBody FpsDwRepoMakeVoucherReq req) {
        log.info("批量制证，交易编号列表：{}", req.getTradeNumbers());
        return fpsDwRepoService.makeVoucher(req);
    }

    /**
     * 导出REPO交易数据
     *
     * @param condition 查询条件
     * @param response HTTP响应
     */
    @GetMapping("/export")
    @ApiOperation(value = "导出REPO交易数据")
    public void export(@ApiParam("查询条件") FpsDwRepoQueryReq condition, HttpServletResponse response) throws IOException {
        log.info("导出REPO交易数据，查询条件：{}", condition);
        List<FpsDwRepoResp> data = fpsDwRepoService.export(condition);
        
        // 设置响应头
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String fileName = DateUtil.getTimestampFileName("Repo_", ".xlsx");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);

        // 使用EasyExcel写入数据
        EasyExcel.write(response.getOutputStream(), FpsDwRepoResp.class)
                .sheet("REPO交易数据")
                .doWrite(data);
    }
} 