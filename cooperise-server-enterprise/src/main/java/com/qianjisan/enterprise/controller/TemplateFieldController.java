package com.qianjisan.enterprise.controller;

import com.qianjisan.core.PageVO;
import com.qianjisan.core.Result;
import com.qianjisan.enterprise.request.BatchSaveTemplateFieldRequest;
import com.qianjisan.enterprise.request.TemplateFieldQueryRequest;

import com.qianjisan.enterprise.service.TemplateFieldService;
import com.qianjisan.enterprise.vo.TemplateFieldVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 模板字段 前端控制器
 * </p>
 *
 * @author cooperise
 * @since 2026-01-17
 */
@Tag(name = "模板字段管理", description = "TemplateField 相关接口")
@RestController
@RequestMapping("/enterprise/template-field")
@RequiredArgsConstructor
@Slf4j
public class TemplateFieldController {

    private final TemplateFieldService templateFieldService;

    @Operation(summary = "批量保存模板字段")
    @PostMapping("/save")
    public Result<Void> batchSave(@Valid @RequestBody BatchSaveTemplateFieldRequest request) {
        try {
            templateFieldService.batchSaveTemplateFields(request);
            return Result.success();
        } catch (Exception e) {
            log.error("批量保存模板字段失败", e);
            return Result.error(e.getMessage());
        }
    }



    @Operation(summary = "更新模板字段")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody BatchSaveTemplateFieldRequest request) {
        try {
            templateFieldService.updateTemplateField(id, request);
            return Result.success();
        } catch (Exception e) {
            log.error("更新模板字段失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除模板字段")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            templateFieldService.deleteTemplateField(id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除模板字段失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询模板字段")
    @GetMapping("/{id}")
    public Result<TemplateFieldVo> getById(@PathVariable Long id) {
        try {
            TemplateFieldVo vo = templateFieldService.getTemplateFieldById(id);
            return Result.success(vo);
        } catch (Exception e) {
            log.error("查询模板字段失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询模板字段")
    @PostMapping("/page")
    public Result<PageVO<TemplateFieldVo>> page(@RequestBody TemplateFieldQueryRequest request) {
        try {
            PageVO<TemplateFieldVo> pageVO = templateFieldService.getTemplateFieldPage(request);
            return Result.success(pageVO);
        } catch (Exception e) {
            log.error("分页查询模板字段失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据模板ID查询字段列表")
    @GetMapping("/template/{templateId}")
    public Result<List<TemplateFieldVo>> getFieldsByTemplateId(@PathVariable String templateId) {
        try {
            List<TemplateFieldVo> vos = templateFieldService.getFieldsByTemplateId(templateId);
            return Result.success(vos);
        } catch (Exception e) {
            log.error("根据模板ID查询字段列表失败", e);
            return Result.error(e.getMessage());
        }
    }
}
