package com.paob.tms.enums;

import lombok.Getter;

/**
 * REPO交易备注类型枚举
 */
@Getter
public enum RemarkTypeEnum {
    BOND("Bond", "债券"),
    CD("CD", "存单"),
    EFB("EFB", "电子银行承兑汇票"),
    UST("UST", "美国国债"),
    INTERBANK("Interbank", "同业拆借"),
    FIXED_DEPOSIT("Fixed Deposit", "定期存款"),
    REPO("REPO", "回购交易");

    /**
     * 代码
     */
    private final String code;

    /**
     * 描述
     */
    private final String description;

    RemarkTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 根据代码获取枚举值
     *
     * @param code 代码
     * @return 枚举值
     */
    public static RemarkTypeEnum getByCode(String code) {
        for (RemarkTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
} 