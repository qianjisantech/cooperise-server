package com.qianjisan.enterprise.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 事项表
 * </p>
 *
 * @author cooperise
 * @since 2026-01-17
 */
@Getter
@Setter
@TableName("template")
public class Template implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 事项模板ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 事项模板编码
     */
    @TableField("code")
    private String code;

    /**
     * 事项模板名称
     */
    @TableField("name")
    private String name;

    /**
     * 模板描述
     */
    @TableField("description")
    private String description;

    /**
     * 企业id
     */
    @TableField("enterprise_id")
    private Long enterpriseId;

    /**
     * 企业编码
     */
    @TableField("company_code")
    private String companyCode;

    /**
     * 企业名称
     */
    @TableField("company_name")
    private String companyName;

    /**
     * 状态：1-启用 0-禁用
     */
    @TableField("status")
    private Byte status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除：0-否，1-是
     */
    @TableField("is_deleted")
    @TableLogic
    private Boolean isDeleted;

    /**
     * 创建人ID
     */
    @TableField("create_by_id")
    private Long createById;

    /**
     * 创建人用户名
     */
    @TableField("create_by_code")
    private String createByCode;

    /**
     * 创建人昵称
     */
    @TableField("create_by_name")
    private String createByName;

    /**
     * 更新人ID
     */
    @TableField("update_by_id")
    private Long updateById;

    /**
     * 更新人用户名
     */
    @TableField("update_by_code")
    private String updateByCode;

    /**
     * 更新人昵称
     */
    @TableField("update_by_name")
    private String updateByName;

    /**
     * 是否默认模板   1是 0否
     */
    @TableField("is_default")
    private Boolean isDefault;
}
