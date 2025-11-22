package com.dcp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 顶部通知表
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_top_notification")
public class TopNotification extends BaseEntity {

    /**
     * 顶部通知ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     * 通知类型：1-信息、2-警告、3-错误、4-成功
     */
    @TableField("type")
    private Integer type;

    /**
     * 跳转链接
     */
    @TableField("link")
    private String link;

    /**
     * 状态：1-已发布，0-草稿
     */
    @TableField("status")
    private Integer status;

    /**
     * 发布时间
     */
    @TableField("publish_time")
    private LocalDateTime publishTime;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
     * 发布者ID
     */
    @TableField("publisher_id")
    private Long publisherId;

    /**
     * 发布者编码
     */
    @TableField("publisher_code")
    private String publisherCode;

    /**
     * 发布者名称
     */
    @TableField("publisher_name")
    private String publisherName;
}
