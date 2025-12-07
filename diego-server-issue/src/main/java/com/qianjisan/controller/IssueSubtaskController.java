package com.qianjisan.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.common.Result;
import com.qianjisan.common.dto.WorkspaceSubtaskQueryDTO;
import com.qianjisan.entity.IssueSubtask;
import com.qianjisan.entity.WorkspaceSubtask;
import com.qianjisan.service.IIssueSubtaskService;
import com.qianjisan.service.IWorkspaceSubtaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 子任务管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "子任务管理", description = "WorkspaceSubtask相关接口")
@RestController
@RequestMapping("/workspace/subtask")
@RequiredArgsConstructor
public class IssueSubtaskController {

    private final IIssueSubtaskService iIssueSubtaskService;

    @Operation(summary = "创建子任务管理")
    @PostMapping
    public Result<IssueSubtask> create(@RequestBody IssueSubtask entity) {
        iIssueSubtaskService.save(entity);
        return Result.success(entity);
    }

    @Operation(summary = "更新子任务管理")
    @PutMapping("/{id}")
    public Result<IssueSubtask> update(@PathVariable Long id, @RequestBody IssueSubtask entity) {
        entity.setId(id);
        iIssueSubtaskService.updateById(entity);
        return Result.success(entity);
    }

    @Operation(summary = "删除子任务管理")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        iIssueSubtaskService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询子任务管理")
    @GetMapping("/{id}")
    public Result<IssueSubtask> getById(@PathVariable Long id) {
        IssueSubtask entity = iIssueSubtaskService.getById(id);
        return Result.success(entity);
    }

    @Operation(summary = "查询子任务管理列表")
    @GetMapping("/list")
    public Result<List<IssueSubtask>> list() {
        List<IssueSubtask> list = iIssueSubtaskService.list();
        return Result.success(list);
    }

    @Operation(summary = "分页查询子任务管理")
    @PostMapping("/page")
    public Result<Page<IssueSubtask>> page(@RequestBody WorkspaceSubtaskQueryDTO query) {
        try {
            Page<IssueSubtask> page = iIssueSubtaskService.pageSubtask(query);
            return Result.success(page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
