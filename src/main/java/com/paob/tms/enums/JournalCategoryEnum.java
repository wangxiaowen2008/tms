package com.paob.tms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 凭证类别枚举
 */
@Getter
@AllArgsConstructor
public enum JournalCategoryEnum {
    
    TREASURY("1", "司库凭证"),
    MANUAL("2", "手工凭证"),
    IMPORT("3", "导入凭证"),
    OTHER("4", "其它凭证");

    private final String code;
    private final String description;

    /**
     * 根据code获取枚举
     */
    public static JournalCategoryEnum getByCode(String code) {
        for (JournalCategoryEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 根据code获取描述
     */
    public static String getDescriptionByCode(String code) {
        JournalCategoryEnum category = getByCode(code);
        return category != null ? category.getDescription() : "";
    }
} 