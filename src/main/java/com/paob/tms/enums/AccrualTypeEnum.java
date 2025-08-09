package com.paob.tms.enums;

/**
 * 计提类型枚举
 */
public enum AccrualTypeEnum {
    
    ACCRUAL_INTEREST("计提利息收入", "计提利息收入"),
    ALLOC_PREM_DISC("分摊溢折价", "分摊溢折价"),
    ACCRUAL_VALUATION("计提估值变动", "计提估值变动");

    private final String code;
    private final String description;

    AccrualTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static AccrualTypeEnum getByCode(String code) {
        for (AccrualTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
} 