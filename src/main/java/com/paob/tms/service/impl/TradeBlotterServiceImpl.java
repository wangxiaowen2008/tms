package com.paob.tms.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paob.tms.dto.req.TradeBlotterQueryReq;
import com.paob.tms.dto.resp.TradeBlotterResp;
import com.paob.tms.mapper.TradeBlotterMapper;
import com.paob.tms.model.TradeBlotter;
import com.paob.tms.service.TradeBlotterService;
import com.paob.tms.util.ExcelUtil;
import com.paob.tms.util.FileUtil;
import com.paob.tms.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TradeBlotterServiceImpl extends ServiceImpl<TradeBlotterMapper, TradeBlotter> implements TradeBlotterService {

    @Autowired
    private TradeBlotterMapper tradeBlotterMapper;

    @Override
    public IPage<TradeBlotterResp> queryTradeBlotterList(TradeBlotterQueryReq req) {
        Page<TradeBlotter> page = new Page<>(req.getPageNum(), req.getPageSize());
        LambdaQueryWrapper<TradeBlotter> wrapper = new LambdaQueryWrapper<>();
        
        // 设置查询条件
        wrapper.like(req.getRemark() != null, TradeBlotter::getRemark, req.getRemark())
               .ge(req.getTradeDateStart() != null, TradeBlotter::getTradeDate, req.getTradeDateStart())
               .le(req.getTradeDateEnd() != null, TradeBlotter::getTradeDate, req.getTradeDateEnd())
               .eq(req.getCurrency() != null, TradeBlotter::getCurrency, req.getCurrency())
               .eq(req.getEntityId() != null, TradeBlotter::getEntityId, req.getEntityId())
               .eq(req.getTradeType() != null, TradeBlotter::getTradeType, req.getTradeType())
               .eq(req.getStatus() != null, TradeBlotter::getStatus, req.getStatus());

        IPage<TradeBlotter> tradeBlotterPage = tradeBlotterMapper.selectPage(page, wrapper);
        
        // 转换为TradeBlotterResp对象
        return tradeBlotterPage.convert(this::convertToResp);
    }

    @Override
    public TradeBlotterResp getTradeBlotterDetail(String tradeNumber) {
        TradeBlotter tradeBlotter = tradeBlotterMapper.selectByTradeNumber(tradeNumber);
        return tradeBlotter != null ? convertToResp(tradeBlotter) : null;
    }

    @Override
    @Transactional
    public void uploadTradeBlotterFile(MultipartFile file) throws IOException {
        // 保存文件
        String filePath = FileUtil.saveFile(file, "trade-blotter");
        
        // 解析Excel文件
        List<TradeBlotter> tradeBlotters = ExcelUtil.parseTradeBlotterFile(file);
        
        // 批量保存到数据库
        for (TradeBlotter tradeBlotter : tradeBlotters) {
            tradeBlotterMapper.insert(tradeBlotter);
        }
    }

    @Override
    @Transactional
    public void uploadOtherAttachment(MultipartFile file) throws IOException {
        // 保存文件
        String filePath = FileUtil.saveFile(file, "attachment");
        
        // TODO: 根据业务需求处理附件
    }

    @Override
    @Transactional
    public void confirmUpload() {
        // 更新所有未确认的交易记录状态
        List<TradeBlotter> unconfirmedTrades = tradeBlotterMapper.selectUnconfirmedTrades();
        for (TradeBlotter trade : unconfirmedTrades) {
            trade.setPaymentStatus("已确认");
            trade.setJournalStatus("待制证");
            tradeBlotterMapper.update(trade);
        }
    }

    @Override
    @Transactional
    public void makeVoucher(List<String> tradeNumbers) {
        for (String tradeNumber : tradeNumbers) {
            TradeBlotter tradeBlotter = tradeBlotterMapper.selectByTradeNumber(tradeNumber);
            if (tradeBlotter != null) {
                // 更新制证状态
                tradeBlotter.setJournalStatus("已制证");
                tradeBlotterMapper.update(tradeBlotter);
            }
        }
    }

    @Override
    public void exportTradeBlotter(TradeBlotterQueryReq req, HttpServletResponse response) throws IOException {
        log.info("开始导出交易流水数据");
        
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("交易流水", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        
        // 构建查询条件
        LambdaQueryWrapper<TradeBlotter> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(req.getRemark())) {
            wrapper.like(TradeBlotter::getRemark, req.getRemark());
        }
        if (req.getTradeDateStart() != null) {
            wrapper.ge(TradeBlotter::getTradeDate, req.getTradeDateStart());
        }
        if (req.getTradeDateEnd() != null) {
            wrapper.le(TradeBlotter::getTradeDate, req.getTradeDateEnd());
        }
        if (StringUtils.isNotBlank(req.getCurrency())) {
            wrapper.eq(TradeBlotter::getCurrency, req.getCurrency());
        }
        if (StringUtils.isNotBlank(req.getStatus())) {
            wrapper.eq(TradeBlotter::getStatus, req.getStatus());
        }
        
        // 查询数据
        List<TradeBlotter> tradeBlotters = this.list(wrapper);
        List<TradeBlotterResp> respList = tradeBlotters.stream()
                .map(item -> BeanCopyUtils.copyProperties(item, TradeBlotterResp.class))
                .collect(Collectors.toList());
        
        // 使用EasyExcel导出
        EasyExcel.write(response.getOutputStream(), TradeBlotterResp.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()) // 自动列宽
                .sheet("交易流水")
                .doWrite(respList);
        
        log.info("交易流水数据导出完成");
    }

    @Override
    public List<String> queryAllIsin() {
        return baseMapper.selectAllIsin();
    }

    private TradeBlotterResp convertToResp(TradeBlotter tradeBlotter) {
        if (tradeBlotter == null) {
            return null;
        }
        TradeBlotterResp resp = new TradeBlotterResp();
        BeanUtils.copyProperties(tradeBlotter, resp);
        return resp;
    }
} 