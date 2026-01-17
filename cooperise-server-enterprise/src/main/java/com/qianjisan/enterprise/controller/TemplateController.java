package com.qianjisan.enterprise.controller;

import com.qianjisan.core.PageVO;
import com.qianjisan.core.Result;
import com.qianjisan.enterprise.request.TemplateQueryRequest;
import com.qianjisan.enterprise.request.TemplateRequest;
import com.qianjisan.enterprise.service.TemplateService;
import com.qianjisan.enterprise.vo.TemplateVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 事项表 前端控制器
 * </p>
 *
 * @author cooperise
 * @since 2026-01-17
 */
@Tag(name = "模板管理", description = "Template 相关接口")
@RestController
@RequestMapping("/enterprise/template")
@RequiredArgsConstructor
@Slf4j
public class TemplateController {

    private final TemplateService templateService;

    @Operation(summary = "创建模板")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody TemplateRequest request) {
        try {
            templateService.createTemplate(request);
            return Result.success();
        } catch (Exception e) {
            log.error("创建模板失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新模板")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody TemplateRequest request) {
        try {
            templateService.updateTemplate(id, request);
            return Result.success();
        } catch (Exception e) {
            log.error("更新模板失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除模板")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            templateService.deleteTemplate(id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除模板失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询模板")
    @GetMapping("/{id}")
    public Result<TemplateVo> getById(@PathVariable Long id) {
        try {
            TemplateVo vo = templateService.getTemplateById(id);
            return Result.success(vo);
        } catch (Exception e) {
            log.error("查询模板失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询模板")
    @PostMapping("/page")
    public Result<PageVO<TemplateVo>> page(@RequestBody TemplateQueryRequest request) {
        try {
            PageVO<TemplateVo> pageVO = templateService.getTemplatePage(request);
            return Result.success(pageVO);
        } catch (Exception e) {
            log.error("分页查询模板失败", e);
            return Result.error(e.getMessage());
        }
    }
}
