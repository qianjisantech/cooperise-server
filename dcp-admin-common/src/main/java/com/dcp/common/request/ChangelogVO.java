package com.dcp.common.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 发布日志VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChangelogVO extends BaseVO {

    /**
     * 版本号
     */
    private String version;

    /**
     * 标题
     */
    private String title;

    /**
     * 发布日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishDate;

    /**
     * 日志内容（JSON格式）
     */
    private String content;

    /**
     * 类型：feature-新功能、bugfix-修复、improvement-改进
     */
    private String type;

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
}
