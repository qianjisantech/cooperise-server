package com.dcp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcp.common.Result;
import com.dcp.common.dto.WorkspaceIssueAttachmentQueryDTO;
import com.dcp.entity.WorkspaceIssueAttachment;
import com.dcp.service.IWorkspaceIssueAttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 事项附件管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "事项附件管理", description = "WorkspaceIssueAttachment相关接口")
@RestController
@RequestMapping("/workspace/issue-attachment")
@RequiredArgsConstructor
public class WorkspaceIssueAttachmentController {

    private final IWorkspaceIssueAttachmentService workspaceIssueAttachmentService;

    @Operation(summary = "创建事项附件管理")
    @PostMapping
    public Result<WorkspaceIssueAttachment> create(@RequestBody WorkspaceIssueAttachment entity) {
        workspaceIssueAttachmentService.save(entity);
        return Result.success(entity);
    }

    @Operation(summary = "更新事项附件管理")
    @PutMapping("/{id}")
    public Result<WorkspaceIssueAttachment> update(@PathVariable Long id, @RequestBody WorkspaceIssueAttachment entity) {
        entity.setId(id);
        workspaceIssueAttachmentService.updateById(entity);
        return Result.success(entity);
    }

    @Operation(summary = "删除事项附件管理")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        workspaceIssueAttachmentService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询事项附件管理")
    @GetMapping("/{id}")
    public Result<WorkspaceIssueAttachment> getById(@PathVariable Long id) {
        WorkspaceIssueAttachment entity = workspaceIssueAttachmentService.getById(id);
        return Result.success(entity);
    }

    @Operation(summary = "查询事项附件管理列表")
    @GetMapping("/list")
    public Result<List<WorkspaceIssueAttachment>> list() {
        List<WorkspaceIssueAttachment> list = workspaceIssueAttachmentService.list();
        return Result.success(list);
    }

    @Operation(summary = "分页查询事项附件管理")
    @PostMapping("/page")
    public Result<Page<WorkspaceIssueAttachment>> page(@RequestBody WorkspaceIssueAttachmentQueryDTO query) {
        Page<WorkspaceIssueAttachment> page = new Page<>(query.getCurrent(), query.getSize());

        LambdaQueryWrapper<WorkspaceIssueAttachment> queryWrapper = new LambdaQueryWrapper<>();

        if (query.getIssueId() != null) {
            queryWrapper.eq(WorkspaceIssueAttachment::getIssueId, query.getIssueId());
        }
        if (query.getUploaderId() != null) {
            queryWrapper.eq(WorkspaceIssueAttachment::getUploaderId, query.getUploaderId());
        }
        if (StringUtils.hasText(query.getFileType())) {
            queryWrapper.eq(WorkspaceIssueAttachment::getFileType, query.getFileType());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.like(WorkspaceIssueAttachment::getFileName, query.getKeyword());
        }

        queryWrapper.orderByDesc(WorkspaceIssueAttachment::getCreateTime);

        page = workspaceIssueAttachmentService.page(page, queryWrapper);
        return Result.success(page);
    }
}
