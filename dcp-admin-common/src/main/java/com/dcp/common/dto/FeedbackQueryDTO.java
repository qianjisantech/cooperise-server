package com.dcp.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 反馈查询DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FeedbackQueryDTO extends PageQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 反馈类型：1-bug, 2-feature, 3-improvement, 4-other
     */
    private Integer type;

    /**
     * 状态：1-open, 2-closed
     */
    private Integer status;

    /**
     * 优先级：1-low, 2-medium, 3-high
     */
    private Integer priority;

    /**
     * 提交者ID
     */
    private Long submitterId;

    /**
     * 处理人ID
     */
    private Long assigneeId;

    /**
     * 搜索关键词（标题、内容）
     */
    private String keyword;
}
