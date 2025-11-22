package com.dcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.entity.SystemUpdate;

/**
 * 系统更新Service接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface ISystemUpdateService extends IService<SystemUpdate> {

    /**
     * 发布系统更新（同时将其他已发布的设置为草稿）
     *
     * @param id 系统更新ID
     */
    void publish(Long id);

    /**
     * 获取当前已发布的系统更新
     *
     * @return 已发布的系统更新，如果没有则返回null
     */
    SystemUpdate getPublished();

    /**
     * 分页查询系统更新列表
     *
     * @param current  当前页
     * @param pageSize 每页大小
     * @param status   状态（可选）
     * @return 分页结果
     */
    Page<SystemUpdate> pageList(Long current, Long pageSize, Integer status);
}
