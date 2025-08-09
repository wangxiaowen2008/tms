package com.paob.tms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.TradeBlotterQueryReq;
import com.paob.tms.dto.resp.TradeBlotterResp;
import com.paob.tms.model.TradeBlotter;
import com.paob.tms.service.TradeBlotterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 交易流水控制器
 */
@Api(tags = "交易流水管理")
@RestController
@RequestMapping("/api/trade-blotter")
public class TradeBlotterController {

    @Autowired
    private TradeBlotterService tradeBlotterService;

    @ApiOperation("查询交易流水列表")
    @GetMapping("/list")
    public IPage<TradeBlotterResp> queryTradeBlotterList(TradeBlotterQueryReq req) {
        return tradeBlotterService.queryTradeBlotterList(req);
    }

    @ApiOperation("获取交易流水详情")
    @GetMapping("/{tradeNumber}")
    public TradeBlotterResp getTradeBlotterDetail(
            @ApiParam(value = "交易编号", required = true)
            @PathVariable String tradeNumber) {
        return tradeBlotterService.getTradeBlotterDetail(tradeNumber);
    }

    @ApiOperation("上传交易流水文件")
    @PostMapping("/upload")
    public void uploadTradeBlotterFile(
            @ApiParam(value = "交易流水文件", required = true)
            @RequestParam("file") MultipartFile file) throws IOException {
        tradeBlotterService.uploadTradeBlotterFile(file);
    }

    @ApiOperation("上传其他附件")
    @PostMapping("/uploadOther")
    public void uploadOtherAttachment(
            @ApiParam(value = "附件文件", required = true)
            @RequestParam("file") MultipartFile file) throws IOException {
        tradeBlotterService.uploadOtherAttachment(file);
    }

    @ApiOperation("确认上传")
    @GetMapping("/confirm")
    public void confirmUpload() {
        tradeBlotterService.confirmUpload();
    }

    @ApiOperation("生成凭证")
    @PostMapping("/make-voucher")
    public void makeVoucher(
            @ApiParam(value = "交易编号列表", required = true)
            @RequestBody List<String> tradeNumbers) {
        tradeBlotterService.makeVoucher(tradeNumbers);
    }

    @ApiOperation("导出交易流水")
    @GetMapping("/export")
    public void exportTradeBlotter(
            @ApiParam("查询条件")
            TradeBlotterQueryReq req,
            HttpServletResponse response) throws IOException {
        tradeBlotterService.exportTradeBlotter(req, response);
    }

    /**
     * 查询所有ISIN
     * @return ISIN列表
     */
    @GetMapping("/queryIsin")
    public List<String> queryIsin() {
        return tradeBlotterService.queryAllIsin();
    }
} 