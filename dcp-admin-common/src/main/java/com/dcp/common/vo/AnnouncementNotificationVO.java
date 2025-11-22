package com.dcp.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 公告通知VO - 用于WebSocket推送
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class AnnouncementNotificationVO {

    /**
     * 公告ID
     */
    private Long id;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 公告类型：0-通知、1-更新、2-维护
     */
    private Integer type;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;
}
