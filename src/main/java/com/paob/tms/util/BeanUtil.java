package com.paob.tms.util;

import org.springframework.beans.BeanUtils;

/**
 * Bean工具类
 */
public class BeanUtil {
    
    /**
     * 复制对象属性
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }
    
    /**
     * 复制对象属性，忽略指定属性
     *
     * @param source 源对象
     * @param target 目标对象
     * @param ignoreProperties 忽略的属性
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        BeanUtils.copyProperties(source, target, ignoreProperties);
    }
} 