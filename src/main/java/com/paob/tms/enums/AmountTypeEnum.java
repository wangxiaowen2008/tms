package com.paob.tms.enums;

import lombok.Getter;

@Getter
public enum AmountTypeEnum {
    
    SETTLEMENT_AMOUNT("【SETTLEMENT AMOUNT】", "结算金额"),
    FACE_AMOUNT("【FACE AMOUNT】", "面值金额"),
    PRICE("【PRICE】", "价格"),
    ACCRUED_INTEREST("【已计提利息】", "已计提利息"),
    UNAMORTIZED_PREMIUM("【未分摊溢折价】", "未分摊溢折价"),
    ACCRUED_VALUATION_CHANGE("【已计提估值变动】", "已计提估值变动"),
    DAILY_ACCRUED_INTEREST("【当日计提利息】", "当日计提利息"),
    DAILY_ALLOC_PREM_DISC("【当日分摊溢折价】", "当日分摊溢折价"),
    DAILY_VALUATION_CHANGE("【当日估值变动】", "当日估值变动"),
    RATIO("比例", "买卖比例");

    private final String code;
    private final String description;

    AmountTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static AmountTypeEnum getByCode(String code) {
        for (AmountTypeEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
} 