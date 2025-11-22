package com.dcp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcp.common.Result;
import com.dcp.entity.TopNotification;
import com.dcp.service.ITopNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 顶部通知控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "顶部通知管理", description = "顶部通知相关接口")
@RestController
@RequestMapping("/feedback/top-notification")
@RequiredArgsConstructor
@Slf4j
public class TopNotificationController {

    private final ITopNotificationService topNotificationService;

    @Operation(summary = "创建顶部通知")
    @PostMapping
    public Result<TopNotification> create(@RequestBody TopNotification entity) {
        try {
            entity.setStatus(0); // 默认为草稿
            topNotificationService.save(entity);
            return Result.success(entity);
        } catch (Exception e) {
            log.error("[创建顶部通知] 失败：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新顶部通知")
    @PutMapping("/{id}")
    public Result<TopNotification> update(@PathVariable Long id, @RequestBody TopNotification entity) {
        try {
            entity.setId(id);
            topNotificationService.updateById(entity);
            return Result.success(entity);
        } catch (Exception e) {
            log.error("[更新顶部通知] 失败：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除顶部通知")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            topNotificationService.removeById(id);
            return Result.success();
        } catch (Exception e) {
            log.error("[删除顶部通知] 失败：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询顶部通知")
    @GetMapping("/{id}")
    public Result<TopNotification> getById(@PathVariable Long id) {
        try {
            TopNotification entity = topNotificationService.getById(id);
            return Result.success(entity);
        } catch (Exception e) {
            log.error("[查询顶部通知] 失败：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "发布顶部通知")
    @PostMapping("/{id}/publish")
    public Result<Void> publish(@PathVariable Long id) {
        try {
            topNotificationService.publish(id);
            return Result.success();
        } catch (Exception e) {
            log.error("[发布顶部通知] 失败：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "获取当前已发布的顶部通知")
    @GetMapping("/published")
    public Result<TopNotification> getPublished() {
        try {
            TopNotification entity = topNotificationService.getPublished();
            return Result.success(entity);
        } catch (Exception e) {
            log.error("[获取已发布顶部通知] 失败：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询顶部通知列表")
    @GetMapping("/page")
    public Result<Page<TopNotification>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long pageSize,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer type) {
        try {
            Page<TopNotification> page = topNotificationService.pageList(current, pageSize, status, type);
            return Result.success(page);
        } catch (Exception e) {
            log.error("[分页查询顶部通知] 失败：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }
}
