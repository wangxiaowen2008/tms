package com.paob.tms.enums;

import lombok.Getter;

/**
 * REPO交易备注类型枚举
 */
@Getter
public enum RepoRemarkTypeEnum {
    
    BOND("Bond", "债券","Bond"),
    BOND1("Bond", "债券","Bond"),
    CD("CD", "存单","CD"),
    CD1("CD", "存单","CD"),
    EFB("EFB", "电子银行承兑汇票","EFB"),
    EFB1("EFB", "电子银行承兑汇票","EFB"),
    UST("UST", "美国国债","UST"),
    UST1("UST", "美国国债","UST"),
    INTERBANK("Interbank", "同业拆借","ITB"),
    NTERBANK1("Interbank", "同业拆借","ITB"),
    NTERBANK2("Interbank", "同业拆借","ITB"),
    NTERBANK3("Interbank", "同业拆借","ITB"),
    FIXED_DEPOSIT("Fixed Deposit", "定期存款","FD"),
    FIXED_DEPOSIT1("Fixed Deposit", "定期存款","FD");
    /**
     * 代码
     */
    private final String code;
    
    /**
     * 描述
     */
    private final String description;

    /**
     * 简写名称
     */
    private final String shortName;

    
    RepoRemarkTypeEnum(String code, String description, String shortName) {
        this.code = code;
        this.description = description;
        this.shortName = shortName;
    }
    
    /**
     * 根据代码获取枚举值
     *
     * @param code 代码
     * @return 枚举值
     */
    public static RepoRemarkTypeEnum getByCode(String code) {
        for (RepoRemarkTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
} 