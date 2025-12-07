package com.qianjisan.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.common.Result;
import com.qianjisan.common.dto.WorkspaceIssueActivityQueryDTO;
import com.qianjisan.entity.IssueActivity;
import com.qianjisan.entity.WorkspaceIssueActivity;
import com.qianjisan.service.IIssueActivityService;
import com.qianjisan.service.IWorkspaceIssueActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
public class IssueActivityController {

    private final IIssueActivityService issueActivityService;

    @Operation(summary = "创建事项活动记录管理")
    @PostMapping
    public Result<IssueActivity> create(@RequestBody IssueActivity entity) {
        issueActivityService.save(entity);
        return Result.success(entity);
    }

    @Operation(summary = "更新事项活动记录管理")
    @PutMapping("/{id}")
    public Result<IssueActivity> update(@PathVariable Long id, @RequestBody IssueActivity entity) {
        entity.setId(id);
        issueActivityService.updateById(entity);
        return Result.success(entity);
    }

    @Operation(summary = "删除事项活动记录管理")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        issueActivityService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询事项活动记录管理")
    @GetMapping("/{id}")
    public Result<IssueActivity> getById(@PathVariable Long id) {
        IssueActivity entity = issueActivityService.getById(id);
        return Result.success(entity);
    }

    @Operation(summary = "查询事项活动记录管理列表")
    @GetMapping("/list")
    public Result<List<IssueActivity>> list() {
        List<IssueActivity> list = issueActivityService.list();
        return Result.success(list);
    }

    @Operation(summary = "分页查询事项活动记录管理")
    @PostMapping("/page")
    public Result<Page<IssueActivity>> page(@RequestBody WorkspaceIssueActivityQueryDTO query) {
        try {
            Page<IssueActivity> page = issueActivityService.pageIssueActivity(query);
            return Result.success(page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
