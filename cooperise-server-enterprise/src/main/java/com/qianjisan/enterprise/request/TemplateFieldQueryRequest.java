package com.qianjisan.enterprise.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * TemplateField 分页查询请求
 */
@Data
public class TemplateFieldQueryRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Min(1)
    private Long current = 1L;

    @Min(1)
    private Long size = 10L;

    /**
     * 模板ID
     */
    private String templateId;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段编码
     */
    private String fieldCode;

    /**
     * 字段类型
     */
    private String fieldType;

    /**
     * 是否可编辑 1是 0否
     */
    private Byte isEdit;

    /**
     * 是否必填 1是 0否
     */
    private Byte isRequired;

    /**
     * 状态：1-启用 0-禁用
     */
    private Integer status;

    /**
     * 位置
     */
    private String position;
}
