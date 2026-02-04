package com.qianjisan.enterprise.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qianjisan.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 企业成员实体
 *
 * 对应表：enterprise_member
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("enterprise_member")
public class EnterpriseMember extends BaseEntity {

    /** 主键ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 企业ID */
    @TableField("enterprise_id")
    private Long enterpriseId;

    /** 用户ID */
    @TableField("user_id")
    private Long userId;

    /** 角色：admin-管理员, member-成员, guest-访客 */
    @TableField("role")
    private String role;

    /** 状态：0-禁用, 1-正常, 2-待审核 */
    @TableField("status")
    private Integer status;

    /** 职位 */
    @TableField("position")
    private String position;

    /** 部门 */
    @TableField("department")
    private String department;

    /** 加入时间 */
    @TableField("join_time")
    private LocalDateTime joinTime;
}