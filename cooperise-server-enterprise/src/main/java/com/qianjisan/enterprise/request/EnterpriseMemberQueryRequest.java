package com.qianjisan.enterprise.request;

import com.qianjisan.core.request.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * EnterpriseMember 查询请求体
 */
@Data
public class EnterpriseMemberQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long enterpriseId;

    private Long userId;

    private String role;

    private Integer status;

    private String department;
}