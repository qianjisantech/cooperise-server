package com.dcp.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 子任务查询DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkspaceSubtaskQueryDTO extends PageQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 所属事项ID
     */
    private Long issueId;

    /**
     * 是否完成：0-未完成，1-已完成
     */
    private Integer completed;

    /**
     * 搜索关键词（标题）
     */
    private String keyword;
}
