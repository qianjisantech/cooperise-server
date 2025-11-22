package com.dcp.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 限流配置属性
 *
 * @author Diego
 * @since 2024-11-21
 */
@Data
@Component
@ConfigurationProperties(prefix = "dcp.rate-limit")
public class RateLimitProperties {

    /**
     * 是否启用限流功能
     */
    private Boolean enabled = true;

    /**
     * 默认限流配置
     */
    private LimitConfig defaultConfig = new LimitConfig(60, 100, "访问过于频繁，请稍后再试");

    /**
     * IP限流配置
     */
    private LimitConfig ip = new LimitConfig(60, 30, "您的访问过于频繁，请稍后再试");

    /**
     * 用户限流配置
     */
    private LimitConfig user = new LimitConfig(60, 50, "您的操作过于频繁，请稍后再试");

    /**
     * 特殊接口限流配置
     */
    private Map<String, LimitConfig> special = new HashMap<>();

    /**
     * 限流配置内部类
     */
    @Data
    public static class LimitConfig {
        /**
         * 时间窗口（秒）
         */
        private Integer time;

        /**
         * 最大访问次数
         */
        private Integer count;

        /**
         * 提示消息
         */
        private String message;

        public LimitConfig() {
        }

        public LimitConfig(Integer time, Integer count, String message) {
            this.time = time;
            this.count = count;
            this.message = message;
        }
    }

    /**
     * 获取特殊配置
     */
    public LimitConfig getSpecialConfig(String key) {
        return special.get(key);
    }
}
