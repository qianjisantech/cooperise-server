package com.dcp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcp.common.Result;
import com.dcp.common.annotation.RequiresPermission;
import com.dcp.common.dto.ChangelogQueryDTO;
import com.dcp.entity.SysChangelog;
import com.dcp.service.IChangelogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 发布日志管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "发布日志管理", description = "Changelog相关接口")
@RestController
@RequestMapping("/changelog")
@RequiredArgsConstructor
public class ChangelogController {

    private final IChangelogService changelogService;

    @Operation(summary = "创建发布日志管理")
    @RequiresPermission("changelog:add")
    @PostMapping
    public Result<SysChangelog> create(@RequestBody SysChangelog entity) {
        changelogService.save(entity);
        return Result.success(entity);
    }

    @Operation(summary = "更新发布日志管理")
    @RequiresPermission("changelog:edit")
    @PutMapping("/{id}")
    public Result<SysChangelog> update(@PathVariable Long id, @RequestBody SysChangelog entity) {
        entity.setId(id);
        changelogService.updateById(entity);
        return Result.success(entity);
    }

    @Operation(summary = "删除发布日志管理")
    @RequiresPermission("changelog:delete")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        changelogService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询发布日志管理")
    @RequiresPermission("changelog:view")
    @GetMapping("/{id}")
    public Result<SysChangelog> getById(@PathVariable Long id) {
        SysChangelog entity = changelogService.getById(id);
        return Result.success(entity);
    }

    @Operation(summary = "查询发布日志管理列表")
    @RequiresPermission("changelog:view")
    @GetMapping("/list")
    public Result<List<SysChangelog>> list() {
        List<SysChangelog> list = changelogService.list();
        return Result.success(list);
    }

    @Operation(summary = "分页查询发布日志管理")
    @RequiresPermission("changelog:view")
    @PostMapping("/page")
    public Result<Page<SysChangelog>> page(@RequestBody ChangelogQueryDTO query) {
        Page<SysChangelog> page = new Page<>(query.getCurrent(), query.getSize());

        LambdaQueryWrapper<SysChangelog> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(query.getVersion())) {
            queryWrapper.eq(SysChangelog::getVersion, query.getVersion());
        }
        if (StringUtils.hasText(query.getType())) {
            queryWrapper.eq(SysChangelog::getType, query.getType());
        }
        if (query.getPublisherId() != null) {
            queryWrapper.eq(SysChangelog::getPublisherId, query.getPublisherId());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                .like(SysChangelog::getTitle, query.getKeyword())
                .or()
                .like(SysChangelog::getContent, query.getKeyword())
            );
        }

        queryWrapper.orderByDesc(SysChangelog::getPublishDate);

        page = changelogService.page(page, queryWrapper);
        return Result.success(page);
    }
}
