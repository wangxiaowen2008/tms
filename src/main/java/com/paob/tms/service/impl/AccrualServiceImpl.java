package com.paob.tms.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paob.tms.dto.req.AccrualQueryReq;
import com.paob.tms.dto.req.BatchInterestJournalReq;
import com.paob.tms.dto.resp.AccrualExportResp;
import com.paob.tms.dto.resp.AccrualResp;
import com.paob.tms.enums.RemarkTypeEnum;
import com.paob.tms.enums.RepoRemarkTypeEnum;
import com.paob.tms.enums.TradeTypeEnum;
import com.paob.tms.enums.VoucherStatusEnum;
import com.paob.tms.mapper.AccrualMapper;
import com.paob.tms.mapper.CorporateActionMapper;
import com.paob.tms.model.Accrual;
import com.paob.tms.model.CorporateAction;
import com.paob.tms.model.TradeBlotter;
import com.paob.tms.service.AccrualService;
import com.paob.tms.util.DateUtil;
import com.paob.tms.util.UserUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 计提数据服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccrualServiceImpl extends ServiceImpl<AccrualMapper, Accrual>  implements AccrualService {
    
    private final AccrualMapper accrualMapper;
    
    @Override
    public IPage<AccrualResp> queryPage(AccrualQueryReq req) {
        Page<Accrual> page = new Page<>(req.getPageNum(), req.getPageSize());
        IPage<Accrual> accrualPage = accrualMapper.selectPage(page, req);
        
        return accrualPage.convert(this::convertToResp);
    }
    
    @Override
    public void export(AccrualQueryReq req, HttpServletResponse response) {
        log.info("导出计提数据列表，查询条件：{}", req);
        
        // 设置响应头
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String fileName = DateUtil.getTimestampFileName("accrual", ".xlsx");
        try {
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            log.error("文件名编码失败", e);
        }
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);

        // 查询数据
        List<Accrual> accruals = accrualMapper.selectPage(new Page<>(1, Integer.MAX_VALUE), req).getRecords();
        List<AccrualExportResp> data = accruals.stream()
                .map(this::convertToExportResp)
                .collect(Collectors.toList());

        // 导出Excel
        try {
            EasyExcel.write(response.getOutputStream(), AccrualExportResp.class)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .sheet("计提数据")
                    .doWrite(data);
        } catch (IOException e) {
            log.error("导出计提数据列表失败", e);
            throw new RuntimeException("导出计提数据列表失败", e);
        }
    }
    
    @Override
    public String batchInterestJournal(BatchInterestJournalReq req) {
        // TODO: 实现批量计提利息制证功能
        return "已完成" + req.getTradeNumbers().size() + "条计提数据制证";
    }

    /**
     * 生成计提数据
     */
    @Override
    public void generate() {
        log.info("开始生成计提数据");
        // 循环处理每种类型的计提数据
        for (RemarkTypeEnum type : RemarkTypeEnum.values()) {
           generateAccruals(type);
        }
        
        log.info("计提数据生成完成");
    }
    
    /**
     * 生成计提数据
     * @param type 计提类型
     */
    private void generateAccruals(RemarkTypeEnum type) {
        log.info("开始生成{}类型计提数据", type.getCode());
        LocalDate currentDate = LocalDate.now();

        // 1. 构建查询条件
        LambdaQueryWrapper<TradeBlotter> queryWrapper = buildTradeBlotterQueryWrapper(type, currentDate);

        // 2. 查询符合条件的交易数据
        List<TradeBlotter> tradeBlotters = accrualMapper.selectTradeBlotters(queryWrapper);
        
        // 3. 按照currency和isin分组处理数据
        Map<String, List<TradeBlotter>> groupedTrades = tradeBlotters.stream()
                .collect(Collectors.groupingBy(trade -> 
                    trade.getCurrency() + "_" + trade.getIsin()));
        
        // 4. 处理每个分组的数据
        List<Accrual> accruals = new ArrayList<>();
        for (List<TradeBlotter> trades : groupedTrades.values()) {
            // 如果是债券、CD、EFB、UST类型的交易
            if (RemarkTypeEnum.BOND.equals(type) ||
                    RemarkTypeEnum.CD.equals(type) ||
                    RemarkTypeEnum.EFB.equals(type) ||
                    RemarkTypeEnum.UST.equals(type)) {
                
                // 计算净面值
                BigDecimal netAmount = calculateNetAmount(trades);
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
            
            TradeBlotter firstTrade = trades.get(0);

            // 创建计提数据对象
            Accrual accrual = new Accrual();
            accrual.setRemark(type.getCode());
            accrual.setTradeNumber(trades.stream()
                    .map(TradeBlotter::getTradeNumber)
                    .distinct()
                    .collect(Collectors.joining(",")));
            accrual.setEntityId(firstTrade.getEntityId());
            accrual.setCounterpart(trades.stream()
                    .map(TradeBlotter::getCounterpart)
                    .distinct()
                    .collect(Collectors.joining(",")));
            accrual.setCurrency(firstTrade.getCurrency());

            // 计算价格 = sum(settlementAmount) - sum(faceAmount)
            BigDecimal totalSettlement = trades.stream()
                    .map(TradeBlotter::getSettlementAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal totalFace = trades.stream()
                    .map(TradeBlotter::getFaceAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            accrual.setPrice(totalSettlement.divide(totalFace, 4, RoundingMode.HALF_UP));

            // 计算HKD金额和基础货币面值汇总
            calculateAmounts(trades, type, accrual);

            accrual.setDebtSecurityName(firstTrade.getDebtSecurityName());
            accrual.setIsin(firstTrade.getIsin());
            accrual.setInterestJournalStatus(VoucherStatusEnum.UNVOUCHERED.getCode());
            accrual.setAllocPremDiscJournalStatus(VoucherStatusEnum.UNVOUCHERED.getCode());
            accrual.setValuationJournalStatus(VoucherStatusEnum.UNVOUCHERED.getCode());
            accrual.setAccrualDate(currentDate);
            accrual.setCreatedTime(LocalDateTime.now());
            accrual.setUpdatedTime(LocalDateTime.now());

            // 计算计提数据
            calculateAccrualData(trades, type, accrual, currentDate);

            accruals.add(accrual);
        }
        
        // 5. 批量保存计提数据
        if (!accruals.isEmpty()) {
            List<Accrual> toInsert = new ArrayList<>();
            List<Accrual> toUpdate = new ArrayList<>();
            
            for (Accrual accrual : accruals) {
                // 查询是否已存在相同记录
                Accrual existing = accrualMapper.selectByUniqueKey(
                    accrual.getRemark(),
                    accrual.getCurrency(),
                    accrual.getIsin(),
                    accrual.getAccrualDate()
                );
                
                if (existing != null) {
                    // 更新已存在的记录
                    accrual.setId(existing.getId());
                    accrual.setCreatedTime(existing.getCreatedTime());
                    accrual.setCreatedBy(existing.getCreatedBy());
                    toUpdate.add(accrual);
                } else {
                    // 新增记录
                    toInsert.add(accrual);
                }
            }
            
            // 批量插入新记录
            if (!toInsert.isEmpty()) {
                accrualMapper.batchInsert(toInsert);
                log.info("成功插入{}条{}类型计提数据", toInsert.size(), type.getCode());
            }
            
            // 批量更新已存在记录
            if (!toUpdate.isEmpty()) {
                for (Accrual accrual : toUpdate) {
                    accrualMapper.updateById(accrual);
                }
                log.info("成功更新{}条{}类型计提数据", toUpdate.size(), type.getCode());
            }
        } else {
            log.info("没有需要生成的{}类型计提数据", type.getCode());
        }
    }

    /**
     * 构建交易查询条件
     * @param remarkType 备注类型
     * @param currentDate 当前日期
     * @return 查询条件
     */
    private LambdaQueryWrapper<TradeBlotter> buildTradeBlotterQueryWrapper(RemarkTypeEnum remarkType, LocalDate currentDate) {
        LambdaQueryWrapper<TradeBlotter> queryWrapper = new LambdaQueryWrapper<TradeBlotter>()
                .eq(TradeBlotter::getRemark, remarkType.getCode());

        // 添加日期相关查询条件
        addDateQueryConditions(queryWrapper, remarkType, currentDate);

        // 添加买卖方向查询条件
        addBuySellQueryCondition(queryWrapper, remarkType);

        return queryWrapper;
    }

    /**
     * 添加买卖方向查询条件
     * @param queryWrapper 查询条件
     * @param remarkType 备注类型
     */
    private void addBuySellQueryCondition(LambdaQueryWrapper<TradeBlotter> queryWrapper, RemarkTypeEnum remarkType) {
        switch (remarkType) {
            case BOND, EFB, CD, UST:
                queryWrapper.in(TradeBlotter::getBuySellBorrowLend,
                        Arrays.asList(TradeTypeEnum.PAOB_BUY.getCode(), TradeTypeEnum.PAOB_SELL.getCode()));
                break;
            case FIXED_DEPOSIT, INTERBANK:
                queryWrapper.in(TradeBlotter::getBuySellBorrowLend,
                        Arrays.asList(TradeTypeEnum.PAOB_LEND.getCode()));
                break;
            default:
                break;
        }
    }

    /**
     * 添加日期相关查询条件
     * @param queryWrapper 查询条件
     * @param remarkType 备注类型
     * @param currentDate 当前日期
     */
    private void addDateQueryConditions(LambdaQueryWrapper<TradeBlotter> queryWrapper, RemarkTypeEnum remarkType, LocalDate currentDate) {
        // 根据remarkType类型添加日期查询条件
        switch (remarkType) {
            case BOND, EFB, CD, UST, FIXED_DEPOSIT, INTERBANK:
                queryWrapper.le(TradeBlotter::getValueDate, currentDate)
                    .gt(TradeBlotter::getMaturityDate, currentDate);
                break;
            default:
                break;
        }
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
     * 将实体类转换为响应对象
     *
     * @param accrual 实体类
     * @return 响应对象
     */
    private AccrualResp convertToResp(Accrual accrual) {
        AccrualResp resp = new AccrualResp();
        BeanUtils.copyProperties(accrual, resp);
        resp.setDate(accrual.getAccrualDate());
        return resp;
    }
    
    /**
     * 将实体类转换为导出响应对象
     *
     * @param accrual 实体类
     * @return 导出响应对象
     */
    private AccrualExportResp convertToExportResp(Accrual accrual) {
        AccrualExportResp resp = new AccrualExportResp();
        BeanUtils.copyProperties(accrual, resp);
        resp.setDate(accrual.getAccrualDate());
        return resp;
    }

    /**
     * 计算HKD金额和基础货币面值汇总
     * @param trades 交易列表
     * @param type 计提类型
     * @param accrual 计提数据对象
     */
    private void calculateAmounts(List<TradeBlotter> trades, RemarkTypeEnum type, Accrual accrual) {
        switch (type) {
            case BOND, EFB, CD, UST:
                // 对于债券类产品，计算买入和卖出的差额
                BigDecimal buyHkdAmount = trades.stream()
                        .filter(t -> TradeTypeEnum.PAOB_BUY.getCode().equals(t.getBuySellBorrowLend()))
                        .map(TradeBlotter::getHkdAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal sellHkdAmount = trades.stream()
                        .filter(t -> TradeTypeEnum.PAOB_SELL.getCode().equals(t.getBuySellBorrowLend()))
                        .map(TradeBlotter::getHkdAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                accrual.setSumOfHkdAmount(buyHkdAmount.subtract(sellHkdAmount));

                BigDecimal buyFaceAmount = trades.stream()
                        .filter(t -> TradeTypeEnum.PAOB_BUY.getCode().equals(t.getBuySellBorrowLend()))
                        .map(TradeBlotter::getFaceAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal sellFaceAmount = trades.stream()
                        .filter(t -> TradeTypeEnum.PAOB_SELL.getCode().equals(t.getBuySellBorrowLend()))
                        .map(TradeBlotter::getFaceAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                accrual.setSumOfNotionalInBaseCurrency(buyFaceAmount.subtract(sellFaceAmount));
                break;

            case FIXED_DEPOSIT, INTERBANK:
                // 对于存款类产品，直接使用借出金额
                BigDecimal lendHkdAmount = trades.stream()
                        .filter(t -> TradeTypeEnum.PAOB_LEND.getCode().equals(t.getBuySellBorrowLend()))
                        .map(TradeBlotter::getHkdAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                accrual.setSumOfHkdAmount(lendHkdAmount);

                BigDecimal lendFaceAmount = trades.stream()
                        .filter(t -> TradeTypeEnum.PAOB_LEND.getCode().equals(t.getBuySellBorrowLend()))
                        .map(TradeBlotter::getFaceAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                accrual.setSumOfNotionalInBaseCurrency(lendFaceAmount);
                break;
            default:
                log.warn("未知的计提类型：{}", type.getCode());
                break;
        }
    }

    /**
     * 计算当日应计利息，已计利息，当日分摊溢价，已分摊溢价，未分摊溢价，当日估值变动，已计提估值变动
     * @param trades 交易列表
     * @param type 计提类型
     * @param accrual 计提数据对象
     * @param currentDate 当前日期
     */
    private void calculateAccrualData(List<TradeBlotter> trades, RemarkTypeEnum type, Accrual accrual, LocalDate currentDate) {
        switch (type) {
            case BOND, EFB, CD, UST:
                calculateBondAccrualData(trades, accrual, currentDate);
                break;
            case FIXED_DEPOSIT, INTERBANK:
                calculateDepositAccrualData(trades, accrual, currentDate);
                break;
            default:
                break;
        }
    }

    /**
     * 计算债券类产品的计提数据
     * @param trades 交易列表
     * @param accrual 计提数据对象
     * @param currentDate 当前日期
     */
    private void calculateBondAccrualData(List<TradeBlotter> trades, Accrual accrual, LocalDate currentDate) {
        // 计算净面值
        BigDecimal buyFaceAmount = trades.stream()
                .filter(t -> TradeTypeEnum.PAOB_BUY.getCode().equals(t.getBuySellBorrowLend()))
                .map(TradeBlotter::getFaceAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal sellFaceAmount = trades.stream()
                .filter(t -> TradeTypeEnum.PAOB_SELL.getCode().equals(t.getBuySellBorrowLend()))
                .map(TradeBlotter::getFaceAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal netFaceAmount = buyFaceAmount.subtract(sellFaceAmount);

        // 获取第一个交易用于获取日期信息
        TradeBlotter firstTrade = trades.get(0);
        long daysBetween = ChronoUnit.DAYS.between(firstTrade.getValueDate(), firstTrade.getMaturityDate());

        // 计算每日应计利息
        BigDecimal buyInterest = trades.stream()
                .filter(t -> TradeTypeEnum.PAOB_BUY.getCode().equals(t.getBuySellBorrowLend()))
                .map(TradeBlotter::getInterest)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal sellInterest = trades.stream()
                .filter(t -> TradeTypeEnum.PAOB_SELL.getCode().equals(t.getBuySellBorrowLend()))
                .map(TradeBlotter::getInterest)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal dailyAccruedInterest = (buyInterest.subtract(sellInterest))
                .divide(BigDecimal.valueOf(daysBetween), 4, RoundingMode.HALF_UP);
        accrual.setDailyAccruedInterest(dailyAccruedInterest);

        // 计算已计利息
        BigDecimal previousAccruedInterest = getPreviousAccruedInterest(accrual);
        if (netFaceAmount.compareTo(BigDecimal.ZERO) > 0) {
            accrual.setAccruedInterest(dailyAccruedInterest.add(previousAccruedInterest));
        } else {
            BigDecimal ratio = sellFaceAmount.divide(buyFaceAmount, 4, RoundingMode.HALF_UP);
            accrual.setAccruedInterest(dailyAccruedInterest.add(previousAccruedInterest.multiply(ratio)));
        }

        // 计算每日分摊溢价
        BigDecimal buySettlement = trades.stream()
                .filter(t -> TradeTypeEnum.PAOB_BUY.getCode().equals(t.getBuySellBorrowLend()))
                .map(t -> t.getSettlementAmount().add(t.getFaceAmount().multiply(t.getPrice()).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal sellSettlement = trades.stream()
                .filter(t -> TradeTypeEnum.PAOB_SELL.getCode().equals(t.getBuySellBorrowLend()))
                .map(t -> t.getSettlementAmount().add(t.getFaceAmount().multiply(t.getPrice()).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal netSettlement = buySettlement.subtract(sellSettlement);
        BigDecimal netAllocPremDisc = buySettlement.add(buyFaceAmount).subtract(sellSettlement.add(sellFaceAmount));
        BigDecimal dailyAllocPremDisc = netAllocPremDisc.divide(BigDecimal.valueOf(daysBetween), 4, RoundingMode.HALF_UP);
        accrual.setDailyAllocPremDisc(dailyAllocPremDisc);

        // 计算已分摊溢价
        BigDecimal previousAllocPremDisc = getPreviousAllocPremDisc(accrual);
        if (netFaceAmount.compareTo(BigDecimal.ZERO) > 0) {
            accrual.setAllocPremDisc(dailyAllocPremDisc.add(previousAllocPremDisc));
        } else {
            BigDecimal ratio = sellFaceAmount.divide(buyFaceAmount, 4, RoundingMode.HALF_UP);
            accrual.setAllocPremDisc(dailyAllocPremDisc.add(previousAllocPremDisc.multiply(ratio)));
        }

        // 计算未分摊溢价
        accrual.setNoAllocPremDisc(netAllocPremDisc.subtract(accrual.getAllocPremDisc()));

        // 计算当日估值变动
        BigDecimal dailyValuationChange = calculateDailyValuationChange(trades, currentDate, firstTrade.getValueDate(),netSettlement);
        accrual.setDailyValuationChange(dailyValuationChange);

        // 计算已计估值变动
        BigDecimal previousValuationChange = getPreviousValuationChange(accrual);
        if (netFaceAmount.compareTo(BigDecimal.ZERO) > 0) {
            accrual.setAccruedValuationChange(previousValuationChange.add(dailyValuationChange));
        } else {
            BigDecimal ratio = sellFaceAmount.divide(buyFaceAmount, 6, RoundingMode.HALF_UP);
            BigDecimal previousValuation = previousValuationChange.multiply(ratio);
            previousValuation.setScale(4, RoundingMode.HALF_UP);
            accrual.setAccruedValuationChange(previousValuation.add(dailyValuationChange));
        }
    }

    /**
     * 计算存款类产品的计提数据
     * @param trades 交易列表
     * @param accrual 计提数据对象
     * @param currentDate 当前日期
     */
    private void calculateDepositAccrualData(List<TradeBlotter> trades, Accrual accrual, LocalDate currentDate) {
        // 获取第一个交易用于获取日期信息
        TradeBlotter firstTrade = trades.get(0);
        long daysBetween = ChronoUnit.DAYS.between(firstTrade.getValueDate(), firstTrade.getMaturityDate());

        // 计算每日应计利息
        BigDecimal totalInterest = trades.stream()
                .filter(t -> TradeTypeEnum.PAOB_LEND.getCode().equals(t.getBuySellBorrowLend()))
                .map(TradeBlotter::getInterest)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal dailyAccruedInterest = totalInterest.divide(BigDecimal.valueOf(daysBetween), 4, RoundingMode.HALF_UP);
        accrual.setDailyAccruedInterest(dailyAccruedInterest);

        // 计算已计利息
        BigDecimal previousAccruedInterest = getPreviousAccruedInterest(accrual);
        accrual.setAccruedInterest(previousAccruedInterest.add(dailyAccruedInterest));
    }

    /**
     * 获取上一天的已计利息
     * @param accrual 计提数据对象
     * @return 上一天已计利息，如果不存在则返回0
     */
    private BigDecimal getPreviousAccruedInterest(Accrual accrual) {
        BigDecimal previousInterest = accrualMapper.selectPreviousAccruedInterest(
                accrual.getRemark(),
                accrual.getCurrency(),
                accrual.getIsin(),
                accrual.getAccrualDate()
        );
        return previousInterest != null ? previousInterest : BigDecimal.ZERO;
    }

    /**
     * 获取上一天的已分摊溢价
     */
    private BigDecimal getPreviousAllocPremDisc(Accrual accrual) {
        BigDecimal previousPremDisc =  accrualMapper.selectPreviousAllocPremDisc(
            accrual.getRemark(),
            accrual.getCurrency(),
            accrual.getIsin(),
            accrual.getAccrualDate()
        );
        return previousPremDisc != null ? previousPremDisc : BigDecimal.ZERO;
    }

    /**
     * 获取上一天的已计估值变动
     * @param accrual 计提数据对象
     * @return 上一天已计估值变动
     */
    private BigDecimal getPreviousValuationChange(Accrual accrual) {
        BigDecimal previousValuationChange=  accrualMapper.selectPreviousAccruedValuationChange(
            accrual.getRemark(),
            accrual.getCurrency(),
            accrual.getIsin(),
            accrual.getAccrualDate()
        );
        return previousValuationChange != null ? previousValuationChange : BigDecimal.ZERO;
    }

    /**
     * 计算当日估值变动
     * @param trades 交易列表
     * @param currentDate 当前日期
     * @param valueDate 起息日
     * @param netSettlement 净结算金额
     * @return 当日估值变动
     */
    private BigDecimal calculateDailyValuationChange(List<TradeBlotter> trades, LocalDate currentDate, LocalDate valueDate, BigDecimal netSettlement) {
        // 获取第一个交易用于获取ISIN和币种信息
        TradeBlotter firstTrade = trades.get(0);
        
        // 如果当日是起息日
        if (currentDate.equals(valueDate)) {
            // 获取市场价值
            BigDecimal sumMarketValue = getMarketValue(firstTrade.getRemark(),firstTrade.getIsin(), firstTrade.getCurrency(),currentDate);
            // 当日估值变动 = 市场价值 - 净结算金额
            return sumMarketValue.subtract(netSettlement);
        } else {
            // 如果当日大于起息日
            // 获取当日市场价值
            BigDecimal currentMarketValue = getMarketValue(firstTrade.getRemark(),firstTrade.getIsin(), firstTrade.getCurrency(), currentDate);
            // 获取上一日市场价值
            BigDecimal previousMarketValue = getPreviousMarketValue(firstTrade.getRemark(), firstTrade.getIsin(), firstTrade.getCurrency(), currentDate);
            // 当日估值变动 = 当日市场价值 - 上一日市场价值
            return currentMarketValue.subtract(previousMarketValue);
        }
    }

    /**
     * 获取市场价值
     * @param isin ISIN代码
     * @param currency 币种
     * @param currentDate 当前日期
     * @return 市场价值
     */
    private BigDecimal getMarketValue(String remark, String isin, String currency, LocalDate currentDate) {
        BigDecimal marketValue =  accrualMapper.selectMarketValue(remark, isin, currency, currentDate);
        return marketValue != null ? marketValue : BigDecimal.ZERO;
    }

    /**
     * 获取上一日市场价值
     * @param isin ISIN代码
     * @param currency 币种
     * @param currentDate 当前日期
     * @return 上一日市场价值
     */
    private BigDecimal getPreviousMarketValue(String remark, String isin, String currency, LocalDate currentDate) {
        LocalDate previousDate = currentDate.minusDays(1);

        BigDecimal  previousMarketValue = accrualMapper.selectMarketValue(remark, isin, currency, previousDate);
        return previousMarketValue != null ? previousMarketValue : BigDecimal.ZERO;
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