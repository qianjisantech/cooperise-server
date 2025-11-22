package com.dcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.common.dto.NotificationQueryDTO;
import com.dcp.common.util.BeanConverter;
import com.dcp.common.vo.NotificationVO;
import com.dcp.entity.SysNotification;
import com.dcp.mapper.NotificationMapper;
import com.dcp.service.INotificationService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Notification服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, SysNotification> implements INotificationService {

    @Override
    public NotificationVO getNotificationById(Long id) {
        SysNotification sysNotification = getById(id);
        return BeanConverter.convert(sysNotification, NotificationVO::new);
    }

    @Override
    public List<NotificationVO> getUnreadByUserId(Long userId) {
        LambdaQueryWrapper<SysNotification> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysNotification::getUserId, userId)
                .eq(SysNotification::getIsRead, 0)
                .orderByDesc(SysNotification::getCreateTime);
        List<SysNotification> list = list(queryWrapper);
        return BeanConverter.convertList(list, NotificationVO::new);
    }

    @Override
    public Long getUnreadCount(Long userId) {
        LambdaQueryWrapper<SysNotification> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysNotification::getUserId, userId)
                .eq(SysNotification::getIsRead, 0);
        return count(queryWrapper);
    }

    @Override
    public boolean markAsRead(Long id) {
        SysNotification sysNotification = getById(id);
        if (sysNotification != null && sysNotification.getIsRead() == 0) {
            sysNotification.setIsRead(1);
            sysNotification.setReadTime(LocalDateTime.now());
            return updateById(sysNotification);
        }
        return false;
    }

    @Override
    public boolean markAsReadBatch(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        LambdaUpdateWrapper<SysNotification> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(SysNotification::getId, ids)
                .set(SysNotification::getIsRead, 1)
                .set(SysNotification::getReadTime, LocalDateTime.now());
        return update(updateWrapper);
    }

    @Override
    public boolean markAllAsRead(Long userId) {
        LambdaUpdateWrapper<SysNotification> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysNotification::getUserId, userId)
                .eq(SysNotification::getIsRead, 0)
                .set(SysNotification::getIsRead, 1)
                .set(SysNotification::getReadTime, LocalDateTime.now());
        return update(updateWrapper);
    }

    @Override
    public Page<NotificationVO> pageQuery(NotificationQueryDTO query) {
        Page<SysNotification> page = new Page<>(query.getCurrent(), query.getSize());

        LambdaQueryWrapper<SysNotification> queryWrapper = new LambdaQueryWrapper<>();

        // 用户ID条件
        if (query.getUserId() != null) {
            queryWrapper.eq(SysNotification::getUserId, query.getUserId());
        }

        // 类型条件
        if (StringUtils.hasText(query.getType())) {
            queryWrapper.eq(SysNotification::getType, query.getType());
        }

        // 已读状态条件
        if (query.getIsRead() != null) {
            queryWrapper.eq(SysNotification::getIsRead, query.getIsRead());
        }

        // 关键词搜索
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                .like(SysNotification::getTitle, query.getKeyword())
                .or()
                .like(SysNotification::getContent, query.getKeyword())
            );
        }

        // 按创建时间倒序
        queryWrapper.orderByDesc(SysNotification::getCreateTime);

        Page<SysNotification> resultPage = page(page, queryWrapper);
        return BeanConverter.convertPage(resultPage, NotificationVO::new);
    }
}
