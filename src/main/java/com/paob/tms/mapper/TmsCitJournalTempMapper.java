package com.paob.tms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.paob.tms.dto.resp.JournalTemplateResp;
import com.paob.tms.model.TmsCitJournalTemp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TmsCitJournalTempMapper extends BaseMapper<TmsCitJournalTemp> {
    
    /**
     * 查询凭证模板列表
     */
    IPage<TmsCitJournalTemp> selectTemplateList(IPage<TmsCitJournalTemp> page,
                                               @Param("remark") String remark,
                                               @Param("templateType") String tempType,
                                               @Param("flowType") String flowType,
                                               @Param("templateName") String templateName);

    /**
     * 查询凭证模板总数
     */
    long selectTemplateCount(@Param("remark") String remark,
                            @Param("templateType") String templateType,
                            @Param("flowType") String flowType,
                            @Param("templateName") String templateName);

    /**
     * 获取当前最大的模板编号
     * @return 最大模板编号，如果没有记录则返回0
     */
    @Select("SELECT COALESCE(MAX(temp_no), 0) FROM tms_cit_journal_temp")
    Long selectMaxTempNo();

    /**
     * 查询模板列表
     * @return 模板列表
     */
    List<JournalTemplateResp> selectTemplateList();

    /**
     * 查询复核通过状态的模板名称列表
     * @return 模板名称列表
     */
    List<String> selectApprovedTemplateNames();
} 