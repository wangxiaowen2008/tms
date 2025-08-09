package com.paob.tms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.paob.tms.dto.req.JournalTemplateAddReq;
import com.paob.tms.dto.req.JournalTemplateQueryReq;
import com.paob.tms.dto.resp.JournalTemplateResp;
import com.paob.tms.model.TmsCitJournalTemp;
import com.paob.tms.model.TmsCitJournal;

import java.util.List;

public interface JournalTemplateService {
    
    /**
     * 查询凭证模板列表
     *
     * @param req 查询请求
     * @return 分页结果
     */
    IPage<JournalTemplateResp> queryJournalTemplateList(JournalTemplateQueryReq req);

    /**
     * 新增凭证模板
     * @param req 新增请求
     */
    void addJournalTemplate(JournalTemplateAddReq req);

    /**
     * 编辑凭证模板
     * @param req 编辑请求
     */
    void updateJournalTemplate(JournalTemplateAddReq req);

    /**
     * 获取凭证模板详情
     * @param tempName 模板名称
     * @return 凭证模板详情
     */
    List<JournalTemplateResp> getJournalTemplateDetail(String tempName);

    /**
     * 提交凭证模板
     * @param tempName 模板名称
     */
    void submitJournalTemplate(String tempName);

    /**
     * 复核凭证模板
     * @param tempName 模板名称
     */
    void approveJournalTemplate(String tempName);

    /**
     * 复核拒绝凭证模板
     * @param tempName 模板名称
     */
    void rejectJournalTemplate(String tempName);

    /**
     * 获取所有复合通过状态的模板名称列表
     * @return 模板名称列表
     */
    List<String> getApprovedTemplateNames();

} 