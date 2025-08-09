package com.paob.tms.enums;

import lombok.Getter;

/**
 * 数据状态枚举
 */
@Getter
public enum DataStatusEnum {
    
    INITIAL("0", "初始状态"),
    SUBMITTED("1", "已提交"),
    PENDING_REVIEW("2", "复核通过"),
    REVIEW_PASSED("3", "复核不通过"),
    REVIEW_REJECTED("4", "复核不通过");

    private final String code;
    private final String description;
    
    DataStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 根据编码获取枚举
     *
     * @param code 编码
     * @return 枚举对象
     */
    public static DataStatusEnum getByCode(String code) {
        for (DataStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
} 