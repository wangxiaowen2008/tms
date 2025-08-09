package com.paob.tms.enums;

import lombok.Getter;

@Getter
public enum DataSourceEnum {
    
    TMS_TRADE_BLOTTER("TMS_TRADE_BLOTTER", "交易记录"),
    TMS_ACCRUAL("TMS_ACCRUAL", "计提记录"),
    TMS_CORPORATE_ACTION("TMS_CORPORATE_ACTION", "公司行为");

    private final String code;
    private final String description;

    DataSourceEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static DataSourceEnum getByCode(String code) {
        for (DataSourceEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
} 