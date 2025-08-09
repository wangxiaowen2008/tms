package com.paob.tms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paob.tms.model.TmsVoucherDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TmsVoucherDetailMapper extends BaseMapper<TmsVoucherDetail> {
    // 可以添加自定义的查询方法
} 