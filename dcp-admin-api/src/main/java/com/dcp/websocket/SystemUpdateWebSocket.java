package com.dcp.websocket;

import com.dcp.common.context.UserContextHolder;
import com.dcp.common.vo.UpdateInfoVO;
import com.dcp.entity.Announcement;
import com.dcp.entity.AnnouncementUser;
import com.dcp.service.IAnnouncementService;
import com.dcp.service.IAnnouncementUserRelationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

/**
 * 系统更新 WebSocket 控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class SystemUpdateWebSocket {

    private final IAnnouncementService announcementService;
    private final IAnnouncementUserRelationService relationService;

    /**
     * 处理客户端发送的更新检查请求
     * 客户端发送到：/app/system/check-update
     * 服务端发送到：/queue/system/update（点对点消息）
     *
     * @param headerAccessor 消息头访问器
     * @return 更新信息
     */
    @MessageMapping("/system/check-update")
    @SendToUser("/queue/system/update")
    public UpdateInfoVO checkUpdate(SimpMessageHeaderAccessor headerAccessor) {
        // 获取当前用户ID
        Long userId = getCurrentUserId(headerAccessor);
        log.debug("收到系统更新检查请求，用户ID: {}", userId);

        UpdateInfoVO updateInfo = new UpdateInfoVO();

        if (userId == null) {
            log.warn("无法获取当前用户ID，返回无更新");
            updateInfo.setHasUpdate(true); // true 表示无需更新
            updateInfo.setCurrentVersion("1.0.0");
            updateInfo.setLatestVersion("1.0.0");
            updateInfo.setMessage("当前已是最新版本");
            return updateInfo;
        }

        try {
            // 1. 查询用户最新的公告关联记录，获取用户当前版本
            AnnouncementUser latestRelation = relationService.getLatestByUserId(userId);
            String currentVersion = "1.0.0"; // 默认版本
            if (latestRelation != null && latestRelation.getLatestVersion() != null) {
                // 如果用户有关联记录，使用其最新版本作为当前版本
                currentVersion = latestRelation.getCurrentVersion();
            }

            // 2. 查询最新的未查看系统更新公告
            Announcement latestAnnouncement = announcementService.getLatestUnreadUpdateAnnouncement(userId);

            if (latestAnnouncement != null) {
                // 有未查看的系统更新公告
                // 从公告标题中提取版本号，格式如 "v1.1.0 系统更新"


                updateInfo.setHasUpdate(false); // false 表示用户还未更新到这个版本
                updateInfo.setCurrentVersion(currentVersion);
                assert latestRelation != null;
                updateInfo.setLatestVersion(currentVersion);
                updateInfo.setMessage(latestAnnouncement.getTitle());
                updateInfo.setAnnouncementId(latestAnnouncement.getId());
                updateInfo.setAnnouncementTitle(latestAnnouncement.getTitle());
                updateInfo.setAnnouncementContent(latestAnnouncement.getContent());

                log.info("发现未读系统更新公告: userId={}, announcementId={}, title={}, version: {} -> {}",
                        userId, latestAnnouncement.getId(), latestAnnouncement.getTitle(),
                        currentVersion, currentVersion);
            } else {
                // 没有未查看的系统更新公告
                updateInfo.setHasUpdate(true); // true 表示用户已是最新版本
                updateInfo.setCurrentVersion(currentVersion);
                updateInfo.setLatestVersion(currentVersion);
                updateInfo.setMessage("当前已是最新版本");

                log.debug("用户无未读系统更新公告: userId={}, currentVersion={}", userId, currentVersion);
            }
        } catch (Exception e) {
            log.error("查询系统更新公告失败: userId={}", userId, e);
            // 发生异常时返回无更新（true 表示已是最新，不需要更新）
            updateInfo.setHasUpdate(true);
            updateInfo.setCurrentVersion("1.0.0");
            updateInfo.setLatestVersion("1.0.0");
            updateInfo.setMessage("检查更新失败，请稍后重试");
        }

        return updateInfo;
    }


    /**
     * 从消息头中获取当前用户ID
     *
     * @param headerAccessor 消息头访问器
     * @return 用户ID
     */
    private Long getCurrentUserId(SimpMessageHeaderAccessor headerAccessor) {
        try {
            // 尝试从 UserContextHolder 获取用户ID
            Long userId = UserContextHolder.getUserId();
            if (userId != null) {
                return userId;
            }
        } catch (Exception e) {
            log.warn("无法从 UserContextHolder 获取用户ID，尝试从消息头获取", e);
        }

        // 尝试从 WebSocket session 属性中获取
        try {
            Object userIdObj = headerAccessor.getSessionAttributes().get("userId");
            if (userIdObj != null) {
                return Long.parseLong(userIdObj.toString());
            }
        } catch (Exception e) {
            log.warn("无法从 session 获取用户ID", e);
        }

        // 如果都获取不到，返回 null 或默认值
        // 在实际应用中，可以在 WebSocket 握手时设置用户ID到 session
        log.warn("无法获取用户ID");
        return null;
    }
}
