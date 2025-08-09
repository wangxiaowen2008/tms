package com.paob.tms.util;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Random;

/**
 * 文件加密工具类
 * 实现与of_out.sh相同的功能：
 * 1. 生成随机密码
 * 2. 压缩数据文件
 * 3. 使用DES3加密压缩后的数据
 * 4. 使用RSA公钥加密密码文件
 */
public class FileEncryptionUtil {
    private static final Logger log = LoggerFactory.getLogger(FileEncryptionUtil.class);
    
    /**
     * 加密文件
     * @param path 文件目录
     * @param pwdFileName 密码文件名称
     * @param dataFileName 数据文件名称
     * @param publicKeyPath 公钥文件路径
     */
    public static void encryptFile(String path, String pwdFileName, String dataFileName, String publicKeyPath) {
        try {
            // 切换到指定目录
            Path workingDir = Paths.get(path);
            Files.createDirectories(workingDir);
            
            // 生成随机密码
            String randomPassword = generateRandomPassword();
            String tempPwdFile = "Pw_create.txt";
            Path pwdFilePath = workingDir.resolve(tempPwdFile);
            Files.write(pwdFilePath, randomPassword.getBytes());
            
            // 压缩数据文件
            String dataFileNameWithoutExt = dataFileName.substring(0, dataFileName.lastIndexOf('.'));
            String compressedFileName = dataFileNameWithoutExt + ".tar.gz";
            compressFile(workingDir.resolve(dataFileName), workingDir.resolve(compressedFileName));
            
            // DES3加密压缩文件
            encryptWithDES3(workingDir.resolve(compressedFileName), workingDir.resolve(pwdFilePath));
            
            // RSA加密密码文件
            encryptPasswordWithRSA(pwdFilePath, workingDir.resolve(pwdFileName), publicKeyPath);
            
            // 清理临时文件
            //Files.deleteIfExists(pwdFilePath);
            //Files.deleteIfExists(workingDir.resolve(dataFileName));
            
        } catch (Exception e) {
            log.error("文件加密失败", e);
            throw new RuntimeException("文件加密失败: " + e.getMessage());
        }
    }
    
    /**
     * 生成16字节的随机密码
     */
    private static String generateRandomPassword() {
        byte[] randomBytes = new byte[16];
        new Random().nextBytes(randomBytes);
        return bytesToHex(randomBytes);
    }
    
    /**
     * 将字节数组转换为十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
    
    /**
     * 压缩文件为tar.gz格式
     */
    private static void compressFile(Path sourceFile, Path targetFile) throws IOException {
        try (OutputStream fos = Files.newOutputStream(targetFile);
             GzipCompressorOutputStream gzipOut = new GzipCompressorOutputStream(fos);
             TarArchiveOutputStream tarOut = new TarArchiveOutputStream(gzipOut)) {
            
            // 创建tar条目
            TarArchiveEntry entry = new TarArchiveEntry(sourceFile.toFile(), sourceFile.getFileName().toString());
            tarOut.putArchiveEntry(entry);
            
            // 写入文件内容
            Files.copy(sourceFile, tarOut);
            
            // 关闭当前条目
            tarOut.closeArchiveEntry();
        }
    }
    
    /**
     * 使用openssl兼容方式进行DES3加密（与openssl des3 -salt -kfile一致）
     */
    private static void encryptWithDES3(Path sourceFile, Path pwdFilePath) throws Exception {
        // 生成8字节salt
        byte[] salt = new byte[8];
        new SecureRandom().nextBytes(salt);

        // 读取密码（hex转byte）
        byte[] password = readPasswordFromHexFile(pwdFilePath);

        // EVP_BytesToKey 派生密钥和IV
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[][] keyAndIV = evpBytesToKey(24, 8, md, salt, password);
        SecretKey key = new javax.crypto.spec.SecretKeySpec(keyAndIV[0], "DESede");
        javax.crypto.spec.IvParameterSpec iv = new javax.crypto.spec.IvParameterSpec(keyAndIV[1]);

        // 写入文件头并加密
        Path outFile = sourceFile; // 直接覆盖原文件
        Path tempFile = Files.createTempFile("encrypted_", ".tmp");
        try (FileOutputStream fos = new FileOutputStream(tempFile.toFile());
             FileInputStream fis = new FileInputStream(sourceFile.toFile())) {
            // 写入openssl兼容头
            fos.write("Salted__".getBytes(java.nio.charset.StandardCharsets.US_ASCII));
            fos.write(salt);

            // 加密流
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            try (CipherOutputStream cos = new CipherOutputStream(fos, cipher)) {
                byte[] buffer = new byte[4096];
                int n;
                while ((n = fis.read(buffer)) != -1) {
                    cos.write(buffer, 0, n);
                }
            }
        }
        // 用加密后的文件替换原文件
        Files.move(tempFile, outFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * 读取hex格式密码文件内容转为byte[]
     */
    private static byte[] readPasswordFromHexFile(Path pwdFilePath) throws IOException {
        String hex = Files.readString(pwdFilePath).trim();
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                                 + Character.digit(hex.charAt(i+1), 16));
        }
        return data;
    }

    /**
     * openssl EVP_BytesToKey 派生算法（MD5）
     */
    private static byte[][] evpBytesToKey(int keyLen, int ivLen, MessageDigest md, byte[] salt, byte[] password) {
        byte[] key = new byte[keyLen];
        byte[] iv = new byte[ivLen];
        byte[] prev = new byte[0];
        int offset = 0;
        int totalLen = keyLen + ivLen;
        byte[] result = new byte[0];
        while (offset < totalLen) {
            md.reset();
            md.update(prev);
            md.update(password);
            if (salt != null) md.update(salt, 0, 8);
            prev = md.digest();
            int l = Math.min(prev.length, totalLen - offset);
            byte[] newResult = new byte[result.length + l];
            System.arraycopy(result, 0, newResult, 0, result.length);
            System.arraycopy(prev, 0, newResult, result.length, l);
            result = newResult;
            offset += l;
        }
        System.arraycopy(result, 0, key, 0, keyLen);
        System.arraycopy(result, keyLen, iv, 0, ivLen);
        return new byte[][]{key, iv};
    }
    
    /**
     * 使用RSA公钥加密密码文件，支持PEM格式公钥（带头尾）
     */
    private static void encryptPasswordWithRSA(Path sourceFile, Path targetFile, String publicKeyPath) throws Exception {
        // 读取公钥（更健壮的方式，过滤头尾和空行）
        java.util.List<String> lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(publicKeyPath), java.nio.charset.StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            log.info("读取到的公钥文件行: [{}]", line);
            line = line.trim();
            if (line.startsWith("-----") || line.isEmpty()) continue;
            sb.append(line);
        }
        log.info("拼接后的Base64内容: [{}]", sb.toString());
        byte[] decodedKey = java.util.Base64.getDecoder().decode(sb.toString());
        java.security.spec.X509EncodedKeySpec keySpec = new java.security.spec.X509EncodedKeySpec(decodedKey);
        java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance("RSA");
        java.security.PublicKey publicKey = keyFactory.generatePublic(keySpec);

        // 初始化加密器
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("RSA");
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, publicKey);

        // 读取密码文件内容
        byte[] passwordBytes = java.nio.file.Files.readAllBytes(sourceFile);

        // 加密并写入目标文件
        byte[] encryptedBytes = cipher.doFinal(passwordBytes);
        java.nio.file.Files.write(targetFile, encryptedBytes);
    }
} 