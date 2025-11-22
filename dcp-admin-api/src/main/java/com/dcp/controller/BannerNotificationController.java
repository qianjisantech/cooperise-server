package com.dcp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcp.common.Result;
import com.dcp.common.dto.BannerNotificationQueryDTO;
import com.dcp.entity.BannerNotification;
import com.dcp.service.IBannerNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Banner通知管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "Banner通知管理", description = "BannerNotification相关接口")
@RestController
@RequestMapping("/banner-notification")
@RequiredArgsConstructor
public class BannerNotificationController {

    private final IBannerNotificationService bannerNotificationService;

    @Operation(summary = "创建Banner通知管理")
    @PostMapping
    public Result<BannerNotification> create(@RequestBody BannerNotification entity) {
        bannerNotificationService.save(entity);
        return Result.success(entity);
    }

    @Operation(summary = "更新Banner通知管理")
    @PutMapping("/{id}")
    public Result<BannerNotification> update(@PathVariable Long id, @RequestBody BannerNotification entity) {
        entity.setId(id);
        bannerNotificationService.updateById(entity);
        return Result.success(entity);
    }

    @Operation(summary = "删除Banner通知管理")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        bannerNotificationService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询Banner通知管理")
    @GetMapping("/{id}")
    public Result<BannerNotification> getById(@PathVariable Long id) {
        BannerNotification entity = bannerNotificationService.getById(id);
        return Result.success(entity);
    }

    @Operation(summary = "查询Banner通知管理列表")
    @GetMapping("/list")
    public Result<List<BannerNotification>> list() {
        List<BannerNotification> list = bannerNotificationService.list();
        return Result.success(list);
    }

    @Operation(summary = "分页查询Banner通知管理")
    @PostMapping("/page")
    public Result<Page<BannerNotification>> page(@RequestBody BannerNotificationQueryDTO query) {
        Page<BannerNotification> page = new Page<>(query.getCurrent(), query.getSize());

        LambdaQueryWrapper<BannerNotification> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(query.getStatus())) {
            queryWrapper.eq(BannerNotification::getStatus, query.getStatus());
        }
        if (StringUtils.hasText(query.getType())) {
            queryWrapper.eq(BannerNotification::getType, query.getType());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                .like(BannerNotification::getTitle, query.getKeyword())
                .or()
                .like(BannerNotification::getContent, query.getKeyword())
            );
        }

        queryWrapper.orderByDesc(BannerNotification::getCreateTime);

        page = bannerNotificationService.page(page, queryWrapper);
        return Result.success(page);
    }
}
