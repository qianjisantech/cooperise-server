package com.dcp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcp.common.Result;
import com.dcp.common.context.UserContextHolder;
import com.dcp.common.dto.WorkspaceViewQueryDTO;
import com.dcp.common.vo.ViewTreeNodeVO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 视图管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "视图管理", description = "WorkspaceView相关接口")
@RestController
@RequestMapping("/workspace/view")
@RequiredArgsConstructor
@Slf4j
public class WorkspaceViewController {

    private final IWorkspaceViewService workspaceViewService;
    private final IWorkspaceViewFolderService workspaceViewFolderService;

    @Operation(summary = "创建视图管理")
    @PostMapping
    public Result<WorkspaceView> create(@RequestBody WorkspaceView entity) {
        try {
            log.info("[创建视图] 接收到请求: {}", entity);

            // 参数校验
            if (!StringUtils.hasText(entity.getName())) {
                return Result.error("视图名称不能为空");
            }
            if (!StringUtils.hasText(entity.getType())) {
                return Result.error("视图类型不能为空");
            }

            // 设置默认值
            if (entity.getOwnerId() == null) {
                Long userId = UserContextHolder.getUserId();
                if (userId != null) {
                    entity.setOwnerId(userId);
                    log.info("[创建视图] 设置创建者ID: {}", userId);
                }
            }

            if (entity.getIsPublic() == null) {
                entity.setIsPublic(0); // 默认私有
            }

            if (entity.getSortOrder() == null) {
                entity.setSortOrder(0);
            }

            // config 字段如果为 null，设置为空 JSON 字符串
            if (entity.getConfig() == null) {
                entity.setConfig("{}");
            }

            // 保存视图
            workspaceViewService.save(entity);

            log.info("[创建视图] 成功，视图ID: {}", entity.getId());
            return Result.success(entity);

        } catch (Exception e) {
            log.error("[创建视图] 失败", e);
            return Result.error("创建视图失败: " + e.getMessage());
        }
    }

