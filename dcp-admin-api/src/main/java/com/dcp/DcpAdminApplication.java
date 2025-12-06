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

        // ANSI颜色代码
        String reset = "\033[0m";      // 重置
        String bold = "\033[1m";       // 粗体
        String cyan = "\033[36m";       // 青色（边框）
        String brightBlue = "\033[94m"; // 亮蓝色（标题）
        
        // 使用彩色日志输出不同级别的信息
        System.out.println();
        System.out.println(bold + cyan + "╔═══════════════════════════════════════════════════════════╗" + reset);
        System.out.println(bold + cyan + "║" + reset + "                                                           " + bold + cyan + "║" + reset);
        System.out.println(bold + cyan + "║" + reset + "          " + bold + brightBlue + "   管理员后台管理系统    " + reset + "          " + bold + cyan + "║" + reset);
        System.out.println(bold + cyan + "║" + reset + "                                                           " + bold + cyan + "║" + reset);
        System.out.println(bold + cyan + "╚═══════════════════════════════════════════════════════════╝" + reset);
        System.out.println();
        log.info("========================================");
        log.info("DCP Admin Application Started Successfully!");
        log.info("========================================");
        log.info("API文档地址: http://localhost:80/doc.html");
        log.info("Druid监控: http://localhost:80/druid/");
    }
}
