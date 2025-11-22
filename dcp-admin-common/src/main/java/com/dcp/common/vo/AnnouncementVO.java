package com.dcp.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 公告VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AnnouncementVO extends BaseVO {

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
     * 状态：1-发布，0-草稿
     */
    private Integer status;

    /**
     * 是否置顶：0-否，1-是
     */
    private Integer isTop;

    /**
     * 发布者ID
     */
    private Long publisherId;

    /**
     * 发布者用户名
     */
    private String publisherUsername;

    /**
     * 发布者昵称
     */
    private String publisherNickname;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;
}
