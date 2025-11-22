package com.dcp.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户设置查询DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserSettingsQueryDTO extends PageQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 语言设置
     */
    private String language;

    /**
     * 主题：light-浅色，dark-深色
     */
    private String theme;

    /**
     * 搜索关键词
     */
    private String keyword;
}
