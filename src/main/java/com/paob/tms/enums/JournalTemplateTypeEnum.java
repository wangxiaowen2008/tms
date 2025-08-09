package com.paob.tms.enums;

import lombok.Getter;

/**
 * 凭证模板类型枚举
 */
@Getter
public enum JournalTemplateTypeEnum {
    TRADE_DATE("1", "交易日期"),
    VALUE_DATE("2", "起息日"),
    ACCRUAL_INTEREST("11", "计提利息收入"),
    ALLOC_PREM_DISC("22", "分摊溢折价"),
    ACCRUAL_VALUATION("33", "计提估值变动");

    private final String code;
    private final String desc;

    JournalTemplateTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static JournalTemplateTypeEnum getByCode(String code) {
        for (JournalTemplateTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }


    public boolean isAccrualType() {
        return this == ACCRUAL_INTEREST || this == ALLOC_PREM_DISC || this == ACCRUAL_VALUATION;
    }

} 