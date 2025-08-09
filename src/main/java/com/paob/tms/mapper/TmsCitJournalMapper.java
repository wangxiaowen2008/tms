package com.paob.tms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.JournalQueryReq;
import com.paob.tms.model.TmsCitJournal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TmsCitJournalMapper extends BaseMapper<TmsCitJournal> {
    
    /**
     * 查询凭证列表
     *
     * @param page 分页参数
     * @param req 查询条件
     * @return 凭证列表
     */
    IPage<TmsCitJournal> selectJournalList(Page<TmsCitJournal> page, @Param("req") JournalQueryReq req);

    /**
     * 查询凭证详情列表
     *
     * @param journalSequence 凭证编号
     * @return 凭证详情列表
     */
    List<TmsCitJournal> selectJournalDetailList(@Param("journalSequence") String journalSequence);

    /**
     * 查询导出数据
     *
     * @param req 查询条件
     * @return 导出数据列表
     */
    List<TmsCitJournal> selectJournalListForExport(@Param("req") JournalQueryReq req);
} 