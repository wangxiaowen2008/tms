package com.paob.tms.enums;

import lombok.Getter;

/**
 * 交易类型枚举
 */
@Getter
public enum TradeTypeEnum {
    
    PAOB_BUY("PAOB Buy", "PAOB买入"),
    PAOB_SELL("PAOB Sell", "PAOB卖出"),
    PAOB_BORROW("PAOB Borrow", "PAOB借入"),
    PAOB_LEND("PAOB Lend", "PAOB借出");
    
    /**
     * 代码
     */
    private final String code;
    
    /**
     * 描述
     */
    private final String description;
    
    TradeTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 根据代码获取枚举值
     *
     * @param code 代码
     * @return 枚举值
     */
    public static TradeTypeEnum getByCode(String code) {
        for (TradeTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
} 