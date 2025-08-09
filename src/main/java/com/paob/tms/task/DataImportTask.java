package com.paob.tms.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.paob.tms.mapper.TmsDailyRateMapper;
import com.paob.tms.mapper.TmsSubjectBalanceMapper;
import com.paob.tms.model.TmsDailyRate;
import com.paob.tms.model.TmsSubjectBalance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Component
public class DataImportTask {

    @Autowired
    private TmsDailyRateMapper dailyRateMapper;

    @Autowired
    private TmsSubjectBalanceMapper subjectBalanceMapper;

    @Value("${voucher.file.path}")
    private String voucherFilePath;

    private static final String RATE_FILE_PREFIX = "fin_gl_daily_rates_to_hkvb.dat";
    private static final String BALANCE_FILE_PREFIX = "fin_gl_balances_to_hkvb.dat";
    private static final String FIELD_SEPARATOR = "\u001B";

    //@Scheduled(cron = "0 0 */1 * * ?") // 每小时执行一次
    public void importData() {
        log.info("开始导入汇率和科目余额数据...");
        try {
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String basePath = voucherFilePath + today;

            // 处理汇率数据
            processRateFile(basePath);
            
            // 处理科目余额数据
            processBalanceFile(basePath);
            
        } catch (Exception e) {
            log.error("导入数据失败", e);
        }
    }

