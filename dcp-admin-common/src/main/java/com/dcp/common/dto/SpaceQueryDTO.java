package com.dcp.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 空间查询DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SpaceQueryDTO extends PageQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 空间名称
     */
    private String name;

    /**
     * 空间编码
     */
    private String code;

    /**
     * 所有者ID
     */
    private Long ownerId;

    /**
     * 搜索关键词（名称、编码、描述）
     */
    private String keyword;
}
