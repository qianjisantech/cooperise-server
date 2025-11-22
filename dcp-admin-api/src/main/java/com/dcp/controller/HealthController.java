package com.dcp.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 健康检查控制器
 * 用于 Kubernetes 健康探针检查
 */
@Hidden // 不在 Swagger 文档中显示
@RestController
@RequestMapping("/health")
public class HealthController {

    /**
     * 健康检查接口
     * 用于 Kubernetes Liveness 和 Readiness 探针
     */
    @GetMapping
    public Map<String, Object> health() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "UP");
        result.put("timestamp", System.currentTimeMillis());
        return result;
    }

    /**
     * 就绪检查接口
     * 可以添加额外的检查逻辑（如数据库连接等）
     */
    @GetMapping("/ready")
    public Map<String, Object> ready() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "READY");
        result.put("timestamp", System.currentTimeMillis());
        return result;
    }

    /**
     * 存活检查接口
     */
    @GetMapping("/live")
    public Map<String, Object> live() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "ALIVE");
        result.put("timestamp", System.currentTimeMillis());
        return result;
    }
}