    @Operation(summary = "更新视图管理")
    @PutMapping("/{id}")
    public Result<WorkspaceView> update(@PathVariable Long id, @RequestBody WorkspaceView entity) {
        try {
            log.info("[更新视图] ID: {}, 数据: {}", id, entity);

            // 参数校验
            if (!StringUtils.hasText(entity.getName())) {
                return Result.error("视图名称不能为空");
            }
            if (!StringUtils.hasText(entity.getType())) {
                return Result.error("视图类型不能为空");
            }

            // 检查视图是否存在
            WorkspaceView existView = workspaceViewService.getById(id);
            if (existView == null) {
                return Result.error("视图不存在");
            }

            entity.setId(id);
            workspaceViewService.updateById(entity);

            log.info("[更新视图] 成功");
            return Result.success(entity);
        } catch (Exception e) {
            log.error("[更新视图] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除视图管理")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            log.info("[删除视图] ID: {}", id);

            // 检查视图是否存在
            WorkspaceView view = workspaceViewService.getById(id);
            if (view == null) {
                return Result.error("视图不存在");
            }

            workspaceViewService.removeById(id);

            log.info("[删除视图] 成功");
            return Result.success();
        } catch (Exception e) {
            log.error("[删除视图] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询视图管理")
    @GetMapping("/{id}")
    public Result<WorkspaceView> getById(@PathVariable Long id) {
        try {
            log.info("[查询视图] ID: {}", id);
            WorkspaceView entity = workspaceViewService.getById(id);
            if (entity == null) {
                return Result.error("视图不存在");
            }
            return Result.success(entity);
        } catch (Exception e) {
            log.error("[查询视图] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "查询视图管理列表（不含文件夹结构）")
    @GetMapping("/list")
    public Result<List<WorkspaceView>> list() {
        try {
            log.info("[查询视图列表]");
            List<WorkspaceView> list = workspaceViewService.list();
            return Result.success(list);
        } catch (Exception e) {
            log.error("[查询视图列表] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "查询视图树形列表（包含文件夹和视图的树形结构）")
    @GetMapping("/tree-list")
    public Result<List<ViewTreeNodeVO>> getTreeList() {
        try {
            log.info("[查询视图树形列表] 开始");

            // 获取当前用户ID
            Long userId = UserContextHolder.getUserId();
            log.info("[查询视图树形列表] 当前用户ID: {}", userId);

            // 1. 查询所有文件夹（按排序顺序）
            LambdaQueryWrapper<WorkspaceViewFolder> folderWrapper = new LambdaQueryWrapper<>();
            if (userId != null) {
                folderWrapper.eq(WorkspaceViewFolder::getOwnerId, userId);
            }
            folderWrapper.orderByAsc(WorkspaceViewFolder::getSortOrder)
                        .orderByDesc(WorkspaceViewFolder::getCreateTime);
            List<WorkspaceViewFolder> folders = workspaceViewFolderService.list(folderWrapper);
            log.info("[查询视图树形列表] 查询到 {} 个文件夹", folders.size());

            // 2. 查询所有视图（按排序顺序）
            LambdaQueryWrapper<WorkspaceView> viewWrapper = new LambdaQueryWrapper<>();
            if (userId != null) {
                viewWrapper.and(wrapper -> wrapper
                    .eq(WorkspaceView::getOwnerId, userId)
                    .or()
                    .eq(WorkspaceView::getIsPublic, 1)
                );
            }
            viewWrapper.orderByAsc(WorkspaceView::getSortOrder)
                       .orderByDesc(WorkspaceView::getCreateTime);
            List<WorkspaceView> views = workspaceViewService.list(viewWrapper);
            log.info("[查询视图树形列表] 查询到 {} 个视图", views.size());

            // 3. 构建树形结构
            List<ViewTreeNodeVO> treeList = buildViewTree(folders, views);

            log.info("[查询视图树形列表] 成功，共 {} 个根节点", treeList.size());
            return Result.success(treeList);

        } catch (Exception e) {
            log.error("[查询视图树形列表] 失败", e);
            return Result.error("查询视图树形列表失败: " + e.getMessage());
        }
    }

    /**
     * 构建视图树形结构
     *
     * @param folders 所有文件夹列表
     * @param views 所有视图列表
     * @return 树形结构列表
     */
    private List<ViewTreeNodeVO> buildViewTree(List<WorkspaceViewFolder> folders, List<WorkspaceView> views) {
        List<ViewTreeNodeVO> result = new ArrayList<>();

        // 1. 将所有文件夹转换为树节点
        List<ViewTreeNodeVO> folderNodes = folders.stream()
            .map(this::convertFolderToNode)
            .collect(Collectors.toList());

        // 2. 将所有视图转换为树节点
        List<ViewTreeNodeVO> viewNodes = views.stream()
            .map(this::convertViewToNode)
            .collect(Collectors.toList());

        // 3. 按 folderId 分组视图
        Map<Long, List<ViewTreeNodeVO>> viewsByFolder = viewNodes.stream()
            .filter(node -> node.getFolderId() != null)
            .collect(Collectors.groupingBy(ViewTreeNodeVO::getFolderId));

        // 4. 构建文件夹树结构并添加视图
        for (ViewTreeNodeVO folderNode : folderNodes) {
            // 4.1 如果是根文件夹（parentId 为 null），添加到结果
            if (folderNode.getParentId() == null) {
                result.add(folderNode);
            }

            // 4.2 将属于该文件夹的视图添加为子节点
            List<ViewTreeNodeVO> folderViews = viewsByFolder.get(folderNode.getId());
            if (folderViews != null && !folderViews.isEmpty()) {
                if (folderNode.getChildren() == null) {
                    folderNode.setChildren(new ArrayList<>());
                }
                folderNode.getChildren().addAll(folderViews);
            }
        }

        // 5. 处理子文件夹（如果支持嵌套文件夹）
        Map<Long, List<ViewTreeNodeVO>> foldersByParent = folderNodes.stream()
            .filter(node -> node.getParentId() != null)
            .collect(Collectors.groupingBy(ViewTreeNodeVO::getParentId));

        for (ViewTreeNodeVO folderNode : folderNodes) {
            List<ViewTreeNodeVO> childFolders = foldersByParent.get(folderNode.getId());
            if (childFolders != null && !childFolders.isEmpty()) {
                if (folderNode.getChildren() == null) {
                    folderNode.setChildren(new ArrayList<>());
                }
                folderNode.getChildren().addAll(0, childFolders);
            }
        }

        // 6. 添加根级视图（folderId 为 null 的视图）
        List<ViewTreeNodeVO> rootViews = viewNodes.stream()
            .filter(node -> node.getFolderId() == null)
            .collect(Collectors.toList());
        result.addAll(rootViews);

        return result;
    }

    /**
     * 将文件夹实体转换为树节点
     */
    private ViewTreeNodeVO convertFolderToNode(WorkspaceViewFolder folder) {
        ViewTreeNodeVO node = new ViewTreeNodeVO();
        node.setId(folder.getId());
        node.setName(folder.getName());
        node.setType("folder");
        node.setOwnerId(folder.getOwnerId());
        node.setParentId(folder.getParentId());
        node.setSortOrder(folder.getSortOrder());
        node.setCreateTime(folder.getCreateTime());
        node.setUpdateTime(folder.getUpdateTime());
        node.setChildren(new ArrayList<>());
        return node;
    }

    /**
     * 将视图实体转换为树节点
     */
    private ViewTreeNodeVO convertViewToNode(WorkspaceView view) {
        ViewTreeNodeVO node = new ViewTreeNodeVO();
        node.setId(view.getId());
        node.setName(view.getName());
        node.setType("view");
        node.setViewType(view.getType());
        node.setDescription(view.getDescription());
        node.setSpaceId(view.getSpaceId());
        node.setOwnerId(view.getOwnerId());
        node.setIsPublic(view.getIsPublic());
        node.setFolderId(view.getFolderId());
        node.setConfig(view.getConfig());
        node.setSortOrder(view.getSortOrder());
        node.setCreateTime(view.getCreateTime());
        node.setUpdateTime(view.getUpdateTime());
        return node;
    }

    @Operation(summary = "分页查询视图管理")
    @PostMapping("/page")
    public Result<Page<WorkspaceView>> page(@RequestBody WorkspaceViewQueryDTO query) {
        log.info("[分页查询视图] 查询参数: {}", query);
        try {
            Page<WorkspaceView> page = new Page<>(query.getCurrent(), query.getSize());

            LambdaQueryWrapper<WorkspaceView> queryWrapper = new LambdaQueryWrapper<>();

            if (query.getSpaceId() != null) {
                queryWrapper.eq(WorkspaceView::getSpaceId, query.getSpaceId());
            }
            if (query.getFolderId() != null) {
                queryWrapper.eq(WorkspaceView::getFolderId, query.getFolderId());
            }
            if (StringUtils.hasText(query.getType())) {
                queryWrapper.eq(WorkspaceView::getType, query.getType());
            }
            if (query.getOwnerId() != null) {
                queryWrapper.eq(WorkspaceView::getOwnerId, query.getOwnerId());
            }
            if (query.getIsPublic() != null) {
                queryWrapper.eq(WorkspaceView::getIsPublic, query.getIsPublic());
            }
            if (StringUtils.hasText(query.getKeyword())) {
                queryWrapper.and(wrapper -> wrapper
                    .like(WorkspaceView::getName, query.getKeyword())
                    .or()
                    .like(WorkspaceView::getDescription, query.getKeyword())
                );
            }

            queryWrapper.orderByDesc(WorkspaceView::getCreateTime);

            page = workspaceViewService.page(page, queryWrapper);
            log.info("[分页查询视图] 成功，共 {} 条", page.getTotal());
            return Result.success(page);
        } catch (Exception e) {
            log.error("[分页查询视图] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }
}
