package com.dcp.rbac.request;

import com.dcp.common.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserQueryRequest extends PageRequest {

    /**
     * 用户名（模糊查询）
     */
    private String username;

    /**
     * 用户编码（模糊查询）
     */
    private String userCode;

    /**
     * 状态：1-正常，0-禁用
     */
    private Integer status;
}
