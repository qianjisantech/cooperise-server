package com.qianjisan.console.controller;

import com.qianjisan.core.PageVO;
import com.qianjisan.core.Result;
import com.qianjisan.console.request.IssueDetailQueryRequest;
import com.qianjisan.console.request.IssueDetailRequest;
import com.qianjisan.console.service.IssueDetailService;
import com.qianjisan.console.vo.IssueDetailVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 事项主表 前端控制器
 * </p>
 *
 * @author cooperise
 * @since 2026-01-17
 */
@Tag(name = "事项详情管理", description = "IssueDetail 相关接口")
@RestController
@RequestMapping("/console/issue-detail")
@RequiredArgsConstructor
@Slf4j
public class IssueDetailController {

    private final IssueDetailService issueDetailService;

    @Operation(summary = "创建事项详情")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody IssueDetailRequest request) {
        try {
            issueDetailService.createIssueDetail(request);
            return Result.success();
        } catch (Exception e) {
            log.error("创建事项详情失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新事项详情")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody IssueDetailRequest request) {
        try {
            issueDetailService.updateIssueDetail(id, request);
            return Result.success();
        } catch (Exception e) {
            log.error("更新事项详情失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除事项详情")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            issueDetailService.deleteIssueDetail(id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除事项详情失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询事项详情")
    @GetMapping("/{id}")
    public Result<IssueDetailVO> getById(@PathVariable Long id) {
        try {
            IssueDetailVO vo = issueDetailService.getIssueDetailById(id);
            return Result.success(vo);
        } catch (Exception e) {
            log.error("查询事项详情失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询事项详情")
    @PostMapping("/page")
    public Result<PageVO<IssueDetailVO>> page(@RequestBody IssueDetailQueryRequest request) {
        try {
            PageVO<IssueDetailVO> pageVO = issueDetailService.getIssueDetailPage(request);
            return Result.success(pageVO);
        } catch (Exception e) {
            log.error("分页查询事项详情失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据企业ID查询事项详情列表")
    @GetMapping("/company/{companyId}")
    public Result<List<IssueDetailVO>> getByCompanyId(@PathVariable Long companyId) {
        try {
            List<IssueDetailVO> vos = issueDetailService.getIssueDetailsByCompanyId(companyId);
            return Result.success(vos);
        } catch (Exception e) {
            log.error("根据企业ID查询事项详情失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据模板字段ID查询事项详情列表")
    @GetMapping("/template-field/{templateFieldId}")
    public Result<List<IssueDetailVO>> getByTemplateFieldId(@PathVariable Long templateFieldId) {
        try {
            List<IssueDetailVO> vos = issueDetailService.getIssueDetailsByTemplateFieldId(templateFieldId);
            return Result.success(vos);
        } catch (Exception e) {
            log.error("根据模板字段ID查询事项详情失败", e);
            return Result.error(e.getMessage());
        }
    }
}
