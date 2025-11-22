package com.dcp.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 事项活动查询DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkspaceIssueActivityQueryDTO extends PageQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 事项ID
     */
    private Long issueId;

    /**
     * 操作类型
     */
    private String activityType;

    /**
     * 操作人ID
     */
    private Long userId;

    /**
     * 搜索关键词（活动描述）
     */
    private String keyword;
}
