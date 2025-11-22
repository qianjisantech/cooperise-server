package com.dcp.common.dto;

import lombok.Data;

/**
 * 埋点日志查询DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class TrackingLogQueryDTO {

    /**
     * 当前页
     */
    private Long current = 1L;

    /**
     * 每页大小
     */
    private Long size = 10L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 事件名称
     */
    private String eventName;

    /**
     * 页面URL
     */
    private String pageUrl;

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
