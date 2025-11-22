package com.dcp.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 事项评论查询DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkspaceIssueCommentQueryDTO extends PageQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 事项ID
     */
    private Long issueId;

    /**
     * 评论人ID
     */
    private Long userId;

    /**
     * 父评论ID（用于查询子评论）
     */
    private Long parentId;

    /**
     * 搜索关键词（评论内容）
     */
    private String keyword;
}
