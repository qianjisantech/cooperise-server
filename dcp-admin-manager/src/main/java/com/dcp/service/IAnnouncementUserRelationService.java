package com.dcp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.entity.AnnouncementUser;

/**
 * 公告用户关联服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IAnnouncementUserRelationService extends IService<AnnouncementUser> {

    /**
     * 查询用户的最新公告关联记录
     *
     * @param userId 用户ID
     * @return 最新的公告关联记录
     */
    AnnouncementUser getLatestByUserId(Long userId);

    /**
     * 更新或创建用户公告关联记录
     * 用于记录用户已查看某个公告，并更新版本信息
     *
     * @param userId         用户ID
     * @param announcementId 公告ID
     * @param currentVersion 当前版本
     * @param latestVersion  最新版本
     * @return 是否成功
     */
    boolean updateOrCreateRelation(Long userId, Long announcementId, String currentVersion, String latestVersion);

    /**
     * 检查用户是否已查看某个公告
     *
     * @param userId         用户ID
     * @param announcementId 公告ID
     * @return 是否已查看
     */
    boolean hasUserViewedAnnouncement(Long userId, Long announcementId);
}
