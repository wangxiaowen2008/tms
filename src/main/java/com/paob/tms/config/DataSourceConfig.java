package com.paob.tms.config;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class DataSourceConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.hikari.pool-name}")
    private String poolName;

    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int maximumPoolSize;

    @Value("${spring.datasource.hikari.minimum-idle}")
    private int minimumIdle;

    @Value("${spring.datasource.hikari.connection-timeout}")
    private long connectionTimeout;

    @Value("${spring.datasource.hikari.validation-timeout}")
    private long validationTimeout;

    @Value("${spring.datasource.hikari.leak-detection-threshold}")
    private long leakDetectionThreshold;

    @Value("${spring.datasource.hikari.max-lifetime}")
    private long maxLifetime;

    @Value("${spring.datasource.hikari.connection-test-query}")
    private String connectionTestQuery;

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        
        try {
            // 基本配置
            dataSource.setDriverClassName(driverClassName);
            dataSource.setJdbcUrl(url);
            dataSource.setUsername(username);
            
            // 从外部文件读取密码
            String password = readPasswordFromFile();
            if (password == null || password.trim().isEmpty()) {
                throw new RuntimeException("数据库密码不能为空");
            }
            dataSource.setPassword(password);
            
            // Hikari连接池配置
            dataSource.setPoolName(poolName);
            dataSource.setMaximumPoolSize(maximumPoolSize);
            dataSource.setMinimumIdle(minimumIdle);
            dataSource.setConnectionTimeout(connectionTimeout);
            dataSource.setValidationTimeout(validationTimeout);
            dataSource.setLeakDetectionThreshold(leakDetectionThreshold);
            dataSource.setMaxLifetime(maxLifetime);
            dataSource.setConnectionTestQuery(connectionTestQuery);
            
            // 性能优化配置
            dataSource.addDataSourceProperty("cachePrepStmts", "true");
            dataSource.addDataSourceProperty("prepStmtCacheSize", "250");
            dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            dataSource.addDataSourceProperty("useServerPrepStmts", "true");
            dataSource.addDataSourceProperty("useLocalSessionState", "true");
            dataSource.addDataSourceProperty("rewriteBatchedStatements", "true");
            dataSource.addDataSourceProperty("cacheResultSetMetadata", "true");
            dataSource.addDataSourceProperty("cacheServerConfiguration", "true");
            dataSource.addDataSourceProperty("elideSetAutoCommits", "true");
            dataSource.addDataSourceProperty("maintainTimeStats", "false");
            
            logger.info("数据源配置成功: url={}, username={}, poolName={}", url, username, poolName);
            
            return dataSource;
        } catch (Exception e) {
            logger.error("数据源配置失败: {}", e.getMessage(), e);
            throw new RuntimeException("数据源配置失败: " + e.getMessage(), e);
        }
    }

    private String readPasswordFromFile() {
        try {
            Properties properties = new Properties();
            properties.load(new ClassPathResource("db.properties").getInputStream());
            String password = properties.getProperty("db.password");
            if (password == null || password.trim().isEmpty()) {
                logger.error("数据库密码配置为空");
                throw new RuntimeException("数据库密码配置为空");
            }
            return password;
        } catch (IOException e) {
            logger.error("读取数据库密码配置文件失败: {}", e.getMessage(), e);
            throw new RuntimeException("读取数据库密码配置文件失败", e);
        }
    }
} 