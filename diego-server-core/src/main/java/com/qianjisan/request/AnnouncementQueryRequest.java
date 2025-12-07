package com.qianjisan.request;

import com.qianjisan.common.dto.PageQuery;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 公告查询请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AnnouncementQueryRequest extends PageQuery {

    /**
     * 公告标题（模糊查询）
     */
    private String title;

    /**
     * 公告内容（模糊查询）
     */
    private String content;

    /**
     * 状态：1-发布，0-草稿
     */
    private Integer status;

    /**
     * 公告类型：0-通知、1-更新、2-维护
     */
    private Integer type;

    /**
     * 是否置顶：0-否，1-是
     */
    private Integer isTop;

    /**
     * 发布者ID
     */
    private Long publisherId;

    /**
     * 发布时间开始
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime publishTimeStart;

    /**
     * 发布时间结束
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime publishTimeEnd;

    /**
     * 创建时间开始
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTimeStart;

    /**
     * 创建时间结束
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTimeEnd;
}
