package com.dcp.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 事项附件查询DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkspaceIssueAttachmentQueryDTO extends PageQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 事项ID
     */
    private Long issueId;

    /**
     * 上传者ID
     */
    private Long uploaderId;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 搜索关键词（文件名）
     */
    private String keyword;
}
