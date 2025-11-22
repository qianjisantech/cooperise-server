package com.dcp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 公告和用户关联表
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@TableName("announcement_user")
public class AnnouncementUser {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 公告ID
     */
    @TableField("announcement_id")
    private Long announcementId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 是否已更新：0-未更新、1-已更新
     */
    @TableField("has_update")
    private Integer hasUpdate;

    /**
     * 当前版本
     */
    @TableField("current_version")
    private String currentVersion;

    /**
     * 最新版本
     */
    @TableField("latest_version")
    private String latestVersion;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
