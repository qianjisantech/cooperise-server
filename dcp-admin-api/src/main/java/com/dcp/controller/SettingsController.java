package com.dcp.controller;

import com.dcp.common.Result;
import com.dcp.common.context.UserContext;
import com.dcp.common.context.UserContextHolder;
import com.dcp.common.request.ChangePasswordRequest;
import com.dcp.common.request.UpdateAccountSettingsRequest;
import com.dcp.common.request.UpdateNotificationSettingsRequest;
import com.dcp.common.request.UpdateSystemSettingsRequest;
import com.dcp.common.vo.LoginLogVO;
import com.dcp.common.vo.PageVO;
import com.dcp.common.vo.UserSettingsGroupedVO;
import com.dcp.common.vo.UserSettingsResponseVO;
import com.dcp.service.ISettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 设置控制器
 * 处理用户设置相关的HTTP请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "设置管理", description = "用户设置相关接口")
@RestController
@RequestMapping("/settings")
@RequiredArgsConstructor
@Slf4j
public class SettingsController {

    private final ISettingsService settingsService;

    /**
     * 获取当前用户的完整设置信息（分组格式）
     */
    @Operation(summary = "获取用户设置")
    @GetMapping("/user")
    public Result<UserSettingsGroupedVO> getUserSettings() {
        try {
            Long userId = getCurrentUserId();
            log.info("[获取用户设置] 用户ID: {}", userId);
            UserSettingsGroupedVO settings = settingsService.getUserSettingsGrouped(userId);
            return Result.success(settings);
        } catch (Exception e) {
            log.error("[获取用户设置] 失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新账号设置
     */
    @Operation(summary = "更新账号设置")
    @PutMapping("/account")
    public Result<Void> updateAccountSettings(@Valid @RequestBody UpdateAccountSettingsRequest request) {
        try {
            Long userId = getCurrentUserId();
            log.info("[更新账号设置] 用户ID: {}, 请求参数: {}", userId, request);
            settingsService.updateAccountSettings(userId, request);
            log.info("[更新账号设置] 成功");
            return Result.success();
        } catch (Exception e) {
            log.error("[更新账号设置] 失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新通知设置
     */
    @Operation(summary = "更新通知设置")
    @PutMapping("/notification")
    public Result<Void> updateNotificationSettings(@RequestBody UpdateNotificationSettingsRequest request) {
        try {
            Long userId = getCurrentUserId();
            log.info("[更新通知设置] 用户ID: {}, 请求参数: {}", userId, request);
            settingsService.updateNotificationSettings(userId, request);
            log.info("[更新通知设置] 成功");
            return Result.success();
        } catch (Exception e) {
            log.error("[更新通知设置] 失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新系统设置
     */
    @Operation(summary = "更新系统设置")
    @PutMapping("/system")
    public Result<Void> updateSystemSettings(@RequestBody UpdateSystemSettingsRequest request) {
        try {
            Long userId = getCurrentUserId();
            log.info("[更新系统设置] 用户ID: {}, 请求参数: {}", userId, request);
            settingsService.updateSystemSettings(userId, request);
            log.info("[更新系统设置] 成功");
            return Result.success();
        } catch (Exception e) {
            log.error("[更新系统设置] 失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 修改密码
     */
    @Operation(summary = "修改密码")
    @PutMapping("/security/password")
    public Result<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        try {
            Long userId = getCurrentUserId();
            log.info("[修改密码] 用户ID: {}", userId);
            settingsService.changePassword(userId, request);
            log.info("[修改密码] 成功");
            return Result.success();
        } catch (Exception e) {
            log.error("[修改密码] 失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 上传头像
     */
    @Operation(summary = "上传头像")
    @PostMapping("/account/avatar")
    public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        try {
            Long userId = getCurrentUserId();
            log.info("[上传头像] 用户ID: {}, 文件名: {}", userId, file.getOriginalFilename());
            String avatarUrl = settingsService.uploadAvatar(userId, file);
            log.info("[上传头像] 成功，URL: {}", avatarUrl);
            return Result.success(avatarUrl);
        } catch (Exception e) {
            log.error("[上传头像] 失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取登录日志
     */
    @Operation(summary = "获取登录日志")
    @GetMapping("/security/login-logs")
    public Result<PageVO<LoginLogVO>> getLoginLogs(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {
        try {
            Long userId = getCurrentUserId();
            log.info("[获取登录日志] 用户ID: {}, 页码: {}, 每页大小: {}", userId, current, size);
            PageVO<LoginLogVO> logs = settingsService.getLoginLogs(userId, current, size);
            log.info("[获取登录日志] 成功，共 {} 条", logs.getTotal());
            return Result.success(logs);
        } catch (Exception e) {
            log.error("[获取登录日志] 失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 启用双重认证
     */
    @Operation(summary = "启用双重认证")
    @PutMapping("/security/two-factor")
    public Result<Void> updateTwoFactor(@RequestParam Boolean enabled) {
        try {
            Long userId = getCurrentUserId();
            log.info("[更新双重认证] 用户ID: {}, 启用状态: {}", userId, enabled);

            if (enabled) {
                settingsService.enableTwoFactor(userId);
            } else {
                settingsService.disableTwoFactor(userId);
            }

            log.info("[更新双重认证] 成功");
            return Result.success();
        } catch (Exception e) {
            log.error("[更新双重认证] 失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取当前登录用户ID
     */
    private Long getCurrentUserId() {
        UserContext userContext = UserContextHolder.getUser();
        if (userContext == null || userContext.getUserId() == null) {
            throw new RuntimeException("未登录或登录已过期");
        }
        return userContext.getUserId();
    }
}
