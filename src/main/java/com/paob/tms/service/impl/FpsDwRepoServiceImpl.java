package com.paob.tms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paob.tms.dto.req.FpsDwRepoQueryReq;
import com.paob.tms.dto.req.FpsDwRepoMakeVoucherReq;
import com.paob.tms.dto.resp.FpsDwRepoResp;
import com.paob.tms.mapper.FpsDwRepoMapper;
import com.paob.tms.model.FpsDwRepo;
import com.paob.tms.service.FpsDwRepoService;
import com.paob.tms.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REPO交易服务实现类
 */
@Slf4j
@Service
public class FpsDwRepoServiceImpl extends ServiceImpl<FpsDwRepoMapper, FpsDwRepo> implements FpsDwRepoService {

    @Override
    public IPage<FpsDwRepoResp> queryList(FpsDwRepoQueryReq condition) {
        log.info("查询REPO交易列表，查询条件：{}", condition);
        Page<FpsDwRepo> page = new Page<>(condition.getPageNum(), condition.getPageSize());
        IPage<FpsDwRepo> repoPage = baseMapper.selectByCondition(page, condition);
        
        // 转换为响应对象
        return repoPage.convert(this::convertToResp);
    }

    @Override
    public FpsDwRepoResp getDetail(String tradeNumber) {
        log.info("获取REPO交易详情，交易编号：{}", tradeNumber);
        FpsDwRepo repo = baseMapper.selectByTradeNumber(tradeNumber);
        return repo != null ? convertToResp(repo) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean makeVoucher(FpsDwRepoMakeVoucherReq req) {
        log.info("批量制证，交易编号列表：{}", req.getTradeNumbers());
        String currentUser = SecurityUtil.getCurrentUsername();
        return baseMapper.batchUpdateJournalStatus(req.getTradeNumbers(), "待复核", currentUser) > 0;
    }

    @Override
    public List<FpsDwRepoResp> export(FpsDwRepoQueryReq condition) {
        log.info("导出REPO交易数据，查询条件：{}", condition);
        List<FpsDwRepo> repoList = baseMapper.selectByCondition(new Page<>(1, Integer.MAX_VALUE), condition).getRecords();
        return repoList.stream().map(this::convertToResp).collect(Collectors.toList());
    }

    /**
     * 将实体对象转换为响应对象
     */
    private FpsDwRepoResp convertToResp(FpsDwRepo repo) {
        if (repo == null) {
            return null;
        }
        FpsDwRepoResp resp = new FpsDwRepoResp();
        BeanUtils.copyProperties(repo, resp);
        return resp;
    }
} 