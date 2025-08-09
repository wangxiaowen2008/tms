package com.paob.tms.enums;

import lombok.Getter;

/**
 * 制证状态枚举
 */
@Getter
public enum JournalStatusEnum {
    
    UNVOUCHERED("0", "未制证", VoucherStatusEnum.UNVOUCHERED, DataStatusEnum.INITIAL),
    PENDING_REVIEW("1", "待复核", VoucherStatusEnum.PENDING_REVIEW, DataStatusEnum.PENDING_REVIEW),
    REVIEW_PASSED_PENDING_UPLOAD("2", "复核通过 & 待上传", VoucherStatusEnum.PENDING_REVIEW, DataStatusEnum.REVIEW_PASSED),
    REVIEW_PASSED_UPLOAD_FAILED("3", "复核通过 & 上传失败", VoucherStatusEnum.UPLOAD_FAILED, DataStatusEnum.REVIEW_PASSED),
    REVIEW_PASSED_UPLOAD_SUCCESS("4", "复核通过 & 上传成功", VoucherStatusEnum.VOUCHERED, DataStatusEnum.REVIEW_PASSED),
    REVIEW_REJECTED("5", "复核不通过", VoucherStatusEnum.UNVOUCHERED, DataStatusEnum.REVIEW_REJECTED);
    
    private final String code;
    private final String description;
    private final VoucherStatusEnum voucherStatus;
    private final DataStatusEnum dataStatus;

    JournalStatusEnum(String code, String description, VoucherStatusEnum voucherStatus, DataStatusEnum dataStatus) {
        this.code = code;
        this.description = description;
        this.voucherStatus = voucherStatus;
        this.dataStatus = dataStatus;
    }
    
    /**
     * 根据编码获取枚举
     *
     * @param code 编码
     * @return 枚举对象
     */
    public static JournalStatusEnum getByCode(String code) {
        for (JournalStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
    
    /**
     * 根据凭证状态和数据状态获取制证状态
     *
     * @param voucherStatus 凭证状态
     * @param dataStatus 数据状态
     * @return 制证状态
     */
    public static JournalStatusEnum getByStatus(VoucherStatusEnum voucherStatus, DataStatusEnum dataStatus) {
        for (JournalStatusEnum status : values()) {
            if (status.getVoucherStatus() == voucherStatus && status.getDataStatus() == dataStatus) {
                return status;
            }
        }
        return null;
    }
} 