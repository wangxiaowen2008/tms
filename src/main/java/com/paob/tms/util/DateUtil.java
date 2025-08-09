package com.paob.tms.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具类
 */
public class DateUtil {
    
    /**
     * 默认日期格式：yyyy-MM-dd
     */
    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * 默认时间格式：HH:mm:ss
     */
    public static final DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    /**
     * 默认日期时间格式：yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 文件名日期时间格式：yyyyMMddHHmmss
     */
    public static final DateTimeFormatter FILE_NAME_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 获取当前时间戳格式的文件名
     *
     * @param prefix 文件名前缀
     * @param suffix 文件名后缀
     * @return 格式化的文件名
     */
    public static String getTimestampFileName(String prefix, String suffix) {
        return prefix + LocalDateTime.now().format(TIMESTAMP_FORMATTER) + suffix;
    }

    /**
     * 格式化日期
     *
     * @param date 日期
     * @return 格式化后的日期字符串
     */
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(DEFAULT_DATE_FORMATTER);
    }

    /**
     * 格式化日期时间
     *
     * @param date 日期时间
     * @return 格式化后的日期字符串
     */
    public static String formatDate(LocalDateTime date) {
        if (date == null) {
            return "";
        }
        return date.format(DEFAULT_DATE_FORMATTER);
    }

    /**
     * 格式化时间
     *
     * @param time 时间
     * @return 格式化后的时间字符串
     */
    public static String formatTime(LocalTime time) {
        if (time == null) {
            return "";
        }
        return time.format(DEFAULT_TIME_FORMATTER);
    }

    /**
     * 格式化时间
     *
     * @param date 日期时间
     * @return 格式化后的时间字符串
     */
    public static String formatTime(LocalDateTime date) {
        if (date == null) {
            return "";
        }
        return date.format(DEFAULT_TIME_FORMATTER);
    }

    /**
     * 格式化日期时间
     *
     * @param date 日期时间
     * @return 格式化后的日期时间字符串
     */
    public static String formatDateTime(LocalDateTime date) {
        if (date == null) {
            return "";
        }
        return date.format(DEFAULT_DATETIME_FORMATTER);
    }
} 