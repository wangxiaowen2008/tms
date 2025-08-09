package com.paob.tms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.req.TradeBlotterQueryReq;
import com.paob.tms.model.TradeBlotter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 交易流水Mapper接口
 */
@Mapper
public interface TradeBlotterMapper extends BaseMapper<TradeBlotter> {
    /**
     * 根据条件查询交易流水
     *
     * @param page 分页对象
     * @param condition 查询条件
     * @return 交易流水分页对象
     */
    IPage<TradeBlotter> selectByCondition(IPage<TradeBlotter> page, @Param("condition") TradeBlotterQueryReq condition);
    
    /**
     * 根据交易编号查询交易流水
     *
     * @param tradeNumber 交易编号
     * @return 交易流水信息
     */
    TradeBlotter selectByTradeNumber(@Param("tradeNumber") String tradeNumber);
    
    List<TradeBlotter> selectUnconfirmedTrades();
    
    int insert(TradeBlotter tradeBlotter);
    
    int update(TradeBlotter tradeBlotter);
    
    int deleteByTradeNumber(@Param("tradeNumber") String tradeNumber);
    
    /**
     * 根据条件查询交易流水数量
     *
     * @param req 查询条件
     * @return 交易流水数量
     */
    int countByCondition(@Param("condition") TradeBlotterQueryReq condition);

    /**
     * 查询所有不重复的ISIN
     * @return ISIN列表
     */
    List<String> selectAllIsin();
} 