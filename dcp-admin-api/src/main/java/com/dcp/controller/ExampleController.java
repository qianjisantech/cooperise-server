package com.dcp.controller;

import com.dcp.common.annotation.RateLimit;
import com.dcp.common.enums.LimitType;
import com.dcp.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 限流示例Controller
 *
 * @author Diego
 * @since 2024-11-21
 */
@Slf4j
@Tag(name = "限流示例", description = "限流功能演示接口")
@RestController
@RequestMapping("/api/example")
public class ExampleController {

    /**
     * 全局限流示例
     * 60秒内最多访问10次
     */
    @Operation(summary = "全局限流示例", description = "60秒内最多访问10次")
    @GetMapping("/global")
    @RateLimit(time = 60, count = 10, limitType = LimitType.DEFAULT)
    public Result<String> globalLimit() {
        log.info("全局限流接口被调用");
        return Result.success("全局限流测试成功");
    }

    /**
     * IP限流示例
     * 每个IP 60秒内最多访问5次
     */
    @Operation(summary = "IP限流示例", description = "每个IP 60秒内最多访问5次")
    @GetMapping("/ip")
    @RateLimit(time = 60, count = 5, limitType = LimitType.IP, message = "您的访问过于频繁,请稍后再试")
    public Result<String> ipLimit() {
        log.info("IP限流接口被调用");
        return Result.success("IP限流测试成功");
    }

    /**
     * 用户限流示例
     * 每个用户 60秒内最多访问3次
     */
    @Operation(summary = "用户限流示例", description = "每个用户 60秒内最多访问3次")
    @PostMapping("/user")
    @RateLimit(time = 60, count = 3, limitType = LimitType.USER, message = "您的操作过于频繁,请稍后再试")
    public Result<String> userLimit() {
        log.info("用户限流接口被调用");
        return Result.success("用户限流测试成功");
    }

    /**
     * 登录限流示例（使用配置文件预设）
     * 配置来源：dcp.rate-limit.special.login
     */
    @Operation(summary = "登录限流示例", description = "使用配置文件预设值，每个IP 5分钟内最多尝试5次")
    @PostMapping("/login")
    @RateLimit(configKey = "login", limitType = LimitType.IP)
    public Result<String> loginLimit() {
        log.info("登录限流接口被调用");
        return Result.success("登录成功");
    }

    /**
     * 发送验证码限流示例（使用配置文件预设）
     * 配置来源：dcp.rate-limit.special.send-code
     */
    @Operation(summary = "发送验证码限流示例", description = "使用配置文件预设值，每个IP每天最多发送10次")
    @PostMapping("/send-code")
    @RateLimit(configKey = "send-code", limitType = LimitType.IP)
    public Result<String> sendCodeLimit() {
        log.info("发送验证码接口被调用");
        return Result.success("验证码已发送");
    }
}
