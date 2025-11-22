package com.dcp.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知查询DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NotificationQueryDTO extends PageQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 接收用户ID
     */
    private Long userId;

    /**
     * 通知类型
     */
    private String type;

    /**
     * 是否已读：0-未读，1-已读
     */
    private Integer isRead;

    /**
     * 搜索关键词（标题、内容）
     */
    private String keyword;
}
