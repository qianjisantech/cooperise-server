package com.dcp.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询基础DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码，默认第1页
     */
    private Integer current = 1;

    /**
     * 每页显示条数，默认10条
     */
    private Integer size = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序方式：0-升序，1-降序
     */
    private Integer sortMethod;
}
