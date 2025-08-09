package com.paob.tms.enums;

import lombok.Getter;

/**
 * 公司行为类型枚举
 */
@Getter
public enum ActionTypeEnum {
    
    PAY_INTEREST("0", "支付利息"),
    PAY_PRINCIPAL_AND_INTEREST("1", "支付本金&利息"),
    RECEIVE_INTEREST("2", "收取利息"),
    RECEIVE_PRINCIPAL_AND_INTEREST("3", "收取本金&利息");
    
    private final String code;
    private final String description;
    
    ActionTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 根据编码获取枚举
     *
     * @param code 编码
     * @return 枚举对象
     */
    public static ActionTypeEnum getByCode(String code) {
        for (ActionTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
} 