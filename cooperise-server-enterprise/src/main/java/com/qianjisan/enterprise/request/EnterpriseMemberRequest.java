package com.qianjisan.enterprise.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * EnterpriseMember 请求体
 */
@Data
public class EnterpriseMemberRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "enterpriseId 不能为空")
    private Long enterpriseId;

    @NotNull(message = "userId 不能为空")
    private Long userId;

    private String role;

    private Integer status;

    private String position;

    private String department;
}