package com.qianjisan.enterprise.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Template 返回体 (VO)
 */
@Data
public class TemplateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 事项模板ID
     */
    private Long id;

    /**
     * 事项模板编码
     */
    private String code;

    /**
     * 事项模板名称
     */
    private String name;

    /**
     * 模板描述
     */
    private String description;

    /**
     * 企业id
     */
    private Long companyId;

    /**
     * 企业编码
     */
    private String companyCode;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 状态：1-启用 0-禁用
     */
    private Byte status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人ID
     */
    private Long createById;

    /**
     * 创建人用户名
     */
    private String createByCode;

    /**
     * 创建人昵称
     */
    private String createByName;

    /**
     * 更新人ID
     */
    private Long updateById;

    /**
     * 更新人用户名
     */
    private String updateByCode;

    /**
     * 更新人昵称
     */
    private String updateByName;

    /**
     * 是否默认模板   1是 0否
     */
    private Boolean isDefault;
}
