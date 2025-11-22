package com.dcp.controller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcp.common.Result;
import com.dcp.common.annotation.RequiresPermission;
import com.dcp.common.context.UserContextHolder;
import com.dcp.common.request.AnnouncementQueryRequest;
import com.dcp.common.request.AnnouncementRequest;
import com.dcp.common.vo.AnnouncementVO;
import com.dcp.entity.Announcement;
import com.dcp.service.IAnnouncementService;
import com.dcp.service.IAnnouncementUserRelationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公告管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "公告管理", description = "Announcement相关接口")
@RestController
@RequestMapping("/announcement")
@RequiredArgsConstructor
@Slf4j
public class AnnouncementController {

    private final IAnnouncementService announcementService;
    private final IAnnouncementUserRelationService relationService;

    @Operation(summary = "创建公告管理")
    @RequiresPermission("announcement:add")
    @PostMapping
    public Result<Void> create(@RequestBody AnnouncementRequest request) {
        try {
            announcementService.saveAnnouncement(request);
            return Result.success();
        }catch (Exception e){
            log.error("创建公告失败，失败原因：{}",e.getMessage());
            return Result.error(e.getMessage());
        }

    }

    @Operation(summary = "更新公告管理")
    @RequiresPermission("announcement:edit")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody AnnouncementRequest request) {
      try {
          announcementService.updateAnnouncementById(id,request);
          return Result.success();
      }catch (Exception e){
          log.error("更新公告失败,失败原因：{}",e.getMessage());
          return Result.error(e.getMessage());
      }

    }

    @Operation(summary = "删除公告管理")
    @RequiresPermission("announcement:delete")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
     try {
         announcementService.removeById(id);
         return Result.success();
     }catch (Exception e){
         log.error("删除公告失败，失败原因：{}",e.getMessage());
         return Result.error(e.getMessage());
     }
    }

    @Operation(summary = "查询公告管理列表")
    @RequiresPermission("announcement:view")
    @GetMapping("/list")
    public Result<List<Announcement>> list() {
        List<Announcement> list = announcementService.list();
        return Result.success(list);
    }

    @Operation(summary = "分页查询公告管理")
    @RequiresPermission("announcement:view")
    @PostMapping("/page")
    public Result<Page<AnnouncementVO>> page(@RequestBody AnnouncementQueryRequest request) {
        log.info("分页查询公告管理入参:{}", JSON.toJSONString(request));
        try {
            Page<AnnouncementVO> page = announcementService.queryPage(request);
            return Result.success(page);
        } catch (Exception e) {
            log.error("分页查询公告管理失败：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "标记公告为已读")

    @PostMapping("/mark-read/{announcementId}")
    public Result<Void> markAnnouncementAsRead(@PathVariable Long announcementId) {
        try {
            // 获取当前用户ID
            Long userId = UserContextHolder.getUserId();

            if (userId == null) {
                return Result.error("用户未登录");
            }

            // 1. 获取公告详情
            Announcement announcement = announcementService.getById(announcementId);
            if (announcement == null) {
                return Result.error("公告不存在");
            }

            // 2. 提取公告的版本号
            String latestVersion = extractVersionFromTitle(announcement.getTitle());

            // 3. 获取用户当前版本
            String currentVersion = "1.0.0"; // 默认版本
            var latestRelation = relationService.getLatestByUserId(userId);
            if (latestRelation != null && latestRelation.getLatestVersion() != null) {
                currentVersion = latestRelation.getLatestVersion();
            }

            // 4. 更新或创建关联记录
            boolean success = relationService.updateOrCreateRelation(userId, announcementId, currentVersion, latestVersion);

            if (success) {
                log.info("标记公告为已读成功: userId={}, announcementId={}, version: {} -> {}",
                        userId, announcementId, currentVersion, latestVersion);
                return Result.success();
            } else {
                log.error("标记公告为已读失败: userId={}, announcementId={}", userId, announcementId);
                return Result.error("标记已读失败");
            }
        } catch (Exception e) {
            log.error("标记公告为已读异常: announcementId={}", announcementId, e);
            return Result.error("操作失败: " + e.getMessage());
        }
    }

    /**
     * 从公告标题中提取版本号
     * 支持格式: "v1.1.0 系统更新"、"系统更新 v1.1.0"、"1.1.0版本更新"等
     *
     * @param title 公告标题
     * @return 版本号，如果无法提取则返回默认版本
     */
    private String extractVersionFromTitle(String title) {
        if (title == null || title.isEmpty()) {
            return "1.0.1";
        }

        // 使用正则表达式提取版本号: v?数字.数字.数字
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("v?(\\d+\\.\\d+\\.\\d+)");
        java.util.regex.Matcher matcher = pattern.matcher(title);

        if (matcher.find()) {
            return matcher.group(1);
        }

        // 如果无法提取版本号，返回默认值
        return "1.0.1";
    }
}
