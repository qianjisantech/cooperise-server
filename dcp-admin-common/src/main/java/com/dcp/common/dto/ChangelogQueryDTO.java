package com.dcp.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 更新日志查询DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChangelogQueryDTO extends PageQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 版本号
     */
    private String version;

    /**
     * 类型：feature-新功能、bugfix-修复、improvement-改进
     */
    private String type;

    /**
     * 发布者ID
     */
    private Long publisherId;

    /**
     * 搜索关键词（标题、内容）
     */
    private String keyword;
}
