package com.paob.tms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.SubjectBalanceDetailQueryReq;
import com.paob.tms.dto.resp.SubjectBalanceDetailResp;

/**
 * 科目余额详情服务接口
 */
public interface SubjectBalanceDetailService {
    
    /**
     * 分页查询科目余额详情
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<SubjectBalanceDetailResp> querySubjectBalanceDetailPage(SubjectBalanceDetailQueryReq query);
} 