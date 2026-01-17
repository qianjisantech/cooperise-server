package com.qianjisan.console.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serializable;

/**
 * IssueDetail 分页查询请求
 */
@Data
public class IssueDetailQueryRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Min(1)
    private Long current = 1L;

    @Min(1)
    private Long size = 10L;

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
     * 模板字段ID
     */
    private Long templateFieldId;

    /**
     * 模板值（字符串）
     */
    private String valueString;

    /**
     * 模板值JSON
     */
    private String valueJson;
}
