package com.dcp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 公告表
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("announcement")
public class Announcement extends BaseEntity {

    /**
     * 公告ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 公告标题
     */
    @TableField("title")
    private String title;

    /**
     * 公告内容
     */
    @TableField("content")
    private String content;

    /**
     * 公告类型：0-通知、1-更新、2-维护
     */
    @TableField("type")
    private Integer type;

    /**
     * 状态：1-发布，0-草稿
     */
    @TableField("status")
    private Integer status;

    /**
     * 是否置顶：0-否，1-是
     */
    @TableField("is_top")
    private Integer isTop;

    /**
     * 发布者ID
     */
    @TableField("publisher_id")
    private Long publisherId;



    /**
     * 发布者名字
     */
    @TableField("publisher_name")
    private Long publisher_name;


    /**
     * 发布者code
     */
    @TableField("publisher_code")
    private String publisher_code;

    /**
     * 发布时间
     */
    @TableField("publish_time")
    private LocalDateTime publishTime;

    /**
     * 发布时间
     */
    @TableField("announcement_version")
    private String announcement_version;
}
