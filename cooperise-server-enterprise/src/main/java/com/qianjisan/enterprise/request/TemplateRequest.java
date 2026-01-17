package com.qianjisan.enterprise.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Template 请求体
 */
@Data
public class TemplateRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // code 字段由雪花ID自动生成，无需传递
    private String code;

    @NotBlank(message = "模板名称不能为空")
    private String name;

    private String description;

    @NotNull(message = "企业ID不能为空")
    private Long companyId;

    private String companyCode;

    private String companyName;

    /**
     * 状态：1-启用 0-禁用，默认启用
     */
    private Byte status = 1;

    /**
     * 是否默认模板 1是 0否，默认否
     */
    private Boolean isDefault = false;
}
