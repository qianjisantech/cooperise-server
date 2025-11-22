package com.dcp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 通知表
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_notification")
public class SysNotification extends BaseEntity {

    /**
     * 通知ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 接收用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 通知类型：系统通知、事项更新、评论提醒等
     */
    @TableField("type")
    private String type;

    /**
     * 通知标题
     */
    @TableField("title")
    private String title;

    /**
     * 通知内容
     */
    @TableField("content")
    private String content;

    /**
     * 关联链接
     */
    @TableField("link")
    private String link;

    /**
     * 是否已读：0-未读，1-已读
     */
    @TableField("is_read")
    private Integer isRead;

    /**
     * 阅读时间
     */
    @TableField("read_time")
    private LocalDateTime readTime;
}
