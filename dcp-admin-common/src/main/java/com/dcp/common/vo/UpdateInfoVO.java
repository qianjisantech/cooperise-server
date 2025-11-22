package com.dcp.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 系统更新信息VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class UpdateInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否有更新
     */
    private Boolean hasUpdate;

    /**
     * 当前版本
     */
    private String currentVersion;

    /**
     * 最新版本
     */
    private String latestVersion;

    /**
     * 更新说明
     */
    private String message;

    /**
     * 下载链接
     */
    private String downloadUrl;

    /**
     * 公告ID（用于标记已读）
     */
    private Long announcementId;

    /**
     * 公告标题
     */
    private String announcementTitle;

    /**
     * 公告内容
     */
    private String announcementContent;
}
