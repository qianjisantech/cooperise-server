package com.dcp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Banner通知表
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_banner_notification")
public class BannerNotification extends BaseEntity {

    /**
     * 通知ID
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
     * 状态：1-激活、0-未激活
     */
    @TableField("status")
    private Integer status;

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
}
