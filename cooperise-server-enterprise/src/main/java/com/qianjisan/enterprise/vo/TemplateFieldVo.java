package com.qianjisan.enterprise.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * TemplateField 返回体 (VO)
 */
@Data
public class TemplateFieldVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 模板字段ID
     */
    private Long id;

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 字段编码
     */
    private String fieldCode;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段类型
     */
    private String fieldType;

    /**
     * 字段默认值
     */
    private String fieldDefaultValue;

    /**
     * 是否可编辑
     */
    private Byte isEdit;

    /**
     * 是否必填
     */
    private Byte isRequired;

    /**
     * 提示内容
     */
    private String promptContent;

    /**
     * 字段顺序
     */
    private Integer sort;

    /**
     * 位置
     */
    private String position;

    /**
     * 状态：1-启用 0-禁用
     */
    private Integer status;

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
}
