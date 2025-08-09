package com.paob.tms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.SubjectBalanceDetailQueryReq;
import com.paob.tms.dto.resp.SubjectBalanceDetailResp;
import com.paob.tms.mapper.SubjectBalanceDetailMapper;
import com.paob.tms.service.SubjectBalanceDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectBalanceDetailServiceImpl implements SubjectBalanceDetailService {

    private final SubjectBalanceDetailMapper subjectBalanceDetailMapper;

    @Override
    public Page<SubjectBalanceDetailResp> querySubjectBalanceDetailPage(SubjectBalanceDetailQueryReq query) {
        // 创建分页对象
        Page<SubjectBalanceDetailResp> page = new Page<>(query.getPageNum(), query.getPageSize());
        
        // 执行分页查询
        return subjectBalanceDetailMapper.selectSubjectBalanceDetailPage(page, query);
    }
} 