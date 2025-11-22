package com.dcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.entity.TopNotification;

/**
 * 顶部通知Service接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface ITopNotificationService extends IService<TopNotification> {

    /**
     * 发布顶部通知（同时将其他已发布的设置为草稿）
     *
     * @param id 顶部通知ID
     */
    void publish(Long id);

    /**
     * 获取当前已发布的顶部通知
     *
     * @return 已发布的顶部通知，如果没有则返回null
     */
    TopNotification getPublished();

    /**
     * 分页查询顶部通知列表
     *
     * @param current  当前页
     * @param pageSize 每页大小
     * @param status   状态（可选）
     * @param type     类型（可选）
     * @return 分页结果
     */
    Page<TopNotification> pageList(Long current, Long pageSize, Integer status, Integer type);
}
