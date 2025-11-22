package com.dcp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 发布日志表
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_change_log")
public class SysChangelog extends BaseEntity {

    /**
     * 日志ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 版本号
     */
    @TableField("version")
    private String version;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 发布日期
     */
    @TableField("publish_date")
    private LocalDate publishDate;

    /**
     * 日志内容（JSON格式）- 保留用于向后兼容
     */
    @TableField("content")
    private String content;

    /**
     * 新功能内容（富文本HTML）
     */
    @TableField("features_content")
    private String featuresContent;

    /**
     * 优化改进内容（富文本HTML）
     */
    @TableField("improvements_content")
    private String improvementsContent;

    /**
     * 问题修复内容（富文本HTML）
     */
    @TableField("bugfixes_content")
    private String bugfixesContent;

    /**
     * 类型：0-新功能、1-修复、2-改进
     */
    @TableField("type")
    private Integer type;

    /**
     * 发布者ID
     */
    @TableField("publisher_id")
    private Long publisherId;
}
