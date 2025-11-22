package com.dcp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dcp.entity.AnnouncementUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 公告用户关联表 Mapper
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface AnnouncementUserRelationMapper extends BaseMapper<AnnouncementUser> {

    /**
     * 查询用户的最新公告关联记录
     * 关联查询公告表，获取最新的系统更新公告
     *
     * @param userId 用户ID
     * @return 最新的公告关联记录
     */
    @Select("SELECT au.* " +
            "FROM announcement_user au " +
            "INNER JOIN announcement a ON au.announcement_id = a.id " +
            "WHERE au.user_id = #{userId} " +
            "AND a.type = 1 " +
            "AND a.status = 1 " +
            "ORDER BY a.publish_time DESC " +
            "LIMIT 1")
    AnnouncementUser selectLatestByUserId(@Param("userId") Long userId);

    /**
     * 查询用户是否已读某个公告
     *
     * @param userId         用户ID
     * @param announcementId 公告ID
     * @return 关联记录
     */
    @Select("SELECT * FROM announcement_user " +
            "WHERE user_id = #{userId} AND announcement_id = #{announcementId} " +
            "LIMIT 1")
    AnnouncementUser selectByUserIdAndAnnouncementId(
            @Param("userId") Long userId,
            @Param("announcementId") Long announcementId
    );
}
