package com.dcp.common.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 空间成员VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SpaceMemberVO extends BaseVO {

    /**
     * 空间ID
     */
    private Long spaceId;

    /**
     * 空间名称
     */
    private String spaceName;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 角色：owner-所有者，admin-管理员，member-成员
     */
    private String role;

    /**
     * 加入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime joinTime;
}
