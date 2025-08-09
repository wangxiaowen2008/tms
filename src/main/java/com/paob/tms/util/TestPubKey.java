package com.paob.tms.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Base64;
import java.util.List;
import java.util.Locale;

public class TestPubKey {
    public static void main(String[] args) throws Exception {
//        if (Security.getProvider("BC") == null) {
//            //Security.addProvider(new BouncyCastleProvider());
//        }
//
//        List<String> lines = Files.readAllLines(Paths.get("D:/work/paob_tms1/src/main/resources/bash/public.key111"));
//        StringBuilder sb = new StringBuilder();
//        for (String line : lines) {
//            line = line.trim();
//            if (line.startsWith("-----") || line.isEmpty()) continue;
//            sb.append(line);
//        }
//        System.out.println("拼接后的Base64字符串：" + sb.toString());
//        byte[] decodedKey = Base64.getDecoder().decode(sb.toString());
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
//        PublicKey publicKey = keyFactory.generatePublic(keySpec);
//        System.out.println("公钥解析成功！");
        System.out.println(convertPeriodName("FEB-39"));
    }
    private static String convertPeriodName(String periodName) {
        if (periodName == null || periodName.isEmpty()) {
            return null;
        }
        try {
            // 将期段名称转换为标准格式，例如 "JAN-23" 变为 "Jan-23"
            String standardizedPeriodName = standardizePeriodName(periodName);

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM-yy", Locale.ENGLISH);
            YearMonth yearMonth = YearMonth.parse(standardizedPeriodName, inputFormatter);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
            return yearMonth.format(outputFormatter);
        } catch (DateTimeParseException e) {
            //log.error("无法解析期段名称格式: {}", periodName, e);
            return periodName; // 如果解析失败，返回原始值或根据需求处理
        }
    }

    // 添加一个辅助方法用于标准化期段名称的大小写
    private static String standardizePeriodName(String periodName) {
        if (periodName == null || periodName.length() < 3) {
            return periodName;
        }
        // 假设格式总是 "MMM-yy" 或类似
        String monthPart = periodName.substring(0, 3);
        String restPart = periodName.substring(3);
        // 将月份部分转换为首字母大写，其余小写
        String standardizedMonthPart = monthPart.substring(0, 1).toUpperCase() + monthPart.substring(1).toLowerCase();
        return standardizedMonthPart + restPart;
    }
} 