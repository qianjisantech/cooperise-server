package com.dcp.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 空间成员查询DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SpaceMemberQueryDTO extends PageQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 空间ID
     */
    private Long spaceId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色：owner-所有者、admin-管理员、member-成员
     */
    private String role;

    /**
     * 搜索关键词
     */
    private String keyword;
}
