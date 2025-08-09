package com.paob.tms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paob.tms.model.RiskMonitoring;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface RiskMonitoringMapper extends BaseMapper<RiskMonitoring> {
    
    /**
     * 批量插入风险监控数据
     */
    int batchInsert(@Param("list") List<RiskMonitoring> list);
    
    /**
     * 根据ISIN和报告日期查询
     */
    RiskMonitoring selectByIsinAndReportingDate(@Param("isin") String isin, @Param("reportingDate") LocalDate reportingDate);
    
    /**
     * 查询交易数据中存在但风险监控数据中不存在的ISIN
     */
    List<String> selectMissingIsins(@Param("reportingDate") LocalDate reportingDate);
    
    /**
     * 查询风险监控数据中存在但交易数据中不存在的ISIN
     */
    List<String> selectExtraIsins(@Param("reportingDate") LocalDate reportingDate);
} 