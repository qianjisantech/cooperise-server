package com.dcp.websocket;

import com.dcp.entity.TopNotification;
import com.dcp.service.ITopNotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 顶部通知WebSocket服务
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Component
@ServerEndpoint("/ws/top-notification")
public class TopNotificationWebSocket {

    /**
     * 静态变量，用于存储在线的客户端
     */
    private static final CopyOnWriteArraySet<TopNotificationWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 使用静态变量存储service，因为@ServerEndpoint不支持依赖注入
     */
    private static ITopNotificationService topNotificationService;

    private static ObjectMapper objectMapper;

    @Autowired
    public void setTopNotificationService(ITopNotificationService topNotificationService) {
        TopNotificationWebSocket.topNotificationService = topNotificationService;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        TopNotificationWebSocket.objectMapper = objectMapper;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        log.info("[WebSocket] 新客户端连接，当前在线人数: {}", webSocketSet.size());

        // 连接建立后立即推送当前已发布的通知
        try {
            TopNotification publishedNotification = topNotificationService.getPublished();
            if (publishedNotification != null) {
                sendMessage(objectMapper.writeValueAsString(publishedNotification));
            } else {
                sendMessage("{}");
            }
        } catch (Exception e) {
            log.error("[WebSocket] 推送初始通知失败", e);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        log.info("[WebSocket] 客户端断开连接，当前在线人数: {}", webSocketSet.size());
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("[WebSocket] 收到客户端消息: {}", message);
        // 可以根据客户端消息做处理，比如刷新通知
        if ("refresh".equals(message)) {
            try {
                TopNotification publishedNotification = topNotificationService.getPublished();
                if (publishedNotification != null) {
                    sendMessage(objectMapper.writeValueAsString(publishedNotification));
                } else {
                    sendMessage("{}");
                }
            } catch (Exception e) {
                log.error("[WebSocket] 刷新通知失败", e);
            }
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("[WebSocket] 发生错误", error);
    }

    /**
     * 发送消息
     */
    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("[WebSocket] 发送消息失败", e);
        }
    }

    /**
     * 群发消息
     */
    public static void broadcastMessage(String message) {
        log.info("[WebSocket] 开始群发消息，在线人数: {}", webSocketSet.size());
        for (TopNotificationWebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (Exception e) {
                log.error("[WebSocket] 群发消息失败", e);
            }
        }
    }

    /**
     * 广播已发布的通知
     */
    public static void broadcastPublishedNotification() {
        try {
            TopNotification publishedNotification = topNotificationService.getPublished();
            if (publishedNotification != null) {
                String message = objectMapper.writeValueAsString(publishedNotification);
                broadcastMessage(message);
                log.info("[WebSocket] 广播已发布通知: {}", publishedNotification.getTitle());
            } else {
                broadcastMessage("{}");
                log.info("[WebSocket] 广播空通知（没有已发布的通知）");
            }
        } catch (Exception e) {
            log.error("[WebSocket] 广播已发布通知失败", e);
        }
    }
}
