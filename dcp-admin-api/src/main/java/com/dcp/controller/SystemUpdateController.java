package com.dcp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcp.common.Result;
import com.dcp.entity.SystemUpdate;
import com.dcp.service.ISystemUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 系统更新控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "系统更新管理", description = "系统更新相关接口")
@RestController
@RequestMapping("/feedback/system-update")
@RequiredArgsConstructor
@Slf4j
public class SystemUpdateController {

    private final ISystemUpdateService systemUpdateService;

    @Operation(summary = "创建系统更新")
    @PostMapping
    public Result<SystemUpdate> create(@RequestBody SystemUpdate entity) {
        try {
            entity.setStatus(0); // 默认为草稿
            systemUpdateService.save(entity);
            return Result.success(entity);
        } catch (Exception e) {
            log.error("[创建系统更新] 失败：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新系统更新")
    @PutMapping("/{id}")
    public Result<SystemUpdate> update(@PathVariable Long id, @RequestBody SystemUpdate entity) {
        try {
            entity.setId(id);
            systemUpdateService.updateById(entity);
            return Result.success(entity);
        } catch (Exception e) {
            log.error("[更新系统更新] 失败：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除系统更新")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            systemUpdateService.removeById(id);
            return Result.success();
        } catch (Exception e) {
            log.error("[删除系统更新] 失败：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询系统更新")
    @GetMapping("/{id}")
    public Result<SystemUpdate> getById(@PathVariable Long id) {
        try {
            SystemUpdate entity = systemUpdateService.getById(id);
            return Result.success(entity);
        } catch (Exception e) {
            log.error("[查询系统更新] 失败：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "发布系统更新")
    @PostMapping("/{id}/publish")
    public Result<Void> publish(@PathVariable Long id) {
        try {
            systemUpdateService.publish(id);
            return Result.success();
        } catch (Exception e) {
            log.error("[发布系统更新] 失败：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "获取当前已发布的系统更新")
    @GetMapping("/published")
    public Result<SystemUpdate> getPublished() {
        try {
            SystemUpdate entity = systemUpdateService.getPublished();
            return Result.success(entity);
        } catch (Exception e) {
            log.error("[获取已发布系统更新] 失败：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询系统更新列表")
    @GetMapping("/page")
    public Result<Page<SystemUpdate>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long pageSize,
            @RequestParam(required = false) Integer status) {
        try {
            Page<SystemUpdate> page = systemUpdateService.pageList(current, pageSize, status);
            return Result.success(page);
        } catch (Exception e) {
            log.error("[分页查询系统更新] 失败：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }
}
