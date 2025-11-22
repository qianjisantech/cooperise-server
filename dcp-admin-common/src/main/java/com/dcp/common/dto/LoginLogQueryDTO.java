package com.dcp.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录日志查询DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginLogQueryDTO extends PageQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 登录IP
     */
    private String loginIp;

    /**
     * 状态：success-成功，failed-失败
     */
    private String status;

    /**
     * 搜索关键词（登录IP、操作系统、浏览器）
     */
    private String keyword;
}
