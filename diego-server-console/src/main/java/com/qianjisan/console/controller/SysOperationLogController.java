package com.qianjisan.console.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.qianjisan.annotation.RequiresPermission;
import com.qianjisan.core.PageVO;
import com.qianjisan.core.Result;
import com.qianjisan.system.entity.SysOperationLog;
import com.qianjisan.system.request.SysOperationLogRequest;
import com.qianjisan.system.service.ISysOperationLogService;
import com.qianjisan.system.vo.SysOperationLogVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 操作日志控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "操作日志管理", description = "操作日志相关接口")
@RestController
@RequestMapping("/operation-log")
@RequiredArgsConstructor
public class SysOperationLogController {

    private final ISysOperationLogService operationLogService;

    @Operation(summary = "分页查询操作日志")
    @RequiresPermission("record:operation:view")
    @PostMapping("/page")
    public Result<PageVO<SysOperationLogVO>> page(@RequestBody SysOperationLogRequest query) {
        Page<SysOperationLogVO> page = operationLogService.pageQuery(query);
        return Result.success(PageVO.of(page));
    }

    @Operation(summary = "根据ID查询操作日志")
    @RequiresPermission("record:operation:view")
    @GetMapping("/{id}")
    public Result<SysOperationLogVO> getById(@PathVariable Long id) {
        SysOperationLog log = operationLogService.getById(id);
        SysOperationLogVO vo = new SysOperationLogVO();
        if (log != null) {
            org.springframework.beans.BeanUtils.copyProperties(log, vo);
        }
        return Result.success(vo);
    }
}
