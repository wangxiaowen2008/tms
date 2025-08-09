package com.paob.tms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paob.tms.dto.req.JournalTemplateAddReq;
import com.paob.tms.dto.req.JournalTemplateQueryReq;
import com.paob.tms.dto.resp.JournalTemplateDetailResp;
import com.paob.tms.dto.resp.JournalTemplateResp;
import com.paob.tms.enums.DataStatusEnum;
import com.paob.tms.mapper.TmsCitJournalTempMapper;
import com.paob.tms.model.TmsCitJournalTemp;
import com.paob.tms.service.JournalTemplateService;
import com.paob.tms.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JournalTemplateServiceImpl extends ServiceImpl<TmsCitJournalTempMapper,TmsCitJournalTemp> implements JournalTemplateService {

    @Autowired
    private TmsCitJournalTempMapper tmsCitJournalTempMapper;

    @Override
    public IPage<JournalTemplateResp> queryJournalTemplateList(JournalTemplateQueryReq req) {
        // 创建分页对象
        Page<TmsCitJournalTemp> page = new Page<>(req.getPageNum(), req.getPageSize());
        
        // 调用自定义的Mapper方法进行分页查询
        IPage<TmsCitJournalTemp> resultPage = tmsCitJournalTempMapper.selectTemplateList(
            page,
            req.getRemark(),
            req.getTempType(),
            req.getFlowType(),
            req.getTemplateName()
        );

        // 转换为响应对象
        IPage<JournalTemplateResp> respPage = new Page<>();
        BeanUtils.copyProperties(resultPage, respPage, "records");

        // 按模板名称分组
        Map<String, List<TmsCitJournalTemp>> groupByTempName = resultPage.getRecords().stream()
                .collect(Collectors.groupingBy(TmsCitJournalTemp::getTempName));

        // 转换为响应对象列表
        List<JournalTemplateResp> respList = new ArrayList<>();
        groupByTempName.forEach((tempName, tempList) -> {
            JournalTemplateResp resp = new JournalTemplateResp();
            resp.setTempName(tempName);
            resp.setBooksNo(tempList.get(0).getBooksNo());
            resp.setSegment1(tempList.get(0).getSegment1());
            resp.setRemark(tempList.get(0).getRemark());
            resp.setTempType(tempList.get(0).getTempType());
            resp.setStreamType(tempList.get(0).getStreamType());
            resp.setUpdatedBy(tempList.get(0).getUpdatedBy());
            resp.setUpdatedDate(tempList.get(0).getUpdatedTime());

            //根据模板名称获取模板详情
            List<TmsCitJournalTemp> templateDetails = tmsCitJournalTempMapper.selectList(
                new LambdaQueryWrapper<TmsCitJournalTemp>()
                    .eq(TmsCitJournalTemp::getTempName, tempName)
            );

            // 设置明细列表
            List<JournalTemplateDetailResp> detailList = templateDetails.stream().map(temp -> {
                JournalTemplateDetailResp detail = new JournalTemplateDetailResp();
                BeanUtils.copyProperties(temp, detail);
                return detail;
            }).collect(Collectors.toList());
            resp.setSubList(detailList);

            respList.add(resp);
        });

        respPage.setRecords(respList);
        return respPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addJournalTemplate(JournalTemplateAddReq req) {
        if (req.getJournalTempList() == null || req.getJournalTempList().isEmpty()) {
            throw new IllegalArgumentException("凭证模板列表不能为空");
        }
        // 校验模板名称是否重复
        for (JournalTemplateAddReq.JournalTemp temp : req.getJournalTempList()) {
            checkTemplateNameDuplicate(temp.getTempName());
        }
        
        //获取当前表里最大的模板编号
        Long maxTempNo = tmsCitJournalTempMapper.selectMaxTempNo();
        
        // 保存凭证模板信息
        List<TmsCitJournalTemp> templates = new ArrayList<>();
        for (JournalTemplateAddReq.JournalTemp temp : req.getJournalTempList()) {
            TmsCitJournalTemp template = new TmsCitJournalTemp();
            BeanUtils.copyProperties(temp, template);
            
            // 设置默认值
            template.setDataState(DataStatusEnum.INITIAL.getCode());
            template.setCreatedBy(UserUtils.getUser().getUmNo());
            template.setUpdatedBy(UserUtils.getUser().getUmNo());
            template.setCreatedTime(LocalDateTime.now());
            template.setUpdatedTime(LocalDateTime.now());
            
            // 设置模板编号
            if (template.getTempNo() == null) {
                template.setTempNo(maxTempNo + 1);
            }
            
            // 设置UUID
            template.setTmsCitJournalTempId(UUID.randomUUID().toString());
            
            // 设置报告币金额
            template.setPEnteredDr(BigDecimal.ZERO);
            template.setPEnteredCr(BigDecimal.ZERO);
            
            templates.add(template);
        }

        // 批量保存
        saveBatch(templates);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJournalTemplate(JournalTemplateAddReq req) {
        if (req.getJournalTempList() == null || req.getJournalTempList().isEmpty()) {
            throw new IllegalArgumentException("凭证模板列表不能为空");
        }

        // 保存凭证模板信息
        List<TmsCitJournalTemp> templates = new ArrayList<>();
        for (JournalTemplateAddReq.JournalTemp temp : req.getJournalTempList()) {
            // 根据模板ID查询原记录
            TmsCitJournalTemp oldTemplate = getOne(new LambdaQueryWrapper<TmsCitJournalTemp>()
                    .eq(TmsCitJournalTemp::getTmsCitJournalTempId, temp.getTmsCitJournalTempId()));
            
            if (oldTemplate == null) {
                throw new IllegalArgumentException("模板编号[" + temp.getTempNo() + "]不存在");
            }

            TmsCitJournalTemp template = new TmsCitJournalTemp();
            BeanUtils.copyProperties(temp, template);
            
            // 保留原有ID
            template.setTmsCitJournalTempId(oldTemplate.getTmsCitJournalTempId());
            
            //如果状态为复核通过或复核拒绝，更新信息后，状态为已提交，或者是初始状态
            if(DataStatusEnum.REVIEW_PASSED.getCode().equals(oldTemplate.getDataState())
                    || DataStatusEnum.PENDING_REVIEW.getCode().equals(oldTemplate.getDataState())){
                template.setDataState(DataStatusEnum.SUBMITTED.getCode());
            }else{
                template.setDataState(oldTemplate.getDataState());
            }
            // 设置默认值
            template.setCreatedBy(oldTemplate.getCreatedBy());
            template.setCreatedTime(oldTemplate.getCreatedTime());
            template.setUpdatedBy(UserUtils.getUser().getUmNo());
            template.setUpdatedTime(LocalDateTime.now());
            
            // 设置报告币金额
            template.setPEnteredDr(BigDecimal.ZERO);
            template.setPEnteredCr(BigDecimal.ZERO);
            
            templates.add(template);
        }

        // 批量更新
        updateBatchById(templates);
    }

    /**
     * 校验模板名称是否重复
     * @param tempName 模板名称
     */
    private void checkTemplateNameDuplicate(String tempName) {
        long count = count(new LambdaQueryWrapper<TmsCitJournalTemp>()
                .eq(TmsCitJournalTemp::getTempName, tempName));
        if (count > 0) {
            throw new IllegalArgumentException("模板名称[" + tempName + "]已存在");
        }
    }

    @Override
    public List<JournalTemplateResp> getJournalTemplateDetail(String tempName) {
        // 根据模板名称查询模板列表
        List<TmsCitJournalTemp> templateList = tmsCitJournalTempMapper.selectList(
            new LambdaQueryWrapper<TmsCitJournalTemp>()
                .eq(TmsCitJournalTemp::getTempName, tempName)
        );

        if (templateList.isEmpty()) {
            throw new IllegalArgumentException("模板名称[" + tempName + "]不存在");
        }

        // 按模板编号分组
        Map<Long, List<TmsCitJournalTemp>> groupByTempNo = templateList.stream()
                .collect(Collectors.groupingBy(TmsCitJournalTemp::getTempNo));

        // 转换为响应对象列表
        List<JournalTemplateResp> respList = new ArrayList<>();
        groupByTempNo.forEach((tempNo, tempList) -> {
            JournalTemplateResp resp = new JournalTemplateResp();
            resp.setTempName(tempList.get(0).getTempName());
            resp.setTempNo(tempNo);
            resp.setBooksNo(tempList.get(0).getBooksNo());
            resp.setSegment1(tempList.get(0).getSegment1());
            resp.setRemark(tempList.get(0).getRemark());
            resp.setTempType(tempList.get(0).getTempType());
            resp.setStreamType(tempList.get(0).getStreamType());
            resp.setUpdatedBy(tempList.get(0).getUpdatedBy());
            resp.setUpdatedDate(tempList.get(0).getUpdatedTime());

            // 设置明细列表
            List<JournalTemplateDetailResp> detailList = tempList.stream().map(temp -> {
                JournalTemplateDetailResp detail = new JournalTemplateDetailResp();
                BeanUtils.copyProperties(temp, detail);
                return detail;
            }).collect(Collectors.toList());
            resp.setSubList(detailList);

            respList.add(resp);
        });

        return respList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitJournalTemplate(String tempName) {
        // 根据模板名称查询模板列表
        List<TmsCitJournalTemp> templateList = tmsCitJournalTempMapper.selectList(
            new LambdaQueryWrapper<TmsCitJournalTemp>()
                .eq(TmsCitJournalTemp::getTempName, tempName)
        );

        if (templateList.isEmpty()) {
            throw new IllegalArgumentException("模板名称[" + tempName + "]不存在");
        }

        // 校验所有记录的状态是否为"未提交"
        for (TmsCitJournalTemp template : templateList) {
            if (!DataStatusEnum.INITIAL.getCode().equals(template.getDataState())) {
                throw new IllegalArgumentException("模板名称[" + tempName + "]状态不是初始状态，不能提交");
            }
        }

        // 更新所有记录的状态为""
        List<TmsCitJournalTemp> updateList = templateList.stream().map(template -> {
            TmsCitJournalTemp updateTemplate = new TmsCitJournalTemp();
            updateTemplate.setTmsCitJournalTempId(template.getTmsCitJournalTempId());
            updateTemplate.setDataState(DataStatusEnum.SUBMITTED.getCode());
            updateTemplate.setUpdatedBy(UserUtils.getUser().getUmNo());
            updateTemplate.setUpdatedTime(LocalDateTime.now());
            return updateTemplate;
        }).collect(Collectors.toList());

        // 批量更新
        updateBatchById(updateList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveJournalTemplate(String tempName) {
        // 根据模板名称查询模板列表
        List<TmsCitJournalTemp> templateList = tmsCitJournalTempMapper.selectList(
            new LambdaQueryWrapper<TmsCitJournalTemp>()
                .eq(TmsCitJournalTemp::getTempName, tempName)
        );

        if (templateList.isEmpty()) {
            throw new IllegalArgumentException("模板名称[" + tempName + "]不存在");
        }

        // 校验所有记录的状态是否为"待复核"
        for (TmsCitJournalTemp template : templateList) {
            if (!DataStatusEnum.SUBMITTED.getCode().equals(template.getDataState())) {
                throw new IllegalArgumentException("模板名称[" + tempName + "]状态不是待复核，不能复核");
            }
        }

        //不能复核自己创建的模板
        if (UserUtils.getUser().getUmNo().equals(templateList.get(0).getCreatedBy())) {
            throw new IllegalArgumentException("不能复核自己创建的模板");
        }
        
        // 更新所有记录的状态
        List<TmsCitJournalTemp> updateList = templateList.stream().map(template -> {
            TmsCitJournalTemp updateTemplate = new TmsCitJournalTemp();
            updateTemplate.setTmsCitJournalTempId(template.getTmsCitJournalTempId());
            updateTemplate.setDataState(DataStatusEnum.PENDING_REVIEW.getCode());
            // TODO: 触发对trade blotter，公司行为，计提数据的匹配进行制证
            updateTemplate.setUpdatedBy(UserUtils.getUser().getUmNo());
            updateTemplate.setUpdatedTime(LocalDateTime.now());
            return updateTemplate;
        }).collect(Collectors.toList());

        // 批量更新
        updateBatchById(updateList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rejectJournalTemplate(String tempName) {
        // 根据模板名称查询模板列表
        List<TmsCitJournalTemp> templateList = tmsCitJournalTempMapper.selectList(
            new LambdaQueryWrapper<TmsCitJournalTemp>()
                .eq(TmsCitJournalTemp::getTempName, tempName)
        );

        if (templateList.isEmpty()) {
            throw new IllegalArgumentException("模板名称[" + tempName + "]不存在");
        }

        // 校验所有记录的状态是否为"待复核"
        for (TmsCitJournalTemp template : templateList) {
            if (!DataStatusEnum.SUBMITTED.getCode().equals(template.getDataState())) {
                throw new IllegalArgumentException("模板名称[" + tempName + "]状态不是待复核，不能拒绝");
            }
        }

        // 更新所有记录的状态为"已拒绝"
        List<TmsCitJournalTemp> updateList = templateList.stream().map(template -> {
            TmsCitJournalTemp updateTemplate = new TmsCitJournalTemp();
            updateTemplate.setTmsCitJournalTempId(template.getTmsCitJournalTempId());
            updateTemplate.setDataState(DataStatusEnum.REVIEW_PASSED.getCode());
            updateTemplate.setUpdatedBy(UserUtils.getUser().getUmNo());
            updateTemplate.setUpdatedTime(LocalDateTime.now());
            return updateTemplate;
        }).collect(Collectors.toList());

        // 批量更新
        updateBatchById(updateList);
    }

    @Override
    public List<String> getApprovedTemplateNames() {
        return tmsCitJournalTempMapper.selectApprovedTemplateNames();
    }
} 