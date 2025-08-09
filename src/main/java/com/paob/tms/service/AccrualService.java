package com.paob.tms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.AccrualQueryReq;
import com.paob.tms.dto.req.BatchInterestJournalReq;
import com.paob.tms.dto.resp.AccrualResp;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 计提数据服务接口
 */
public interface AccrualService {
    
    /**
     * 分页查询计提数据
     *
     * @param req 查询条件
     * @return 分页结果
     */
    IPage<AccrualResp> queryPage(AccrualQueryReq req);
    
    /**
     * 导出计提数据
     *
     * @param req 查询条件
     * @param response HTTP响应
     */
    void export(AccrualQueryReq req, HttpServletResponse response);
    
    /**
     * 批量计提利息制证
     *
     * @param req 请求参数
     * @return 处理结果
     */
    String batchInterestJournal(BatchInterestJournalReq req);
    
    /**
     * 生成计提数据
     *
     * @return 处理结果
     */
    void generate();
} 