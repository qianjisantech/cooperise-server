package com.dcp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * DCP项目管理系统 - 后端服务启动类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@SpringBootApplication
@EnableAsync
@EnableScheduling
public class DcpAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(DcpAdminApplication.class, args);

        // 使用彩色日志输出不同级别的信息
        log.info("========================================");
        log.info("DCP Admin Application Started Successfully!");
        log.info("========================================");
        log.info("API文档地址: http://localhost:80/doc.html");
        log.info("Druid监控: http://localhost:80/druid/");
    }
}
