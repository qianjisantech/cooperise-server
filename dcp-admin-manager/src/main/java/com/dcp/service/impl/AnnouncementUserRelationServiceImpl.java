package com.dcp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.entity.AnnouncementUser;
import com.dcp.mapper.AnnouncementUserRelationMapper;
import com.dcp.service.IAnnouncementUserRelationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 公告用户关联服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AnnouncementUserRelationServiceImpl extends ServiceImpl<AnnouncementUserRelationMapper, AnnouncementUser>
        implements IAnnouncementUserRelationService {

    private final AnnouncementUserRelationMapper relationMapper;

    @Override
    public AnnouncementUser getLatestByUserId(Long userId) {
        return relationMapper.selectLatestByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOrCreateRelation(Long userId, Long announcementId, String currentVersion, String latestVersion) {
        // 查询是否已存在关联记录
        AnnouncementUser existing = relationMapper.selectByUserIdAndAnnouncementId(userId, announcementId);

        if (existing != null) {
            // 更新已有记录
            existing.setHasUpdate(1); // 标记为已更新
            existing.setCurrentVersion(currentVersion);
            existing.setLatestVersion(latestVersion);

            boolean result = this.updateById(existing);

            if (result) {
                log.info("更新用户公告关联成功: userId={}, announcementId={}, version={}->{}",
                        userId, announcementId, currentVersion, latestVersion);
            } else {
                log.error("更新用户公告关联失败: userId={}, announcementId={}", userId, announcementId);
            }

            return result;
        } else {
            // 创建新记录
            AnnouncementUser relation = new AnnouncementUser();
            relation.setUserId(userId);
            relation.setAnnouncementId(announcementId);
            relation.setHasUpdate(1); // 标记为已更新
            relation.setCurrentVersion(currentVersion);
            relation.setLatestVersion(latestVersion);

            boolean result = this.save(relation);

            if (result) {
                log.info("创建用户公告关联成功: userId={}, announcementId={}, version={}->{}",
                        userId, announcementId, currentVersion, latestVersion);
            } else {
                log.error("创建用户公告关联失败: userId={}, announcementId={}", userId, announcementId);
            }

            return result;
        }
    }

    @Override
    public boolean hasUserViewedAnnouncement(Long userId, Long announcementId) {
        AnnouncementUser relation = relationMapper.selectByUserIdAndAnnouncementId(userId, announcementId);
        return relation != null && relation.getHasUpdate() == 1;
    }
}
