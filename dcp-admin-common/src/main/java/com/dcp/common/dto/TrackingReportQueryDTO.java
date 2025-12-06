package com.dcp.common.dto;

import lombok.Data;

/**
 * 埋点报表查询DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class TrackingReportQueryDTO {

    /**
     * 时间维度类型：day(日), month(月), year(年)
     */
    private String timeType;

    /**
     * 开始时间（格式：yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss）
     */
    private String startTime;

    /**
     * 结束时间（格式：yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss）
     */
    private String endTime;
}


