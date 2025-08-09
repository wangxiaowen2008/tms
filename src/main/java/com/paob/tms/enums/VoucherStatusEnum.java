package com.paob.tms.enums;

import lombok.Getter;

/**
 * 凭证状态枚举
 */
@Getter
public enum VoucherStatusEnum {
    
    UNVOUCHERED("0", "未制证"),
    PENDING_REVIEW("1", "待复核/复核通过"),
    VOUCHERED("2", "已制证"),
    UPLOAD_FAILED("3", "上传失败");
    
    private final String code;
    private final String description;
    
    VoucherStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 根据编码获取枚举
     *
     * @param code 编码
     * @return 枚举对象
     */
    public static VoucherStatusEnum getByCode(String code) {
        for (VoucherStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
} 