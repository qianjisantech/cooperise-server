package com.dcp.common.request;

import lombok.Data;

/**
 * 空间成员请求对象
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class SpaceMemberRequest {

    /**
     * 空间ID
     */
    private Long spaceId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色：admin-管理员，member-成员
     */
    private String role;
}
