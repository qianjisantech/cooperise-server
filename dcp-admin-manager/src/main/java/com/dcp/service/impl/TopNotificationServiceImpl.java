package com.dcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.common.context.UserContext;
import com.dcp.common.context.UserContextHolder;
import com.dcp.entity.TopNotification;
import com.dcp.rbac.entity.SysUser;
import com.dcp.mapper.TopNotificationMapper;
import com.dcp.service.ITopNotificationService;
import com.dcp.rbac.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 顶部通知Service实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TopNotificationServiceImpl extends ServiceImpl<TopNotificationMapper, TopNotification> implements ITopNotificationService {

    private final ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publish(Long id) {
        log.info("[TopNotificationService] 发布顶部通知，ID: {}", id);

        // 获取要发布的记录
        TopNotification notification = this.getById(id);
        if (notification == null) {
            throw new RuntimeException("顶部通知不存在");
        }

        // 获取当前登录用户信息
        UserContext userContext = UserContextHolder.getUser();
        if (userContext == null || userContext.getUserId() == null) {
            throw new RuntimeException("未登录或登录已过期");
        }
        Long userId = userContext.getUserId();
        SysUser currentUser = sysUserService.getById(userId);
        if (currentUser == null) {
            throw new RuntimeException("获取当前用户信息失败");
        }

        // 将所有已发布的记录设置为草稿
        LambdaUpdateWrapper<TopNotification> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TopNotification::getStatus, 1)
                .set(TopNotification::getStatus, 0);
        this.update(updateWrapper);

        log.info("[TopNotificationService] 已将其他已发布的顶部通知设置为草稿");

        // 发布当前记录
        notification.setStatus(1);
        notification.setPublishTime(LocalDateTime.now());
        notification.setPublisherId(userId);
        notification.setPublisherCode(currentUser.getUserCode());
        notification.setPublisherName(currentUser.getUsername());
        this.updateById(notification);

        log.info("[TopNotificationService] 顶部通知发布成功，ID: {}, 发布人: {}", id, currentUser.getUsername());

        // 通过WebSocket广播最新的已发布通知
        try {
            // 使用反射调用WebSocket的静态方法，避免循环依赖
            Class<?> webSocketClass = Class.forName("com.dcp.websocket.TopNotificationWebSocket");
            java.lang.reflect.Method method = webSocketClass.getMethod("broadcastPublishedNotification");
            method.invoke(null);
            log.info("[TopNotificationService] WebSocket广播通知成功");
        } catch (Exception e) {
            log.error("[TopNotificationService] WebSocket广播通知失败", e);
        }
    }

    @Override
    public TopNotification getPublished() {
        LocalDateTime now = LocalDateTime.now();

        LambdaQueryWrapper<TopNotification> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TopNotification::getStatus, 1)
                // 时间范围判断：
                // 1. 如果 startTime 和 endTime 都为 null，则不做时间限制
                // 2. 如果 startTime 不为 null，则当前时间必须 >= startTime
                // 3. 如果 endTime 不为 null，则当前时间必须 <= endTime
                .and(wrapper -> wrapper
                        // 情况1: startTime 和 endTime 都为 null（永久有效）
                        .nested(w -> w.isNull(TopNotification::getStartTime)
                                .isNull(TopNotification::getEndTime))
                        // 情况2: 只设置了 startTime，没有设置 endTime
                        .or(w -> w.isNotNull(TopNotification::getStartTime)
                                .le(TopNotification::getStartTime, now)
                                .isNull(TopNotification::getEndTime))
                        // 情况3: 只设置了 endTime，没有设置 startTime
                        .or(w -> w.isNull(TopNotification::getStartTime)
                                .isNotNull(TopNotification::getEndTime)
                                .ge(TopNotification::getEndTime, now))
                        // 情况4: startTime 和 endTime 都设置了
                        .or(w -> w.isNotNull(TopNotification::getStartTime)
                                .le(TopNotification::getStartTime, now)
                                .isNotNull(TopNotification::getEndTime)
                                .ge(TopNotification::getEndTime, now))
                )
                .orderByDesc(TopNotification::getPublishTime)
                .last("LIMIT 1");

        TopNotification notification = this.getOne(queryWrapper);

        if (notification != null) {
            log.info("[TopNotificationService] 获取到有效的已发布通知: {}, 生效时间: {} - {}",
                    notification.getTitle(),
                    notification.getStartTime(),
                    notification.getEndTime());
        } else {
            log.info("[TopNotificationService] 当前没有在有效时间范围内的已发布通知");
        }

        return notification;
    }

    @Override
    public Page<TopNotification> pageList(Long current, Long pageSize, Integer status, Integer type) {
        Page<TopNotification> page = new Page<>(current, pageSize);
        LambdaQueryWrapper<TopNotification> queryWrapper = new LambdaQueryWrapper<>();

        if (status != null) {
            queryWrapper.eq(TopNotification::getStatus, status);
        }

        if (type != null) {
            queryWrapper.eq(TopNotification::getType, type);
        }

        queryWrapper.orderByDesc(TopNotification::getCreateTime);

        return this.page(page, queryWrapper);
    }
}
