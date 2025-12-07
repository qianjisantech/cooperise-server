package com.qianjisan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
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
@ComponentScan(basePackages = {
    "com.qianjisan",      // 当前模块及 console、core、system、issue 等模块
    "qianjisan"          // auth、view、cs 等模块
})
public class DiegoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiegoServerApplication.class, args);
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
    }
}
