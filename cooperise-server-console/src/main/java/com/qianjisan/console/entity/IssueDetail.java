package com.qianjisan.console.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 事项主表
 * </p>
 *
 * @author cooperise
 * @since 2026-01-17
 */
@Getter
@Setter
@TableName("issue_detail")
public class IssueDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 事项ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 企业id
     */
    @TableField("enterprise_id")
    private Long enterpriseId;

    /**
     * 企业名称
     */
    @TableField("company_name")
    private String companyName;

    /**
     * 企业编码
     */
    @TableField("company_code")
    private String companyCode;

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
     * 模板字段id
     */
    @TableField("template_field_id")
    private Long templateFieldId;

    /**
     * 模板值
     */
    @TableField("value_string")
    private String valueString;

    /**
     * 模板值json
     */
    @TableField("value_json")
    private String valueJson;
}
