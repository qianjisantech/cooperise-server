package com.dcp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 空间成员表
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("space_member")
public class SpaceMember extends BaseEntity {

    /**
     * 成员ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 空间ID
     */
    @TableField("space_id")
    private Long spaceId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 角色：owner-所有者，admin-管理员，member-成员
     */
    @TableField("role")
    private String role;

    /**
     * 加入时间
     */
    @TableField(value = "join_time", fill = FieldFill.INSERT)
    private LocalDateTime joinTime;
}
