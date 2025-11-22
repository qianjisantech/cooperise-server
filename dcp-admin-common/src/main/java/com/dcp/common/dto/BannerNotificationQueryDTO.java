package com.dcp.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 横幅通知查询DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BannerNotificationQueryDTO extends PageQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 状态：active-激活，inactive-未激活
     */
    private String status;

    /**
     * 类型
     */
    private String type;

    /**
     * 搜索关键词（标题、内容）
     */
    private String keyword;
}
