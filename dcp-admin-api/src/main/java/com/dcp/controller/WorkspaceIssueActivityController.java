package com.dcp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcp.common.Result;
import com.dcp.common.dto.WorkspaceIssueActivityQueryDTO;
import com.dcp.entity.WorkspaceIssueActivity;
import com.dcp.service.IWorkspaceIssueActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 事项活动记录管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "事项活动记录管理", description = "WorkspaceIssueActivity相关接口")
@RestController
@RequestMapping("/workspace/issue-activity")
@RequiredArgsConstructor
public class WorkspaceIssueActivityController {

    private final IWorkspaceIssueActivityService workspaceIssueActivityService;

    @Operation(summary = "创建事项活动记录管理")
    @PostMapping
    public Result<WorkspaceIssueActivity> create(@RequestBody WorkspaceIssueActivity entity) {
        workspaceIssueActivityService.save(entity);
        return Result.success(entity);
    }

    @Operation(summary = "更新事项活动记录管理")
    @PutMapping("/{id}")
    public Result<WorkspaceIssueActivity> update(@PathVariable Long id, @RequestBody WorkspaceIssueActivity entity) {
        entity.setId(id);
        workspaceIssueActivityService.updateById(entity);
        return Result.success(entity);
    }

    @Operation(summary = "删除事项活动记录管理")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        workspaceIssueActivityService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询事项活动记录管理")
    @GetMapping("/{id}")
    public Result<WorkspaceIssueActivity> getById(@PathVariable Long id) {
        WorkspaceIssueActivity entity = workspaceIssueActivityService.getById(id);
        return Result.success(entity);
    }

    @Operation(summary = "查询事项活动记录管理列表")
    @GetMapping("/list")
    public Result<List<WorkspaceIssueActivity>> list() {
        List<WorkspaceIssueActivity> list = workspaceIssueActivityService.list();
        return Result.success(list);
    }

    @Operation(summary = "分页查询事项活动记录管理")
    @PostMapping("/page")
    public Result<Page<WorkspaceIssueActivity>> page(@RequestBody WorkspaceIssueActivityQueryDTO query) {
        Page<WorkspaceIssueActivity> page = new Page<>(query.getCurrent(), query.getSize());

        LambdaQueryWrapper<WorkspaceIssueActivity> queryWrapper = new LambdaQueryWrapper<>();

        if (query.getIssueId() != null) {
            queryWrapper.eq(WorkspaceIssueActivity::getIssueId, query.getIssueId());
        }
        if (StringUtils.hasText(query.getActivityType())) {
            queryWrapper.eq(WorkspaceIssueActivity::getAction, query.getActivityType());
        }
        if (query.getUserId() != null) {
            queryWrapper.eq(WorkspaceIssueActivity::getUserId, query.getUserId());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                .like(WorkspaceIssueActivity::getAction, query.getKeyword())
                .or().like(WorkspaceIssueActivity::getField, query.getKeyword())
                .or().like(WorkspaceIssueActivity::getOldValue, query.getKeyword())
                .or().like(WorkspaceIssueActivity::getNewValue, query.getKeyword())
            );
        }

        queryWrapper.orderByDesc(WorkspaceIssueActivity::getCreateTime);

        page = workspaceIssueActivityService.page(page, queryWrapper);
        return Result.success(page);
    }
}
