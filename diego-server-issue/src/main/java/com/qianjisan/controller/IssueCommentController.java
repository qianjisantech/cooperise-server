package com.qianjisan.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.common.Result;
import com.qianjisan.common.dto.WorkspaceIssueCommentQueryDTO;
import com.qianjisan.entity.IssueComment;
import com.qianjisan.service.IIssueCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 事项评论管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "事项评论管理", description = "WorkspaceIssueComment相关接口")
@RestController
@RequestMapping("/workspace/issue-comment")
@RequiredArgsConstructor
public class IssueCommentController {

    private final IIssueCommentService workspaceIssueCommentService;

    @Operation(summary = "创建事项评论管理")
    @PostMapping
    public Result<IssueComment> create(@RequestBody IssueComment entity) {
        workspaceIssueCommentService.save(entity);
        return Result.success(entity);
    }

    @Operation(summary = "更新事项评论管理")
    @PutMapping("/{id}")
    public Result<IssueComment> update(@PathVariable Long id, @RequestBody IssueComment entity) {
        entity.setId(id);
        workspaceIssueCommentService.updateById(entity);
        return Result.success(entity);
    }

    @Operation(summary = "删除事项评论管理")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        workspaceIssueCommentService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询事项评论管理")
    @GetMapping("/{id}")
    public Result<IssueComment> getById(@PathVariable Long id) {
        IssueComment entity = workspaceIssueCommentService.getById(id);
        return Result.success(entity);
    }

    @Operation(summary = "查询事项评论管理列表")
    @GetMapping("/list")
    public Result<List<IssueComment>> list() {
        List<IssueComment> list = workspaceIssueCommentService.list();
        return Result.success(list);
    }

    @Operation(summary = "分页查询事项评论管理")
    @PostMapping("/page")
    public Result<Page<IssueComment>> page(@RequestBody WorkspaceIssueCommentQueryDTO query) {
        try {
            Page<IssueComment> page = workspaceIssueCommentService.pageIssueComment(query);
            return Result.success(page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
