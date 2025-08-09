package com.paob.tms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.paob.tms.dto.req.*;
import com.paob.tms.dto.resp.JournalBatchCreateResp;
import com.paob.tms.dto.resp.JournalDetailResp;
import com.paob.tms.dto.resp.JournalResp;
import com.paob.tms.dto.resp.JournalTemplateResp;
import com.paob.tms.model.TmsCitJournal;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface JournalService extends IService<TmsCitJournal> {
    
    /**
     * 查询凭证列表
     *
     * @param req 查询条件
     * @return 凭证列表
     */
    IPage<JournalResp> queryJournalList(JournalQueryReq req);

    /**
     * 导出凭证列表
     *
     * @param req 查询条件
     * @param response HTTP响应
     */
    void exportJournalList(JournalQueryReq req, HttpServletResponse response);

    /**
     * 复核通过
     *
     * @param req 复核请求
     */
    void approveJournal(JournalApproveReq req);

    /**
     * 复核不通过
     *
     * @param req 复核不通过请求
     */
    void unApproveJournal(JournalUnApproveReq req);

    /**
     * 批量复核拒绝
     *
     * @param req 批量复核拒绝请求
     * @return 处理结果消息
     */
    String batchRejectJournal(JournalBatchRejectReq req);

    /**
     * 批量复核通过
     *
     * @param req 批量复核通过请求
     * @return 处理结果消息
     */
    String batchApproveJournal(JournalBatchApproveReq req);

    /**
     * 下载凭证模板
     *
     * @param response HTTP响应
     */
    void downloadTemplate(HttpServletResponse response);

    /**
     * 查询凭证详情
     * @param journalSequence 凭证编号
     * @return 凭证详情列表
     */
    List<JournalDetailResp> getJournalDetail(String journalSequence);

    /**
     * 编辑凭证
     * @param list 编辑请求
     */
    void updateJournal(List<JournalDetailResp> list);

    /**
     * 导入凭证
     * @param file Excel文件
     * @return 导入结果消息
     */
    String importJournal(MultipartFile file);

    /**
     * 查询模板列表
     * @return 模板列表
     */
    List<JournalTemplateResp> queryTemplateList();

    /**
     * 批量制证
     *
     * @param req 批量制证请求
     * @return 批量制证响应
     */
    JournalBatchCreateResp batchCreateJournal(JournalBatchCreateReq req);

    /**
     * 制证初始化
     * 根据传入的业务ID列表，生成对应的凭证记录（不保存到数据库）
     *
     * @param req 制证初始化请求
     * @return 初始化后的凭证记录列表
     */
    List<JournalDetailResp> initJournal(JournalInitReq req);

    /**
     * 自动制证
     */
    void autoGenerate();
} 