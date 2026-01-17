package com.qianjisan.enterprise.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serializable;

/**
 * Template 分页查询请求
 */
@Data
public class TemplateQueryRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Min(1)
    private Long current = 1L;

    @Min(1)
    private Long size = 10L;

    /**
     * 模板编码
     */
    private String code;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板描述
     */
    private String description;

    /**
     * 企业ID
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
     * 是否默认模板 1是 0否
     */
    private Boolean isDefault;
}
