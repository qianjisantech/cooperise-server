package com.dcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.common.dto.NotificationQueryDTO;
import com.dcp.common.vo.NotificationVO;
import com.dcp.entity.SysNotification;

import java.util.List;

/**
 * Notification服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface INotificationService extends IService<SysNotification> {

    /**
     * 根据ID查询通知
     *
     * @param id 通知ID
     * @return 通知VO
     */
    NotificationVO getNotificationById(Long id);

    /**
     * 获取用户未读通知列表
     *
     * @param userId 用户ID
     * @return 未读通知列表VO
     */
    List<NotificationVO> getUnreadByUserId(Long userId);

    /**
     * 获取用户未读通知数量
     *
     * @param userId 用户ID
     * @return 未读通知数量
     */
    Long getUnreadCount(Long userId);

    /**
     * 标记通知为已读
     *
     * @param id 通知ID
     * @return 是否成功
     */
    boolean markAsRead(Long id);

    /**
     * 批量标记通知为已读
     *
     * @param ids 通知ID列表
     * @return 是否成功
     */
    boolean markAsReadBatch(List<Long> ids);

    /**
     * 标记用户所有通知为已读
     *
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean markAllAsRead(Long userId);

    /**
     * 分页查询通知
     *
     * @param query 查询条件
     * @return 分页结果VO
     */
    Page<NotificationVO> pageQuery(NotificationQueryDTO query);
}
