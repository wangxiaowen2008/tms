package com.paob.tms.enums;

import lombok.Getter;

/**
 * 付款状态枚举
 */
@Getter
public enum PaymentStatusEnum {
    
    UNPAID("0", "未付款"),
    PAYING("1", "付款中"),
    PAID("2", "已付款"),
    UNRECEIVED("3", "未收款"),
    RECEIVED("4", "已收款");
    
    private final String code;
    private final String description;
    
    PaymentStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 根据编码获取枚举
     *
     * @param code 编码
     * @return 枚举对象
     */
    public static PaymentStatusEnum getByCode(String code) {
        for (PaymentStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
} 