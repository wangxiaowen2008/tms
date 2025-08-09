package com.paob.tms.enums;

import lombok.Getter;

@Getter
public enum EffectiveDateEnum {
    
    CURRENT_DATE("当前日期", "当前日期"),
    TRADE_DATE("TRADE DATE", "交易日期"),
    VALUE_DATE("VALUE DATE", "起息日期"),
    MATURITY_DATE("MATURITY DATE", "到期日期"),
    SETTLEMENT_DATE("SETTLEMENT DATE", "结算日期"),
    ACCRUAL_DATE("ACCRUAL DATE", "计提日期");

    private final String code;
    private final String description;

    EffectiveDateEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static EffectiveDateEnum getByCode(String code) {
        for (EffectiveDateEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
} 