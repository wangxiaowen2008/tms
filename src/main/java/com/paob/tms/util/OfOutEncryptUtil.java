package com.paob.tms.util;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 完全等价于bash脚本of_out.sh的加密工具类
 * 步骤：
 * 1. 生成16字节（32位16进制）随机密码，写入明文密码文件（Pw_create.txt）
 * 2. 用tar打包数据文件（不压缩，输出为xxx.tar）
 * 3. 用DES3（openssl des3 -salt -kfile）加密tar包，输出为xxx.tar.gz
 * 4. 用RSA公钥加密明文密码，输出为指定的加密密码文件
 * 5. 删除明文密码文件和原始数据文件
 */
public class OfOutEncryptUtil {
    private static final Logger log = LoggerFactory.getLogger(OfOutEncryptUtil.class);

    /**
     * 主流程方法，参数与bash脚本一致
     * @param dirPath 操作目录
     * @param pwdFileName 加密后密码文件名
     * @param dataFileName 需要加密的数据文件名
     * @param publicKeyFilePath RSA公钥文件路径（PEM格式）
     */
    public static void encrypt(String dirPath, String pwdFileName, String dataFileName, String publicKeyFilePath) throws Exception {
        Path dir = Paths.get(dirPath);
        Path dataFile = dir.resolve(dataFileName);
        String dataFileBase = dataFileName.contains(".") ? dataFileName.substring(0, dataFileName.lastIndexOf('.')) : dataFileName;
        Path tarFile = dir.resolve(dataFileBase + ".tar");
        Path encryptedTarFile = dir.resolve(dataFileBase + ".tar.gz");
        Path plainPwdFile = dir.resolve("Pw_create.txt");
        Path encryptedPwdFile = dir.resolve(pwdFileName);

        // 1. 生成16字节（32位16进制）随机密码
        String hexPwd = generateRandomHexPassword(16);
        Files.writeString(plainPwdFile, hexPwd, StandardCharsets.UTF_8);

        // 2. tar打包数据文件（不压缩）
        createTarFile(dataFile, tarFile);

        // 3. DES3加密tar包，输出为tar.gz
        encryptTarWithDES3(tarFile, encryptedTarFile, plainPwdFile);

        // 4. 用RSA公钥加密明文密码
        encryptPasswordWithRSA(plainPwdFile, encryptedPwdFile, publicKeyFilePath);

        // 5. 删除明文密码和原始数据文件
        Files.deleteIfExists(plainPwdFile);
        Files.deleteIfExists(dataFile);
        Files.deleteIfExists(tarFile);
    }

    /**
     * 生成指定字节数的随机密码，并转为16进制字符串
     */
    private static String generateRandomHexPassword(int byteLen) {
        byte[] bytes = new byte[byteLen];
        new SecureRandom().nextBytes(bytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    /**
     * 用tar打包单个文件（不压缩）
     */
    private static void createTarFile(Path sourceFile, Path tarFile) throws IOException {
        try (TarArchiveOutputStream tarOut = new TarArchiveOutputStream(new FileOutputStream(tarFile.toFile()))) {
            TarArchiveEntry entry = new TarArchiveEntry(sourceFile.toFile(), sourceFile.getFileName().toString());
            tarOut.putArchiveEntry(entry);
            Files.copy(sourceFile, tarOut);
            tarOut.closeArchiveEntry();
        }
    }

    /**
     * 用DES3加密tar包，格式与openssl des3 -salt -kfile一致，输出为tar.gz
     */
    private static void encryptTarWithDES3(Path tarFile, Path encryptedFile, Path pwdFile) throws Exception {
        // 生成8字节salt
        byte[] salt = new byte[8];
        new SecureRandom().nextBytes(salt);
        // 读取16字节密码
        String hex = Files.readString(pwdFile).trim();
        byte[] password = hexStringToBytes(hex);
        // 派生密钥和IV（openssl EVP_BytesToKey算法，MD5）
        byte[][] keyAndIv = evpBytesToKey(24, 8, password, salt);
        SecretKey key = new SecretKeySpec(keyAndIv[0], "DESede");
        IvParameterSpec iv = new IvParameterSpec(keyAndIv[1]);
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        try (FileOutputStream fos = new FileOutputStream(encryptedFile.toFile());
             FileInputStream fis = new FileInputStream(tarFile.toFile());
             CipherOutputStream cos = new CipherOutputStream(fos, cipher)) {
            // 写入openssl兼容头
            fos.write("Salted__".getBytes(StandardCharsets.US_ASCII));
            fos.write(salt);
            byte[] buf = new byte[4096];
            int len;
            while ((len = fis.read(buf)) != -1) {
                cos.write(buf, 0, len);
            }
        }
    }

    /**
     * EVP_BytesToKey算法（MD5），与openssl兼容
     */
    private static byte[][] evpBytesToKey(int keyLen, int ivLen, byte[] password, byte[] salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
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
     * 16进制字符串转byte[]
     */
    private static byte[] hexStringToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * 用RSA公钥加密明文密码，输出为指定文件
     */
    private static void encryptPasswordWithRSA(Path plainPwdFile, Path encryptedPwdFile, String publicKeyFilePath) throws Exception {
        // 读取PEM格式公钥
        StringBuilder sb = new StringBuilder();
        for (String line : Files.readAllLines(Paths.get(publicKeyFilePath), StandardCharsets.UTF_8)) {
            line = line.trim();
            if (line.startsWith("-----") || line.isEmpty()) continue;
            sb.append(line);
        }
        byte[] keyBytes = Base64.getDecoder().decode(sb.toString());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] plainPwd = Files.readAllBytes(plainPwdFile);
        byte[] encrypted = cipher.doFinal(plainPwd);
        Files.write(encryptedPwdFile, encrypted);
    }
} 