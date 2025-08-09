package com.paob.tms.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paob.tms.dto.req.CorporateActionQueryReq;
import com.paob.tms.dto.resp.CorporateActionResp;
import com.paob.tms.enums.*;
import com.paob.tms.mapper.AccrualMapper;
import com.paob.tms.mapper.CorporateActionMapper;
import com.paob.tms.mapper.FpsDwRepoMapper;
import com.paob.tms.mapper.TradeBlotterMapper;
import com.paob.tms.model.Accrual;
import com.paob.tms.model.CorporateAction;
import com.paob.tms.model.FpsDwRepo;
import com.paob.tms.model.TradeBlotter;
import com.paob.tms.service.CorporateActionService;
import com.paob.tms.util.DateUtil;
import com.paob.tms.util.UserUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CorporateActionServiceImpl extends ServiceImpl<CorporateActionMapper, CorporateAction> implements CorporateActionService {

    @Autowired
    private TradeBlotterMapper tradeBlotterMapper;
    
    @Autowired
    private FpsDwRepoMapper fpsDwRepoMapper;

    @Autowired
    private  AccrualMapper accrualMapper;

    @Override
    public IPage<CorporateActionResp> queryList(CorporateActionQueryReq req) {
        Page<CorporateAction> page = new Page<>(req.getPageNum(), req.getPageSize());
        IPage<CorporateAction> corporateActionPage = baseMapper.selectPage(page, req);
        
        return corporateActionPage.convert(this::convertToResp);
    }

    @Override
    public void exportList(CorporateActionQueryReq req, HttpServletResponse response) {
        log.info("导出公司行为列表，查询条件：{}", req);
        
        // 设置响应头
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String fileName = DateUtil.getTimestampFileName("corporate_action", ".xlsx");
        try {
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            log.error("文件名编码失败", e);
        }
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);

        // 查询数据
        List<CorporateAction> corporateActions = baseMapper.selectPage(new Page<>(1, Integer.MAX_VALUE), req).getRecords();
        List<CorporateActionResp> data = corporateActions.stream()
                .map(this::convertToResp)
                .collect(Collectors.toList());

        // 导出Excel
        try {
            EasyExcel.write(response.getOutputStream(), CorporateActionResp.class)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .sheet("公司行为列表")
                    .doWrite(data);
        } catch (IOException e) {
            log.error("导出公司行为列表失败", e);
            throw new RuntimeException("导出公司行为列表失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String batchVoucher(List<String> actionNos) {
        if (actionNos == null || actionNos.isEmpty()) {
            return "请选择要制证的公司行为";
        }

        // 查询所有选中的公司行为
        List<CorporateAction> corporateActions = baseMapper.selectList(
                new LambdaQueryWrapper<CorporateAction>()
                        .in(CorporateAction::getActionNo, actionNos)
        );

        if (corporateActions.isEmpty()) {
            return "未找到选中的公司行为";
        }

        // 检查是否所有选中的公司行为都未制证
        List<String> alreadyVouchered = corporateActions.stream()
                .filter(action -> !"未制证".equals(action.getJournalStatus()))
                .map(CorporateAction::getActionNo)
                .collect(Collectors.toList());

        if (!alreadyVouchered.isEmpty()) {
            return "以下公司行为已制证，请重新选择：" + String.join(", ", alreadyVouchered);
        }

        // 更新制证状态
        corporateActions.forEach(action -> {
            action.setJournalStatus("已制证");
            action.setJournalSequence(generateJournalSequence(action));
        });

        // 批量更新
        updateBatchById(corporateActions);

        return "已完成" + corporateActions.size() + "条公司行为制证";
    }

    @Override
    public CorporateActionResp getDetail(String actionNo) {
        CorporateAction corporateAction = baseMapper.selectOne(
                new LambdaQueryWrapper<CorporateAction>()
                        .eq(CorporateAction::getActionNo, actionNo)
        );
        return corporateAction != null ? convertToResp(corporateAction) : null;
    }

    private CorporateActionResp convertToResp(CorporateAction corporateAction) {
        CorporateActionResp resp = new CorporateActionResp();
        BeanUtils.copyProperties(corporateAction, resp);
        return resp;
    }

    private String generateJournalSequence(CorporateAction action) {
        // TODO: 根据实际业务规则生成凭证编号
        return "VOUCHER_" + System.currentTimeMillis();
    }

    /**
     * 计算公司行为
     */
    public void calculateCorporateAction() {
        // 遍历所有的remarkType交易备注类型
        for (RepoRemarkTypeEnum remarkType : RepoRemarkTypeEnum.values()) {
            log.info("开始计算{}类型的公司行为", remarkType.getDescription());
            calculateCorporateActionByRemarkType(remarkType);
        }
        //计算回购的公司行为
        this.calculateRepoCorporateAction();
    }

    /**
     * 计算面值总和
     * @param trades 交易列表
     * @return 面值总和
     */
    private BigDecimal calculateNetAmount(List<TradeBlotter> trades) {
        // 计算买入和卖出的面值总和
        BigDecimal buyAmount = trades.stream()
                .filter(trade -> TradeTypeEnum.PAOB_BUY.getCode().equals(trade.getBuySellBorrowLend()))
                .map(TradeBlotter::getFaceAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal sellAmount = trades.stream()
                .filter(trade -> TradeTypeEnum.PAOB_SELL.getCode().equals(trade.getBuySellBorrowLend()))
                .map(TradeBlotter::getFaceAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return buyAmount.subtract(sellAmount);
    }

    /**
     * 计算利息总和
     * @param trades 交易列表
     * @return 利息总和
     */
    private BigDecimal calculateInterestAmount(List<TradeBlotter> trades) {
        // 计算买入和卖出的面值总和
        BigDecimal buyAmount = trades.stream()
                .filter(trade -> TradeTypeEnum.PAOB_BUY.getCode().equals(trade.getBuySellBorrowLend()))
                .map(TradeBlotter::getNextInterest)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal sellAmount = trades.stream()
                .filter(trade -> TradeTypeEnum.PAOB_SELL.getCode().equals(trade.getBuySellBorrowLend()))
                .map(TradeBlotter::getNextInterest)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return buyAmount.subtract(sellAmount);
    }

    /**
     * 根据备注类型计算公司行为
     * @param remarkType 备注类型
     */
    private void calculateCorporateActionByRemarkType(RepoRemarkTypeEnum remarkType) {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        
        // 构建查询条件
        LambdaQueryWrapper<TradeBlotter> queryWrapper = buildTradeBlotterQueryWrapper(remarkType, currentDate);

        // 查询指定备注类型的交易数据
        List<TradeBlotter> trades = tradeBlotterMapper.selectList(queryWrapper);

        // 处理交易并生成公司行为
        List<CorporateAction> corporateActions = processTradesSummary(trades, remarkType);

        // 批量保存
        if (!corporateActions.isEmpty()) {
            saveBatch(corporateActions);
        }
        
        log.info("完成计算{}类型的公司行为，共生成{}条记录", remarkType.getDescription(), corporateActions.size());
    }

    /**
     * 计算最大序号
     * @param remarkType 备注类型
     * @return 最大序号
     */
    private Integer calculateMaxSeq(RepoRemarkTypeEnum remarkType) {
        Integer maxSeq = null;
        // 获取最大序号
        CorporateAction maxAction = baseMapper.selectOne(
                new LambdaQueryWrapper<CorporateAction>()
                        .eq(CorporateAction::getRemark, remarkType.getCode())
                        .orderByDesc(CorporateAction::getActionNo)
                        .last("limit 1")
        );
        switch (remarkType) {
            case BOND,BOND1:
                maxSeq = maxAction != null ? Integer.parseInt(maxAction.getActionNo().substring(4)) : null;
                break;
            case EFB,EFB1,UST,UST1,INTERBANK,NTERBANK1,NTERBANK2,NTERBANK3:
                maxSeq = maxAction != null ? Integer.parseInt(maxAction.getActionNo().substring(3)) : null;
                break;
            case CD,CD1,FIXED_DEPOSIT1,FIXED_DEPOSIT:
                maxSeq = maxAction != null ? Integer.parseInt(maxAction.getActionNo().substring(2)) : null;
                break;
            default:
                maxSeq = 0;
                break;
        }
        return (maxSeq == null) ? 0 : maxSeq;
    }

    /**
     * 交易数据,没有进行汇总，包括Interbank，Fixed Deposit
     * @param trades
     * @param remarkType
     * @return
     */
    List<CorporateAction> processTrades(List<TradeBlotter> trades, RepoRemarkTypeEnum remarkType) {
        List<CorporateAction> corporateActions = new ArrayList<>();

        int sequence = this.calculateMaxSeq(remarkType);

        // 处理每个分组
        for (TradeBlotter trade : trades) {
            sequence++;
            String seqStr = String.format("%05d", sequence);

            CorporateAction action = new CorporateAction();
            action.setActionNo(remarkType.getCode() + seqStr);
            action.setRemark(remarkType.getCode());
            action.setCurrency(trade.getCurrency());
            // 收集不重复值
            action.setTradeNumbers(trade.getTradeNumber());
            action.setEntityId(trade.getEntityId());
            action.setCounterpart(trade.getCounterpart());
            action.setValueDate(LocalDate.now());
            action.setDebtSecurityName(trade.getDebtSecurityName());
            action.setSettlementAccount(trade.getSettlementAccount());
            action.setBroker(trade.getBroker());
            action.setPaymentStatus(PaymentStatusEnum.UNPAID.getCode());
            action.setJournalStatus(VoucherStatusEnum.UNVOUCHERED.getCode());

            corporateActions.add(action);
        }
        return corporateActions;
    }

    /**
     * 汇总交易数据，包括Bond, CD, EFB, UST
     * @param trades
     * @param remarkType
     * @return
     */
    List<CorporateAction> processTradesSummary(List<TradeBlotter> trades, RepoRemarkTypeEnum remarkType) {
        // 按entityId、counterpart、currency、isin、debtSecurityName、settlementAccount、broker分组
        Map<String, List<TradeBlotter>> groupedTrades = trades.stream()
                .collect(Collectors.groupingBy(trade ->
                        trade.getCurrency() + "_" + trade.getIsin()));

        List<CorporateAction> corporateActions = new ArrayList<>();

        int sequence = this.calculateMaxSeq(remarkType);

        // 处理每个分组
        for (List<TradeBlotter> tradeList : groupedTrades.values()) {
            BigDecimal netAmount = BigDecimal.ZERO;
            BigDecimal interestAmount = BigDecimal.ZERO;
            // 如果是债券、CD、EFB、UST类型的交易
            if (RepoRemarkTypeEnum.BOND.equals(remarkType) ||
                    RepoRemarkTypeEnum.CD.equals(remarkType) ||
                    RepoRemarkTypeEnum.EFB.equals(remarkType) ||
                    RepoRemarkTypeEnum.UST.equals(remarkType)) {
                // 计算净面值
                netAmount = calculateNetAmount(tradeList);
                // 计算利息金额
                interestAmount = calculateInterestAmount(tradeList);

                // 如果净面值不大于0,跳过当前分组
                if (netAmount.compareTo(BigDecimal.ZERO) <= 0) {
                    continue;
                } else {
                    // 处理交易面值的累加和删除
                    trades = processTradeFaceAmount(trades);
                    // 如果处理后没有交易，跳过当前分组
                    if (trades.isEmpty()) {
                        continue;
                    }
                }
            }
            sequence++;
            String seqStr = String.format("%05d", sequence);

            CorporateAction action = new CorporateAction();
            action.setActionNo(remarkType.getCode() + seqStr);
            action.setRemark(remarkType.getCode());

            // 设置公司行为类型
            setActionTypeByRemarkType(action, remarkType, netAmount, interestAmount);

            // 收集不重复值
            action.setTradeNumbers(tradeList.stream()
                    .map(TradeBlotter::getTradeNumber)
                    .distinct()
                    .collect(Collectors.joining(",")));
            action.setEntityId(tradeList.get(0).getEntityId());
            action.setCounterpart(tradeList.stream()
                    .map(TradeBlotter::getCounterpart)
                    .distinct()
                    .collect(Collectors.joining(",")));
            action.setValueDate(LocalDate.now());
            action.setCurrency(tradeList.get(0).getCurrency());
            action.setDebtSecurityName(tradeList.get(0).getDebtSecurityName());
            action.setSettlementAccount(tradeList.get(0).getSettlementAccount());
            action.setIsin(tradeList.get(0).getIsin());
            action.setBroker(tradeList.get(0).getBroker());
            action.setPaymentStatus(PaymentStatusEnum.UNPAID.getCode());
            action.setJournalStatus(VoucherStatusEnum.UNVOUCHERED.getCode());
            
            // 根据remarkType,currency,isin,valueDate作为条件查询tms_accrual表
            Accrual accrual = accrualMapper.selectByUniqueKey(
                action.getRemark(),
                action.getCurrency(),
                action.getIsin(),
                action.getValueDate()
            );
            
            // 设置相关字段
            if (accrual != null) {
                action.setAllocPremDisc(accrual.getAllocPremDisc());
                action.setAccruedInterest(accrual.getAccruedInterest());
                action.setAccruedValuationChange(accrual.getAccruedValuationChange());
            }

            action.setCreatedBy(UserUtils.getUser().getUmNo());
            action.setUpdatedBy(UserUtils.getUser().getUmNo());
            action.setCreatedTime(LocalDateTime.now());
            action.setUpdatedTime(LocalDateTime.now());

            corporateActions.add(action);
        }
        return corporateActions;
    }

    /**
     * 构建交易查询条件
     * @param remarkType 备注类型
     * @param currentDate 当前日期
     * @return 查询条件
     */
    private LambdaQueryWrapper<TradeBlotter> buildTradeBlotterQueryWrapper(RepoRemarkTypeEnum remarkType, LocalDate currentDate) {
        LambdaQueryWrapper<TradeBlotter> queryWrapper = new LambdaQueryWrapper<TradeBlotter>()
                .eq(TradeBlotter::getRemark, remarkType.getCode());

        // 添加日期相关查询条件
        addDateQueryConditions(queryWrapper, remarkType, currentDate);
        
        // 添加买卖方向查询条件
        addBuySellQueryCondition(queryWrapper, remarkType);

        return queryWrapper;
    }

    /**
     * 添加日期相关查询条件
     * @param queryWrapper 查询条件
     * @param remarkType 备注类型
     * @param currentDate 当前日期
     */
    private void addDateQueryConditions(LambdaQueryWrapper<TradeBlotter> queryWrapper, RepoRemarkTypeEnum remarkType, LocalDate currentDate) {
        // 根据remarkType类型添加日期查询条件
        switch (remarkType) {
            case BOND, EFB, CD, UST, FIXED_DEPOSIT, INTERBANK, NTERBANK2:
                queryWrapper.le(TradeBlotter::getValueDate, currentDate)
                        .eq(TradeBlotter::getNextCouponDate, currentDate)
                        .gt(TradeBlotter::getMaturityDate, currentDate);
                break;
            case BOND1, NTERBANK3, EFB1, CD1, UST1, FIXED_DEPOSIT1, NTERBANK1:
                queryWrapper.eq(TradeBlotter::getMaturityDate, currentDate);
                break;
            default:
                break;
        }
    }

    /**
     * 添加买卖方向查询条件
     * @param queryWrapper 查询条件
     * @param remarkType 备注类型
     */
    private void addBuySellQueryCondition(LambdaQueryWrapper<TradeBlotter> queryWrapper, RepoRemarkTypeEnum remarkType) {
        switch (remarkType) {
            case BOND, BOND1, EFB, EFB1, CD, CD1, UST, UST1:
                queryWrapper.in(TradeBlotter::getBuySellBorrowLend,
                        Arrays.asList(TradeTypeEnum.PAOB_BUY.getCode(), TradeTypeEnum.PAOB_SELL.getCode()));
                break;
            case FIXED_DEPOSIT, FIXED_DEPOSIT1, INTERBANK, NTERBANK1:
                queryWrapper.in(TradeBlotter::getBuySellBorrowLend,
                        Arrays.asList(TradeTypeEnum.PAOB_LEND.getCode()));
                break;
            case NTERBANK2, NTERBANK3:
                queryWrapper.in(TradeBlotter::getBuySellBorrowLend,
                        Arrays.asList(TradeTypeEnum.PAOB_BORROW.getCode()));
                break;
            default:
                break;
        }
    }

    /**
     * 根据备注类型设置公司行为类型和结算金额
     * @param action 公司行为对象
     * @param remarkType 备注类型
     * @param netAmount 净面值
     * @param interestAmount 利息金额
     */
    private void setActionTypeByRemarkType(CorporateAction action, RepoRemarkTypeEnum remarkType,BigDecimal netAmount, BigDecimal interestAmount) {
        action.setInterestAmount(interestAmount);
        action.setPrincipalAmount(netAmount);
        action.setTotalAmount(netAmount.add(interestAmount));
        switch (remarkType) {
            case BOND, EFB, CD,UST, FIXED_DEPOSIT, INTERBANK:
                action.setActionType(ActionTypeEnum.RECEIVE_INTEREST.getCode());
                action.setSettlementAmount(interestAmount);
                break;
            case EFB1,BOND1,CD1,UST1,FIXED_DEPOSIT1,NTERBANK1,NTERBANK3:
                action.setActionType(ActionTypeEnum.RECEIVE_PRINCIPAL_AND_INTEREST.getCode());
                action.setSettlementAmount(interestAmount.add(netAmount));
                break;
            case NTERBANK2:
                action.setActionType(ActionTypeEnum.PAY_INTEREST.getCode());
                action.setSettlementAmount(interestAmount);
                break;
            default:
                break;
        }
    }

    /**
     * 计算回购类型的公司行为
     */
    public void calculateRepoCorporateAction() {
        log.info("开始计算回购类型的公司行为");
        
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        
        // 查询回购日期等于当前日期的REPO交易
        List<FpsDwRepo> repoList = fpsDwRepoMapper.selectList(
                new LambdaQueryWrapper<FpsDwRepo>()
                        .eq(FpsDwRepo::getRepurchaseDate, currentDate)
        );
        
        if (repoList.isEmpty()) {
            log.info("没有找到回购日期为{}的REPO交易", currentDate);
            return;
        }
        
        // 获取最大序号
        CorporateAction maxAction = baseMapper.selectOne(
                new LambdaQueryWrapper<CorporateAction>()
                        .eq(CorporateAction::getRemark, "REPO")
                        .orderByDesc(CorporateAction::getActionNo)
                        .last("limit 1")
        );
        Integer maxSeq = maxAction != null ? Integer.parseInt(maxAction.getActionNo().substring(4)) : null;
        int sequence = (maxSeq == null) ? 0 : maxSeq;
        
        List<CorporateAction> corporateActions = new ArrayList<>();
        
        // 处理每个REPO交易
        for (FpsDwRepo repo : repoList) {
            sequence++;
            String seqStr = String.format("%05d", sequence);
            
            CorporateAction action = new CorporateAction();
            action.setActionNo("REPO" + seqStr);
            action.setTradeNumbers(repo.getTradeNumber());
            action.setEntityId(null);
            action.setCounterpart(repo.getCounterpart());
            action.setValueDate(currentDate);
            action.setDebtSecurityName(null);
            action.setSettlementAccount("");
            action.setCurrency(repo.getCurrency());
            action.setBroker(null);
            action.setPaymentStatus(PaymentStatusEnum.UNPAID.getCode());
            action.setJournalStatus(VoucherStatusEnum.UNVOUCHERED.getCode());
            action.setActionType(ActionTypeEnum.PAY_PRINCIPAL_AND_INTEREST.getCode());
            BigDecimal amount = repo.getAmount() == null ? BigDecimal.ZERO : repo.getAmount();
            BigDecimal interest = repo.getInterest() == null ? BigDecimal.ZERO : repo.getInterest();
            action.setSettlementAmount(amount.add(interest));
            action.setInterestAmount(interest);
            action.setPrincipalAmount(amount);
            action.setTotalAmount(amount.add(interest));
            action.setRemark("REPO");
            action.setCurrency(repo.getCurrency());
            action.setSettlementAmount(repo.getAmount().add(repo.getInterest()));
            
            corporateActions.add(action);
        }
        
        // 批量保存
        if (!corporateActions.isEmpty()) {
            saveBatch(corporateActions);
            log.info("完成计算回购类型的公司行为，共生成{}条记录", corporateActions.size());
        }
    }

    /**
     * 处理交易面值的累加和删除
     * @param trades 交易列表
     * @return 处理后的交易列表
     */
    private List<TradeBlotter> processTradeFaceAmount(List<TradeBlotter> trades) {
        List<TradeBlotter> processedTrades = new ArrayList<>(trades);
        List<TradeBlotter> toRemove = new ArrayList<>();
        BigDecimal sum = BigDecimal.ZERO;

        for (TradeBlotter trade : trades) {
            // 根据买卖方向确定面值的正负
            BigDecimal faceAmount = TradeTypeEnum.PAOB_BUY.getCode().equals(trade.getBuySellBorrowLend())
                    ? trade.getFaceAmount()
                    : trade.getFaceAmount().negate();

            sum = sum.add(faceAmount);
            toRemove.add(trade);

            // 如果累加和为0，则删除已处理的交易
            if (sum.compareTo(BigDecimal.ZERO) == 0) {
                processedTrades.removeAll(toRemove);
                toRemove.clear();
                sum = BigDecimal.ZERO;
            }
        }

        return processedTrades;
    }
} 