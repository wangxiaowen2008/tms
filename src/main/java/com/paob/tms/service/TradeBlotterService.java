package com.paob.tms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.paob.tms.dto.req.TradeBlotterQueryReq;
import com.paob.tms.dto.resp.TradeBlotterResp;
import com.paob.tms.model.TradeBlotter;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface TradeBlotterService extends IService<TradeBlotter> {
    IPage<TradeBlotterResp> queryTradeBlotterList(TradeBlotterQueryReq req);
    
    TradeBlotterResp getTradeBlotterDetail(String tradeNumber);
    
    void uploadTradeBlotterFile(MultipartFile file) throws IOException;
    
    void uploadOtherAttachment(MultipartFile file) throws IOException;
    
    void confirmUpload();
    
    void makeVoucher(List<String> tradeNumbers);
    
    void exportTradeBlotter(TradeBlotterQueryReq req, HttpServletResponse response) throws IOException;

    /**
     * 查询所有不重复的ISIN
     * @return ISIN列表
     */
    List<String> queryAllIsin();
} 