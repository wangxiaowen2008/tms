package com.paob.tms.enums;

import lombok.Getter;

@Getter
public enum DescriptionParamEnum {
    
    CURRENCY("【CURRENCY】", "币种"),
    FACE_AMOUNT("【FACE AMOUNT】", "面值金额"),
    YIELD("【YIELD】", "收益率"),
    VALUE_DATE("【VALUE DATE】", "起息日"),
    MATURITY_DATE("【MATURITY DATE】", "到期日"),
    SETTLEMENT_AMOUNT("【SETTLEMENT AMOUNT】", "结算金额"),
    ISIN("【ISIN】", "ISIN代码");

    private final String code;
    private final String description;

    DescriptionParamEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static DescriptionParamEnum getByCode(String code) {
        for (DescriptionParamEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
} 