    private void processRateFile(String basePath) {
        Path filePath = Paths.get(basePath, RATE_FILE_PREFIX);
        if (!Files.exists(filePath)) {
            log.info("汇率文件不存在: {}", filePath);
            return;
        }

        log.info("开始处理汇率文件: {}", filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            List<TmsDailyRate> rates = new ArrayList<>();
            
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(FIELD_SEPARATOR);
                if (fields.length < 6) {
                    log.warn("汇率数据格式不正确: {}", line);
                    continue;
                }

                TmsDailyRate rate = new TmsDailyRate();
                // 设置字段值
                rate.setFromCurrency(fields[0]);
                rate.setToCurrency(fields[1]);
                
                // 解析日期并检查是否在两个月范围内
                LocalDate conversionDate = LocalDate.parse(fields[2]);
                LocalDate currentDate = LocalDate.now();
                LocalDate twoMonthsAgo = currentDate.minusMonths(2);
                
                // 如果日期不在两个月范围内，跳过该记录
                if (conversionDate.isBefore(twoMonthsAgo) || conversionDate.isAfter(currentDate)) {
                    log.info("跳过超出时间范围的汇率数据: {}, 转换日期: {}", line, conversionDate);
                    continue;
                }
                
                rate.setConversionDate(conversionDate);
                rate.setConversionType(fields[3]);
                rate.setConversionRate(fields[4]);
                rate.setStatusCode("1");
                rate.setCreationDate(LocalDate.now());
                rate.setCreatedBy("SYSTEM");
                rate.setLastUpdateDate(LocalDate.now());
                rate.setLastUpdatedBy("SYSTEM");
                rate.setLastUpdateLogin("SYSTEM");
                rate.setContext("SYSTEM_IMPORT");
                rate.setRateSourceCode("SYSTEM");

                // 检查记录是否存在
                LambdaQueryWrapper<TmsDailyRate> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(TmsDailyRate::getFromCurrency, rate.getFromCurrency())
                        .eq(TmsDailyRate::getToCurrency, rate.getToCurrency())
                        .eq(TmsDailyRate::getConversionDate, rate.getConversionDate())
                        .eq(TmsDailyRate::getConversionType, rate.getConversionType());

                TmsDailyRate existingRate = dailyRateMapper.selectOne(wrapper);
                if (existingRate != null) {
                    // 使用联合唯一键更新记录
                    LambdaUpdateWrapper<TmsDailyRate> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.eq(TmsDailyRate::getFromCurrency, rate.getFromCurrency())
                            .eq(TmsDailyRate::getToCurrency, rate.getToCurrency())
                            .eq(TmsDailyRate::getConversionDate, rate.getConversionDate())
                            .eq(TmsDailyRate::getConversionType, rate.getConversionType())
                            .set(TmsDailyRate::getConversionRate, rate.getConversionRate())
                            .set(TmsDailyRate::getStatusCode, rate.getStatusCode())
                            .set(TmsDailyRate::getLastUpdateDate, rate.getLastUpdateDate())
                            .set(TmsDailyRate::getLastUpdatedBy, rate.getLastUpdatedBy())
                            .set(TmsDailyRate::getLastUpdateLogin, rate.getLastUpdateLogin())
                            .set(TmsDailyRate::getContext, rate.getContext())
                            .set(TmsDailyRate::getRateSourceCode, rate.getRateSourceCode());

                    dailyRateMapper.update(null, updateWrapper);
                    log.info("更新汇率记录: {}", rate);
                } else {
                    // 插入记录
                    dailyRateMapper.insert(rate);
                    log.info("插入汇率记录: {}", rate);
                }
            }
        } catch (Exception e) {
            log.error("处理汇率文件失败", e);
        }
    }

    private String convertPeriodName(String periodName) {
        if (periodName == null || periodName.isEmpty()) {
            return null;
        }
        try {
            // 将期段名称转换为标准格式，例如 "JAN-23" 变为 "Jan-23"
            String standardizedPeriodName = standardizePeriodName(periodName);

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM-yy", Locale.ENGLISH);
            YearMonth yearMonth = YearMonth.parse(standardizedPeriodName, inputFormatter);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
            return yearMonth.format(outputFormatter);
        } catch (DateTimeParseException e) {
            log.error("无法解析期段名称格式: {}", periodName, e);
            return periodName; // 如果解析失败，返回原始值或根据需求处理
        }
    }

    // 添加一个辅助方法用于标准化期段名称的大小写
    private String standardizePeriodName(String periodName) {
        if (periodName == null || periodName.length() < 3) {
            return periodName;
        }
        // 假设格式总是 "MMM-yy" 或类似
        String monthPart = periodName.substring(0, 3);
        String restPart = periodName.substring(3);
        // 将月份部分转换为首字母大写，其余小写
        String standardizedMonthPart = monthPart.substring(0, 1).toUpperCase() + monthPart.substring(1).toLowerCase();
        return standardizedMonthPart + restPart;
    }

    private void processBalanceFile(String basePath) {
        Path filePath = Paths.get(basePath, BALANCE_FILE_PREFIX);
        if (!Files.exists(filePath)) {
            log.info("科目余额文件不存在: {}", filePath);
            return;
        }

        log.info("开始处理科目余额文件: {}", filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            List<TmsSubjectBalance> balances = new ArrayList<>();
            
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(FIELD_SEPARATOR);
                if (fields.length < 30) {
                    log.warn("科目余额数据格式不正确: {}", line);
                    continue;
                }

                TmsSubjectBalance balance = new TmsSubjectBalance();
                // 设置字段值
                balance.setCodeCombinationId(fields[0]);
                balance.setSetOfBooksId(fields[1]);
                balance.setBooksName(fields[2]);
                balance.setPeriodName(convertPeriodName(fields[3]));
                balance.setCurrencyCode(fields[4]);
                balance.setActualFlag(fields[5]);
                balance.setSegment1(fields[6]);
                balance.setSegment1Description(fields[7]);
                balance.setSegment2(fields[8]);
                balance.setSegment2Description(fields[9]);
                balance.setSegment3(fields[10]);
                balance.setSegment3Description(fields[11]);
                balance.setSegment4(fields[12]);
                balance.setSegment4Description(fields[13]);
                balance.setAccCode1(fields[14]);
                balance.setAccCode1Name(fields[15]);
                balance.setAccCode2(fields[16]);
                balance.setAccCode2Name(fields[17]);
                balance.setAccCode3(fields[18]);
                balance.setAccCode3Name(fields[19]);
                balance.setSegment6(fields[20]);
                balance.setSegment6Description(fields[21]);
                balance.setSegment7(fields[22]);
                balance.setSegment7Description(fields[23]);
                balance.setSegment8(fields[24]);
                balance.setSegment8Description(fields[25]);
                balance.setBeginBalanceDr(new BigDecimal(StringUtils.hasText(fields[26]) ? fields[26] : "0"));
                balance.setBeginBalanceCr(new BigDecimal(StringUtils.hasText(fields[27]) ? fields[27] : "0"));
                balance.setBeginBalance(new BigDecimal(StringUtils.hasText(fields[28]) ? fields[28] : "0"));
                balance.setPeriodNetDr(new BigDecimal(StringUtils.hasText(fields[29]) ? fields[29] : "0"));
                balance.setPeriodNetCr(new BigDecimal(StringUtils.hasText(fields[30]) ? fields[30] : "0"));
                balance.setPeriodNet(new BigDecimal(StringUtils.hasText(fields[31]) ? fields[31] : "0"));
                balance.setEndBalanceDr(new BigDecimal(StringUtils.hasText(fields[32]) ? fields[32] : "0"));
                balance.setEndBalanceCr(new BigDecimal(StringUtils.hasText(fields[33]) ? fields[33] : "0"));
                balance.setEndBalance(new BigDecimal(StringUtils.hasText(fields[34]) ? fields[34] : "0"));
                balance.setCreatedTime(LocalDateTime.now());
                balance.setUpdatedTime(LocalDateTime.now());

                // 检查记录是否存在
                LambdaQueryWrapper<TmsSubjectBalance> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(TmsSubjectBalance::getCodeCombinationId, balance.getCodeCombinationId())
                        .eq(TmsSubjectBalance::getSetOfBooksId, balance.getSetOfBooksId())
                        .eq(TmsSubjectBalance::getPeriodName, balance.getPeriodName())
                        .eq(TmsSubjectBalance::getCurrencyCode, balance.getCurrencyCode());

                TmsSubjectBalance existingBalance = subjectBalanceMapper.selectOne(wrapper);
                if (existingBalance != null) {
                    // 使用联合唯一键更新记录
                    LambdaUpdateWrapper<TmsSubjectBalance> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.eq(TmsSubjectBalance::getCodeCombinationId, balance.getCodeCombinationId())
                            .eq(TmsSubjectBalance::getSetOfBooksId, balance.getSetOfBooksId())
                            .eq(TmsSubjectBalance::getPeriodName, balance.getPeriodName())
                            .eq(TmsSubjectBalance::getCurrencyCode, balance.getCurrencyCode())
                            .set(TmsSubjectBalance::getBooksName, balance.getBooksName())
                            .set(TmsSubjectBalance::getActualFlag, balance.getActualFlag())
                            .set(TmsSubjectBalance::getSegment1, balance.getSegment1())
                            .set(TmsSubjectBalance::getSegment1Description, balance.getSegment1Description())
                            .set(TmsSubjectBalance::getSegment2, balance.getSegment2())
                            .set(TmsSubjectBalance::getSegment2Description, balance.getSegment2Description())
                            .set(TmsSubjectBalance::getSegment3, balance.getSegment3())
                            .set(TmsSubjectBalance::getSegment3Description, balance.getSegment3Description())
                            .set(TmsSubjectBalance::getSegment4, balance.getSegment4())
                            .set(TmsSubjectBalance::getSegment4Description, balance.getSegment4Description())
                            .set(TmsSubjectBalance::getAccCode1, balance.getAccCode1())
                            .set(TmsSubjectBalance::getAccCode1Name, balance.getAccCode1Name())
                            .set(TmsSubjectBalance::getAccCode2, balance.getAccCode2())
                            .set(TmsSubjectBalance::getAccCode2Name, balance.getAccCode2Name())
                            .set(TmsSubjectBalance::getAccCode3, balance.getAccCode3())
                            .set(TmsSubjectBalance::getAccCode3Name, balance.getAccCode3Name())
                            .set(TmsSubjectBalance::getSegment6, balance.getSegment6())
                            .set(TmsSubjectBalance::getSegment6Description, balance.getSegment6Description())
                            .set(TmsSubjectBalance::getSegment7, balance.getSegment7())
                            .set(TmsSubjectBalance::getSegment7Description, balance.getSegment7Description())
                            .set(TmsSubjectBalance::getSegment8, balance.getSegment8())
                            .set(TmsSubjectBalance::getSegment8Description, balance.getSegment8Description())
                            .set(TmsSubjectBalance::getBeginBalanceDr, balance.getBeginBalanceDr())
                            .set(TmsSubjectBalance::getBeginBalanceCr, balance.getBeginBalanceCr())
                            .set(TmsSubjectBalance::getPeriodNetDr, balance.getPeriodNetDr())
                            .set(TmsSubjectBalance::getPeriodNetCr, balance.getPeriodNetCr())
                            .set(TmsSubjectBalance::getEndBalanceDr, balance.getEndBalanceDr())
                            .set(TmsSubjectBalance::getEndBalanceCr, balance.getEndBalanceCr())
                            .set(TmsSubjectBalance::getEndBalance, balance.getEndBalance())
                            .set(TmsSubjectBalance::getUpdatedTime, balance.getUpdatedTime());

                    subjectBalanceMapper.update(null, updateWrapper);
                    log.info("更新科目余额记录: {}", balance);
                } else {
                    // 插入记录
                    subjectBalanceMapper.insert(balance);
                    log.info("插入科目余额记录: {}", balance);
                }
            }
        } catch (Exception e) {
            log.error("处理科目余额文件失败", e);
        }
    }
} 