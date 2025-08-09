package com.paob.tms.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.FxListReq;
import com.paob.tms.dto.resp.FxListResp;
import com.paob.tms.mapper.TmsFxMapper;
import com.paob.tms.model.TmsFx;
import com.paob.tms.service.TmsFxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TmsFxServiceImpl implements TmsFxService {

    private final TmsFxMapper tmsFxMapper;

    public TmsFxServiceImpl(TmsFxMapper tmsFxMapper) {
        this.tmsFxMapper = tmsFxMapper;
    }

    @Override
    public IPage<FxListResp> listFx(FxListReq req) {
        Page<TmsFx> page = new Page<>(req.getPageNum(), req.getPageSize());
        IPage<TmsFx> fxPage = tmsFxMapper.selectFxList(page, req);
        
        return fxPage.convert(this::convertToResp);
    }

    @Override
    public void exportFx(FxListReq req, HttpServletResponse response) {
        log.info("开始导出外汇交易数据");
        
        try {
            // 查询所有符合条件的数据
            List<TmsFx> fxList = tmsFxMapper.selectFxList(new Page<>(1, Integer.MAX_VALUE), req).getRecords();
            log.info("查询到{}条外汇交易数据", fxList.size());
            
            // 转换为响应DTO
            List<FxListResp> excelData = fxList.stream()
                    .map(this::convertToResp)
                    .collect(Collectors.toList());
            
            // 使用EasyExcel写入数据
            EasyExcel.write(response.getOutputStream(), FxListResp.class)
                    .registerWriteHandler(new com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy())
                    .sheet("外汇交易数据")
                    .doWrite(excelData);
            
            log.info("外汇交易数据导出成功");
        } catch (Exception e) {
            log.error("外汇交易数据导出失败", e);
            throw new RuntimeException("外汇交易数据导出失败", e);
        }
    }

    private FxListResp convertToResp(TmsFx fx) {
        FxListResp resp = new FxListResp();
        BeanUtils.copyProperties(fx, resp);
        return resp;
    }
} 