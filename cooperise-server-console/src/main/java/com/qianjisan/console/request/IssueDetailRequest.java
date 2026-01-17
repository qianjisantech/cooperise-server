package com.qianjisan.console.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * IssueDetail 请求体
 */
@Data
public class IssueDetailRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "企业ID不能为空")
    private Long companyId;

    private String companyCode;

    private String companyName;

    @NotNull(message = "模板字段ID不能为空")
    private Long templateFieldId;

    private String valueString;

    private String valueJson;
}
