package com.paob.tms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.BankAccountBalanceQueryReq;
import com.paob.tms.dto.resp.BankAccountBalanceResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BankAccountBalanceMapper extends BaseMapper<BankAccountBalanceResp> {
    
    /**
     * 分页查询银行账号余额对账列表
     *
     * @param page 分页参数
     * @param query 查询条件
     * @return 分页结果
     */
    Page<BankAccountBalanceResp> selectBankAccountBalancePage(Page<BankAccountBalanceResp> page, @Param("query") BankAccountBalanceQueryReq query);
} 