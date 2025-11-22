package com.dcp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 空间表
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("space")
public class Space extends BaseEntity {

    /**
     * 空间ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 空间名称
     */
    @TableField("name")
    private String name;

    /**
     * 空间关键词（用于生成事项单号）
     */
    @TableField("keyword")
    private String keyword;

    /**
     * 空间描述
     */
    @TableField("description")
    private String description;

    /**
     * 空间图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 所有者ID
     */
    @TableField("owner_id")
    private Long ownerId;

    /**
     * 所有者工号
     */
    @TableField("owner_code")
    private String ownerCode;

    /**
     * 所有者姓名
     */
    @TableField("owner_name")
    private String ownerName;
}
