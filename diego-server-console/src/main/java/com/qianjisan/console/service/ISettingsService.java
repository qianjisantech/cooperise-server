package com.qianjisan.console.service;

import com.qianjisan.core.PageVO;
import com.qianjisan.request.ChangePasswordRequest;
import com.qianjisan.request.UpdateAccountSettingsRequest;
import com.qianjisan.request.UpdateNotificationSettingsRequest;
import com.qianjisan.request.UpdateSystemSettingsRequest;
import com.qianjisan.console.vo.LoginLogVO;

import com.qianjisan.console.vo.UserSettingsGroupedVO;
import com.qianjisan.console.vo.UserSettingsResponseVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 设置服务接口
 * 处理用户设置相关的业务逻辑
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface ISettingsService {

    /**
     * 获取当前用户的完整设置信息
     * 包含账号信息、通知设置、系统设置等
     *
     * @param userId 用户ID
     * @return 用户设置完整信息
     */
    UserSettingsResponseVO getUserSettings(Long userId);

    /**
     * 获取当前用户的设置信息（分组格式）
     * 返回按功能模块分组的设置数据，用于前端展示
     *
     * @param userId 用户ID
     * @return 分组后的用户设置信息
     */
    UserSettingsGroupedVO getUserSettingsGrouped(Long userId);

    /**
     * 更新账号设置
     *
     * @param userId 用户ID
     * @param request 账号设置更新请求
     */
    void updateAccountSettings(Long userId, UpdateAccountSettingsRequest request);

    /**
     * 更新通知设置
     *
     * @param userId 用户ID
     * @param request 通知设置更新请求
     */
    void updateNotificationSettings(Long userId, UpdateNotificationSettingsRequest request);

    /**
     * 更新系统设置
     *
     * @param userId 用户ID
     * @param request 系统设置更新请求
     */
    void updateSystemSettings(Long userId, UpdateSystemSettingsRequest request);

    /**
     * 修改密码
     *
     * @param userId 用户ID
     * @param request 密码修改请求
     */
    void changePassword(Long userId, ChangePasswordRequest request);

    /**
     * 上传头像
     *
     * @param userId 用户ID
     * @param file 头像文件
     * @return 头像URL
     */
    String uploadAvatar(Long userId, MultipartFile file);

    /**
     * 获取登录日志（分页）
     *
     * @param userId 用户ID
     * @param current 当前页
     * @param size 每页大小
     * @return 登录日志分页数据
     */
    PageVO<LoginLogVO> getLoginLogs(Long userId, Long current, Long size);

    /**
     * 启用双重认证
     *
     * @param userId 用户ID
     */
    void enableTwoFactor(Long userId);

    /**
     * 禁用双重认证
     *
     * @param userId 用户ID
     */
    void disableTwoFactor(Long userId);
}
