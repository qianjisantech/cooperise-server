package com.dcp.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视图查询DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkspaceViewQueryDTO extends PageQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 空间ID
     */
    private Long spaceId;

    /**
     * 文件夹ID
     */
    private Long folderId;

    /**
     * 视图类型
     */
    private String type;

    /**
     * 创建者ID
     */
    private Long ownerId;

    /**
     * 是否公共：0-私有，1-公共
     */
    private Integer isPublic;

    /**
     * 搜索关键词（视图名称）
     */
    private String keyword;
}
