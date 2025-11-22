package com.dcp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 系统更新表
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_system_update")
public class SystemUpdate extends BaseEntity {

    /**
     * 系统更新ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 更新标题
     */
    @TableField("title")
    private String title;

    /**
     * 更新内容
     */
    @TableField("content")
    private String content;

    /**
     * 版本号
     */
    @TableField("version")
    private String version;

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
