package com.paob.tms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * TMS系统启动类
 */
@SpringBootApplication
@MapperScan("com.paob.tms.mapper")
@EnableScheduling
public class TmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(TmsApplication.class, args);
    }
} 