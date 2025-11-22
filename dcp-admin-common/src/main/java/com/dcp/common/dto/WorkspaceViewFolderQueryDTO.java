package com.dcp.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视图文件夹查询DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkspaceViewFolderQueryDTO extends PageQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 父文件夹ID
     */
    private Long parentId;

    /**
     * 创建者ID
     */
    private Long ownerId;

    /**
     * 搜索关键词（文件夹名称）
     */
    private String keyword;
}
