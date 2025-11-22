package com.dcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.common.context.UserContext;
import com.dcp.common.context.UserContextHolder;
import com.dcp.entity.SystemUpdate;
import com.dcp.rbac.entity.SysUser;
import com.dcp.mapper.SystemUpdateMapper;
import com.dcp.service.ISystemUpdateService;
import com.dcp.rbac.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 系统更新Service实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemUpdateServiceImpl extends ServiceImpl<SystemUpdateMapper, SystemUpdate> implements ISystemUpdateService {

    private final ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publish(Long id) {
        log.info("[SystemUpdateService] 发布系统更新，ID: {}", id);

        // 获取要发布的记录
        SystemUpdate update = this.getById(id);
        if (update == null) {
            throw new RuntimeException("系统更新不存在");
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
        LambdaUpdateWrapper<SystemUpdate> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SystemUpdate::getStatus, 1)
                .set(SystemUpdate::getStatus, 0);
        this.update(updateWrapper);

        log.info("[SystemUpdateService] 已将其他已发布的系统更新设置为草稿");

        // 发布当前记录
        update.setStatus(1);
        update.setPublishTime(LocalDateTime.now());
        update.setPublisherId(userId);
        update.setPublisherCode(currentUser.getUserCode());
        update.setPublisherName(currentUser.getUsername());
        this.updateById(update);

        log.info("[SystemUpdateService] 系统更新发布成功，ID: {}, 发布人: {}", id, currentUser.getUsername());
    }

    @Override
    public SystemUpdate getPublished() {
        LambdaQueryWrapper<SystemUpdate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUpdate::getStatus, 1)
                .orderByDesc(SystemUpdate::getPublishTime)
                .last("LIMIT 1");
        return this.getOne(queryWrapper);
    }

    @Override
    public Page<SystemUpdate> pageList(Long current, Long pageSize, Integer status) {
        Page<SystemUpdate> page = new Page<>(current, pageSize);
        LambdaQueryWrapper<SystemUpdate> queryWrapper = new LambdaQueryWrapper<>();

        if (status != null) {
            queryWrapper.eq(SystemUpdate::getStatus, status);
        }

        queryWrapper.orderByDesc(SystemUpdate::getCreateTime);

        return this.page(page, queryWrapper);
    }
}
