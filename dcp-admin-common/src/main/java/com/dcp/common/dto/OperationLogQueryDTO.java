package com.dcp.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 操作日志查询DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OperationLogQueryDTO extends PageQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 操作用户ID
     */
    private Long userId;

    /**
     * 操作用户名
     */
    private String username;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求路径(模糊查询)
     */
    private String requestUrl;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 关键词搜索
     */
    private String keyword;
}
