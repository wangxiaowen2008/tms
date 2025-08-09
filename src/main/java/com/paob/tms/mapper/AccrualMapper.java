package com.paob.tms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.AccrualQueryReq;
import com.paob.tms.model.Accrual;
import com.paob.tms.model.TradeBlotter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 计提数据Mapper接口
 */
@Mapper
public interface AccrualMapper extends BaseMapper<Accrual> {
    
    /**
     * 分页查询计提数据
     *
     * @param page 分页参数
     * @param req 查询条件
     * @return 分页结果
     */
    IPage<Accrual> selectPage(Page<Accrual> page, @Param("req") AccrualQueryReq req);
    
    /**
     * 查询符合条件的交易数据
     *
     * @param queryWrapper 查询条件
     * @return 交易数据列表
     */
    List<TradeBlotter> selectTradeBlotters(@Param("ew") LambdaQueryWrapper<TradeBlotter> queryWrapper);
    
    /**
     * 批量插入计提数据
     *
     * @param accruals 计提数据列表
     * @return 插入成功的记录数
     */
    int batchInsert(@Param("list") List<Accrual> accruals);
    
    /**
     * 根据条件查询计提数据
     *
     * @param remark 备注类型
     * @param currency 币种
     * @param isin ISIN代码
     * @param accrualDate 计提日期
     * @return 计提数据
     */
    Accrual selectByUniqueKey(@Param("remark") String remark, 
                            @Param("currency") String currency, 
                            @Param("isin") String isin, 
                            @Param("accrualDate") LocalDate accrualDate);
    
    /**
     * 查询市场价值
     *
     * @param isin ISIN代码
     * @param currency 币种
     * @param date 日期
     * @return 市场价值
     */
    BigDecimal selectMarketValue(@Param("remark") String remark,
                                 @Param("isin") String isin,
                               @Param("currency") String currency, 
                               @Param("date") LocalDate date);
    
    /**
     * 查询上一天已计提估值变动值
     *
     * @param remark 备注类型
     * @param currency 币种
     * @param isin ISIN代码
     * @param accrualDate 计提日期
     * @return 上一天已计提估值变动值
     */
    BigDecimal selectPreviousAccruedValuationChange(@Param("remark") String remark,
                                                  @Param("currency") String currency,
                                                  @Param("isin") String isin,
                                                  @Param("accrualDate") LocalDate accrualDate);
    
    /**
     * 查询上一天已计利息
     *
     * @param remark 备注类型
     * @param currency 币种
     * @param isin ISIN代码
     * @param accrualDate 计提日期
     * @return 上一天已计利息
     */
    BigDecimal selectPreviousAccruedInterest(@Param("remark") String remark,
                                          @Param("currency") String currency,
                                          @Param("isin") String isin,
                                          @Param("accrualDate") LocalDate accrualDate);
    
    /**
     * 查询上一天已分摊溢价
     *
     * @param remark 备注类型
     * @param currency 币种
     * @param isin ISIN代码
     * @param accrualDate 计提日期
     * @return 上一天已分摊溢价
     */
    BigDecimal selectPreviousAllocPremDisc(@Param("remark") String remark,
                                         @Param("currency") String currency,
                                         @Param("isin") String isin,
                                         @Param("accrualDate") LocalDate accrualDate);
} 