package com.paob.tms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.FpsDwRepoQueryReq;
import com.paob.tms.model.FpsDwRepo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * REPO交易Mapper接口
 */
@Mapper
public interface FpsDwRepoMapper extends BaseMapper<FpsDwRepo> {
    
    /**
     * 根据条件查询REPO交易
     *
     * @param page 分页对象
     * @param condition 查询条件
     * @return REPO交易分页对象
     */
    IPage<FpsDwRepo> selectByCondition(IPage<FpsDwRepo> page, @Param("condition") FpsDwRepoQueryReq condition);
    
    /**
     * 根据交易编号查询REPO交易
     *
     * @param tradeNumber 交易编号
     * @return REPO交易信息
     */
    FpsDwRepo selectByTradeNumber(@Param("tradeNumber") String tradeNumber);
    
    /**
     * 批量更新制证状态
     *
     * @param tradeNumbers 交易编号列表
     * @param journalStatus 制证状态
     * @param updatedBy 更新人
     * @return 更新记录数
     */
    int batchUpdateJournalStatus(@Param("tradeNumbers") List<String> tradeNumbers,
                               @Param("journalStatus") String journalStatus,
                               @Param("updatedBy") String updatedBy);
} 