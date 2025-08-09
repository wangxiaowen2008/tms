package com.paob.tms.utils;

/**
 * 大小写不敏感的字符串替换工具类
 * 提供忽略大小写的字符串替换功能
 */
public class CaseInsensitiveReplace {
    /**
     * 执行大小写不敏感的字符串替换
     * 将原始字符串中所有匹配目标字符串（忽略大小写）的部分替换为指定的替换字符串
     * 保持原始字符串中匹配部分的大小写不变
     *
     * @param originalStr 原始字符串
     * @param target 要替换的目标字符串
     * @param replacement 替换后的字符串
     * @return 替换后的新字符串
     * 
     * 示例：
     * originalStr = "Hello World! hello JAVA!"
     * target = "WORLD"
     * replacement = "hi"
     * 结果 = "Hello hi! hello JAVA!"
     */
    public static String replaceIgnoreCase(String originalStr, String target, String replacement) {
        // 转换为临时的小写版本用于定位索引位置
        String lowerOriginal = originalStr.toLowerCase();
        String lowerTarget = target.toLowerCase();
        StringBuilder result = new StringBuilder();
        // 存储结果的地方
        int index = 0;
        while (index < originalStr.length()) {
            int tempIndex = lowerOriginal.indexOf(lowerTarget, index);
            if (tempIndex == -1) {
                result.append(originalStr.substring(index));
                break;
            } else {
                result.append(originalStr.substring(index, tempIndex)).append(replacement);
                index = tempIndex + target.length();
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String oriStr = "Hello World! hello JAVA!";
        System.out.println(oriStr);
        String res = replaceIgnoreCase(oriStr, "WORLD", "hi");
        System.out.println(res);
    }
}
