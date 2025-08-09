package com.paob.tms.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class FileUtil {
    
    private static final String UPLOAD_DIR = "upload";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static String saveFile(MultipartFile file, String subDir) throws IOException {
        // 创建上传目录
        String dateDir = LocalDate.now().format(DATE_FORMATTER);
        String uploadPath = UPLOAD_DIR + File.separator + subDir + File.separator + dateDir;
        Path path = Paths.get(uploadPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        // 生成文件名
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + extension;

        // 保存文件
        Path filePath = path.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        return uploadPath + File.separator + fileName;
    }
} 