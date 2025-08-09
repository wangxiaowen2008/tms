package com.paob.tms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.FpsDwRepoQueryReq;
import com.paob.tms.dto.req.FpsDwRepoMakeVoucherReq;
import com.paob.tms.dto.resp.FpsDwRepoResp;
import com.paob.tms.model.FpsDwRepo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * REPO交易服务接口
 */
public interface FpsDwRepoService extends IService<FpsDwRepo> {
    
    /**
     * 分页查询REPO交易
     *
     * @param condition 查询条件
     * @return REPO交易分页结果
     */
    IPage<FpsDwRepoResp> queryList(FpsDwRepoQueryReq condition);
    
    /**
     * 根据交易编号查询REPO交易详情
     *
     * @param tradeNumber 交易编号
     * @return REPO交易详情
     */
    FpsDwRepoResp getDetail(String tradeNumber);
    
    /**
     * 批量制证
     *
     * @param req 制证请求
     * @return 制证结果
     */
    boolean makeVoucher(FpsDwRepoMakeVoucherReq req);
    
    /**
     * 导出REPO交易数据
     *
     * @param condition 查询条件
     * @return REPO交易数据列表
     */
    List<FpsDwRepoResp> export(FpsDwRepoQueryReq condition);
} 