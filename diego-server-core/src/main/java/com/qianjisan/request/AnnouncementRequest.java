package com.qianjisan.request;

import lombok.Data;

/**
 * 公告VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class AnnouncementRequest {
    /**
     * 公告id
     */
    private  Long id;
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
     * 状态：0-草稿,1-发布
     */
    private Integer status;

    /**
     * 是否置顶：false-否，true-是
     */
    private Boolean isTop;

}
