package com.paob.tms.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Data
@TableName("tms_trade_blotter")
public class TmsTradeBlotter {
    
    @TableId
    private String tradeNumber;
    
    private Integer item;
    
    private String entityId;
    
    private String counterpart;
    
    private String buySellBorrowLend;
    
    private BigDecimal yield;
    
    private String currency;
    
    private BigDecimal settlementAmount;
    
    private LocalDate tradeDate;
    
    private LocalDate valueDate;
    
    private LocalDate maturityDate;
    
    private String remark;
    
    private Integer dayCount;
    
    private BigDecimal interest;
    
    private BigDecimal price;
    
    private String debtSecurityName;
    
    private String isin;
    
    private BigDecimal faceAmount;
    
    private BigDecimal amountReceivedAtMaturity;
    
    private BigDecimal interestPaidUpfront;
    
    private BigDecimal netInterest;
    
    private String couponType;
    
    private String couponFrequent;
    
    private String channel;
    
    private String settlementAccount;
    
    private String broker;
    
    private LocalTime tradeTime;
    
    private LocalDate interestAccrualDate;
    
    private String fullName;

    @TableField("full_name_2")
    private String fullName2;
    
    private String branch;
    
    private String branch2;
    
    private String name;
    
    private String series;
    
    private String match;
    
    private String maturity;
    
    private String groupParent;
    
    private BigDecimal hkdAmount;
    
    private BigDecimal usdAmount;
    
    private Integer tradeDateVsValueDate;
    
    private String englishName;
    
    private String ringFenced;
    
    private Integer daysToLastMonthEnd;
    
    private Integer daysToMaturity;
    
    private String tenorBucket;
    
    private String irTenorBucket;
    
    private String holdingsPeriod;
    
    private String rank;
    
    private BigDecimal interestInHkd;
    
    private BigDecimal notionalInBaseCurrency;
    
    private String type;
    
    private String boc;
    
    private String scb;
    
    private String hsbc;
    
    private String ccba;
    
    private String des;
    
    private BigDecimal maxDiff;
    
    private BigDecimal auctionAve;
    
    private BigDecimal auctionHigh;
    
    private LocalDate tDay;
    
    private String tradeNoOld;

    @TableField("day_count_2")
    private Integer dayCount2;
    
    private String countryOfDomicileHd;
    
    private String countryOfDomicile;
    
    private BigDecimal bbgIssuedAmountHd;
    
    private BigDecimal bbgIssuedAmount;
    
    private String bbgPrivatePlacementHd;
    
    private String bbgPrivatePlacement;
    
    private LocalDate nextCouponDateHc;
    
    private LocalDate nextCouponDate;
    
    private String issuerRating;
    
    private String parentId;
    
    private String issuerCountry;
    
    private String countryOfIncorporation;
    
    private LocalDate reportDate;
    
    private LocalDate riskReportDate;
    
    private BigDecimal faceAmountInHkd;
    
    private BigDecimal faceAmountInUsd;
    
    private String countryRating;
    
    private String instrument;
    
    private String assetTypeRank;
    
    private String booking;
    
    private String monthEndBookingCheck;
    
    private BigDecimal nextInterest;
    
    private String paymentStatus;
    
    private String journalStatus;
    
    private String journalSequence;
    
    private String valueDateJournalStatus;
    
    private String valueDateJournalSequence;
    
    private String orderId;
    
    private String streamNo;
    
    private String existCompany;
    
    private String existAccrual;
    
    private String createdBy;
    
    private LocalDateTime createdTime;
    
    private String updatedBy;
    
    private LocalDateTime updatedTime;
} 