package com.qianjisan.config;

import com.qianjisan.interceptor.WebSocketAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket 配置类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 配置消息代理
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 启用简单的消息代理，用于向客户端发送消息
        // 客户端订阅地址前缀：/topic (广播), /queue (点对点)
        config.enableSimpleBroker("/topic", "/queue");

        // 客户端发送消息的目的地前缀
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * 注册 STOMP 端点
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册 WebSocket 端点，客户端通过此端点连接
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // 允许跨域
                .addInterceptors(new WebSocketAuthInterceptor()) // 添加认证拦截器
                .withSockJS(); // 启用 SockJS 回退选项
    }

    /**
     * 注入ServerEndpointExporter
     * 用于支持 @ServerEndpoint 注解的 WebSocket 端点（如 TopNotificationWebSocket）
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
