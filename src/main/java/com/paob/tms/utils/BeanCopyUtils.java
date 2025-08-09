package com.paob.tms.utils;

import org.springframework.beans.BeanUtils;

public class BeanCopyUtils {
    
    private BeanCopyUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            T target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Bean copy failed", e);
        }
    }
} 