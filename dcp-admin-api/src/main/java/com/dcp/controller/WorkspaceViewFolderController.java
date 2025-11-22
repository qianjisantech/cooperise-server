package com.dcp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcp.common.Result;
import com.dcp.common.context.UserContextHolder;
import com.dcp.common.dto.WorkspaceViewFolderQueryDTO;
import com.dcp.entity.WorkspaceView;
import com.dcp.entity.WorkspaceViewFolder;
import com.dcp.service.IWorkspaceViewFolderService;
import com.dcp.service.IWorkspaceViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 视图文件夹管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "视图文件夹管理", description = "WorkspaceViewFolder相关接口")
@RestController
@RequestMapping("/workspace/view-folder")
@RequiredArgsConstructor
@Slf4j
public class WorkspaceViewFolderController {

    private final IWorkspaceViewFolderService workspaceViewFolderService;
    private final IWorkspaceViewService workspaceViewService;

    @Operation(summary = "创建视图文件夹")
    @PostMapping
    public Result<WorkspaceViewFolder> create(@RequestBody WorkspaceViewFolder entity) {
        try {
            log.info("[创建文件夹] 接收到请求: {}", entity);

            // 参数校验
            if (!StringUtils.hasText(entity.getName())) {
                return Result.error("文件夹名称不能为空");
            }

            // 设置默认值
            if (entity.getOwnerId() == null) {
                Long userId = UserContextHolder.getUserId();
                if (userId != null) {
                    entity.setOwnerId(userId);
                    log.info("[创建文件夹] 设置创建者ID: {}", userId);
                }
            }

            if (entity.getSortOrder() == null) {
                entity.setSortOrder(0);
            }

            // 保存文件夹
            workspaceViewFolderService.save(entity);

            log.info("[创建文件夹] 成功，文件夹ID: {}", entity.getId());
            return Result.success(entity);

        } catch (Exception e) {
            log.error("[创建文件夹] 失败", e);
            return Result.error("创建文件夹失败: " + e.getMessage());
        }
    }

    @Operation(summary = "更新视图文件夹")
    @PutMapping("/{id}")
    public Result<WorkspaceViewFolder> update(@PathVariable Long id, @RequestBody WorkspaceViewFolder entity) {
        try {
            log.info("[更新文件夹] ID: {}, 数据: {}", id, entity);

            // 参数校验
            if (!StringUtils.hasText(entity.getName())) {
                return Result.error("文件夹名称不能为空");
            }

            // 检查文件夹是否存在
            WorkspaceViewFolder existFolder = workspaceViewFolderService.getById(id);
            if (existFolder == null) {
                return Result.error("文件夹不存在");
            }

            entity.setId(id);
            workspaceViewFolderService.updateById(entity);

            log.info("[更新文件夹] 成功");
            return Result.success(entity);

        } catch (Exception e) {
            log.error("[更新文件夹] 失败", e);
            return Result.error("更新文件夹失败: " + e.getMessage());
        }
    }

    @Operation(summary = "删除视图文件夹")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            log.info("[删除文件夹] ID: {}", id);

            // 检查文件夹是否存在
            WorkspaceViewFolder folder = workspaceViewFolderService.getById(id);
            if (folder == null) {
                return Result.error("文件夹不存在");
            }

            // 检查文件夹下是否有视图
            LambdaQueryWrapper<WorkspaceView> viewWrapper = new LambdaQueryWrapper<>();
            viewWrapper.eq(WorkspaceView::getFolderId, id);
            long viewCount = workspaceViewService.count(viewWrapper);

            if (viewCount > 0) {
                return Result.error("文件夹下还有 " + viewCount + " 个视图，请先删除或移动这些视图");
            }

            // 检查是否有子文件夹
            LambdaQueryWrapper<WorkspaceViewFolder> folderWrapper = new LambdaQueryWrapper<>();
            folderWrapper.eq(WorkspaceViewFolder::getParentId, id);
            long subFolderCount = workspaceViewFolderService.count(folderWrapper);

            if (subFolderCount > 0) {
                return Result.error("文件夹下还有 " + subFolderCount + " 个子文件夹，请先删除这些子文件夹");
            }

            // 删除文件夹
            workspaceViewFolderService.removeById(id);

            log.info("[删除文件夹] 成功");
            return Result.success();

        } catch (Exception e) {
            log.error("[删除文件夹] 失败", e);
            return Result.error("删除文件夹失败: " + e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询视图文件夹管理")
    @GetMapping("/{id}")
    public Result<WorkspaceViewFolder> getById(@PathVariable Long id) {
        try {
            log.info("[查询文件夹] ID: {}", id);
            WorkspaceViewFolder entity = workspaceViewFolderService.getById(id);
            if (entity == null) {
                return Result.error("文件夹不存在");
            }
            return Result.success(entity);
        } catch (Exception e) {
            log.error("[查询文件夹] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "查询视图文件夹管理列表")
    @GetMapping("/list")
    public Result<List<WorkspaceViewFolder>> list() {
        try {
            log.info("[查询文件夹列表]");
            List<WorkspaceViewFolder> list = workspaceViewFolderService.list();
            return Result.success(list);
        } catch (Exception e) {
            log.error("[查询文件夹列表] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询视图文件夹管理")
    @PostMapping("/page")
    public Result<Page<WorkspaceViewFolder>> page(@RequestBody WorkspaceViewFolderQueryDTO query) {
        log.info("[分页查询文件夹] 查询参数: {}", query);
        try {
            Page<WorkspaceViewFolder> page = new Page<>(query.getCurrent(), query.getSize());

            LambdaQueryWrapper<WorkspaceViewFolder> queryWrapper = new LambdaQueryWrapper<>();

            if (query.getParentId() != null) {
                queryWrapper.eq(WorkspaceViewFolder::getParentId, query.getParentId());
            }
            if (query.getOwnerId() != null) {
                queryWrapper.eq(WorkspaceViewFolder::getOwnerId, query.getOwnerId());
            }
            if (StringUtils.hasText(query.getKeyword())) {
                queryWrapper.like(WorkspaceViewFolder::getName, query.getKeyword());
            }

            queryWrapper.orderByDesc(WorkspaceViewFolder::getCreateTime);

            page = workspaceViewFolderService.page(page, queryWrapper);
            log.info("[分页查询文件夹] 成功，共 {} 条", page.getTotal());
            return Result.success(page);
        } catch (Exception e) {
            log.error("[分页查询文件夹] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }
}
