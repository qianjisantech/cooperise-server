package com.qianjisan.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.Result;
import com.qianjisan.common.dto.NotificationQueryDTO;
import com.qianjisan.vo.BannerNotificationVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通知管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "通知管理", description = "通知相关接口")
@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final INotificationService notificationService;
    private final IBannerNotificationService bannerNotificationService;

    @Operation(summary = "创建通知")
    @PostMapping
    public Result<Void> create(@RequestBody SysNotification entity) {
        notificationService.save(entity);
        return Result.success();
    }

    @Operation(summary = "更新通知")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody SysNotification entity) {
        entity.setId(id);
        notificationService.updateById(entity);
        return Result.success();
    }

    @Operation(summary = "删除通知")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        notificationService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询通知")
    @GetMapping("/{id}")
    public Result<NotificationVO> getById(@PathVariable Long id) {
        return Result.success(notificationService.getNotificationById(id));
    }

    @Operation(summary = "根据用户ID查询未读通知")
    @GetMapping("/unread/{userId}")
    public Result<List<NotificationVO>> getUnreadByUserId(@PathVariable Long userId) {
        return Result.success(notificationService.getUnreadByUserId(userId));
    }

    @Operation(summary = "获取激活的Banner通知")
    @GetMapping("/banner")
    public Result<List<BannerNotificationVO>> getActiveBanners() {
        return Result.success(bannerNotificationService.getActiveBanners());
    }

    @Operation(summary = "获取未读通知数量")
    @GetMapping("/unread-count")
    public Result<Long> getUnreadCount(@RequestParam Long userId) {
        return Result.success(notificationService.getUnreadCount(userId));
    }

    @Operation(summary = "标记通知为已读")
    @PutMapping("/{id}/read")
    public Result<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return Result.success();
    }

    @Operation(summary = "批量标记通知为已读")
    @PutMapping("/read")
    public Result<Void> markAsReadBatch(@RequestBody List<Long> ids) {
        notificationService.markAsReadBatch(ids);
        return Result.success();
    }

    @Operation(summary = "标记所有通知为已读")
    @PutMapping("/read-all")
    public Result<Void> markAllAsRead(@RequestParam Long userId) {
        notificationService.markAllAsRead(userId);
        return Result.success();
    }

    @Operation(summary = "分页查询通知")
    @PostMapping("/page")
    public Result<Page<NotificationVO>> page(@RequestBody NotificationQueryDTO query) {
        return Result.success(notificationService.pageQuery(query));
    }
}
