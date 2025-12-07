package com.qianjisan.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.common.Result;
import com.qianjisan.common.dto.WorkspaceIssueAttachmentQueryDTO;
import com.qianjisan.entity.IssueAttachment;
import com.qianjisan.service.IIssueAttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
public class IssueAttachmentController {

    private final IIssueAttachmentService issueAttachmentService;

    @Operation(summary = "创建事项附件管理")
    @PostMapping
    public Result<IssueAttachment> create(@RequestBody IssueAttachment entity) {
        issueAttachmentService.save(entity);
        return Result.success(entity);
    }

    @Operation(summary = "更新事项附件管理")
    @PutMapping("/{id}")
    public Result<IssueAttachment> update(@PathVariable Long id, @RequestBody IssueAttachment entity) {
        entity.setId(id);
        issueAttachmentService.updateById(entity);
        return Result.success(entity);
    }

    @Operation(summary = "删除事项附件管理")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        issueAttachmentService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询事项附件管理")
    @GetMapping("/{id}")
    public Result<IssueAttachment> getById(@PathVariable Long id) {
       IssueAttachment entity = issueAttachmentService.getById(id);
        return Result.success(entity);
    }

    @Operation(summary = "查询事项附件管理列表")
    @GetMapping("/list")
    public Result<List<IssueAttachment>> list() {
        List<IssueAttachment> list = issueAttachmentService.list();
        return Result.success(list);
    }

    @Operation(summary = "分页查询事项附件管理")
    @PostMapping("/page")
    public Result<Page<IssueAttachment>> page(@RequestBody WorkspaceIssueAttachmentQueryDTO query) {
        try {
            Page<IssueAttachment> page = issueAttachmentService.pageIssueAttachment(query);
            return Result.success(page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
