package com.qianjisan.interceptor;

import com.qianjisan.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * WebSocket 握手拦截器
 * 用于在 WebSocket 连接握手时从 token 中提取用户信息
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
public class WebSocketAuthInterceptor implements HandshakeInterceptor {

    /**
     * 握手前拦截
     * 从请求中提取 token 并解析用户信息，存储到 WebSocket session attributes 中
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        try {
            String token = null;

            // 1. 尝试从请求参数中获取 token（支持 ?token=xxx）
            if (request instanceof ServletServerHttpRequest) {
                ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                token = servletRequest.getServletRequest().getParameter("token");

                // 2. 如果参数中没有，尝试从 Authorization header 中获取
                if (!StringUtils.hasText(token)) {
                    String authHeader = servletRequest.getServletRequest().getHeader("Authorization");
                    if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
                        token = authHeader.substring(7);
                    }
                }
            }

            // 3. 如果获取到 token，解析并存储用户信息
            if (StringUtils.hasText(token)) {
                Long userId = JwtUtil.getUserId(token);
                String username = JwtUtil.getUsername(token);

                if (userId != null) {
                    attributes.put("userId", userId);
                    attributes.put("username", username);
                    attributes.put("token", token);

                    log.info("WebSocket 握手成功，用户ID: {}, 用户名: {}", userId, username);
                    return true;
                }
            }

            log.warn("WebSocket 握手失败：无法从 token 中解析用户信息");
            // 即使没有用户信息，也允许连接（根据业务需求可以改为 return false）
            return true;

        } catch (Exception e) {
            log.error("WebSocket 握手异常", e);
            // 发生异常时允许连接，但不设置用户信息
            return true;
        }
    }

    /**
     * 握手后回调
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                              WebSocketHandler wsHandler, Exception exception) {
        if (exception != null) {
            log.error("WebSocket 握手后处理异常", exception);
        }
    }
}
