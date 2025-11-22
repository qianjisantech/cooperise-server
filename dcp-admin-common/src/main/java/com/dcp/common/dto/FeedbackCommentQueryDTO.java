package com.dcp.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 反馈评论查询DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FeedbackCommentQueryDTO extends PageQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 反馈ID
     */
    private Long feedbackId;

    /**
     * 评论人ID
     */
    private Long userId;

    /**
     * 搜索关键词（评论内容）
     */
    private String keyword;
}
