package com.paob.tms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * RSA密钥对生成工具
 */
public class RSAKeyGenerator {
    private static final Logger log = LoggerFactory.getLogger(RSAKeyGenerator.class);
    
    /**
     * 生成RSA密钥对并保存到文件
     * @param publicKeyPath 公钥文件路径
     * @param privateKeyPath 私钥文件路径
     */
    public static void generateKeyPair(String publicKeyPath, String privateKeyPath) {
        try {
            // 创建密钥对生成器
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); // 使用2048位密钥长度
            
            // 生成密钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            
            // 将公钥和私钥转换为Base64编码的字符串
            String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            
            // 确保目录存在
            Path publicKeyDir = Paths.get(publicKeyPath).getParent();
            Path privateKeyDir = Paths.get(privateKeyPath).getParent();
            if (publicKeyDir != null) {
                Files.createDirectories(publicKeyDir);
            }
            if (privateKeyDir != null) {
                Files.createDirectories(privateKeyDir);
            }
            
            // 保存公钥到文件
            try (FileOutputStream fos = new FileOutputStream(publicKeyPath)) {
                fos.write(publicKeyBase64.getBytes());
            }
            
            // 保存私钥到文件
            try (FileOutputStream fos = new FileOutputStream(privateKeyPath)) {
                fos.write(privateKeyBase64.getBytes());
            }
            
            log.info("RSA密钥对生成成功");
            log.info("公钥文件路径: {}", publicKeyPath);
            log.info("私钥文件路径: {}", privateKeyPath);
            
        } catch (Exception e) {
            log.error("生成RSA密钥对失败", e);
            throw new RuntimeException("生成RSA密钥对失败: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        // 示例：生成密钥对
        String publicKeyPath = "D:/work/paob_tms1/keys/public.key";
        String privateKeyPath = "D:/work/paob_tms1/keys/private.key";
        generateKeyPair(publicKeyPath, privateKeyPath);
    }
} 