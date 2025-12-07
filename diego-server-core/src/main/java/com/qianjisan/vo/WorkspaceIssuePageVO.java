package com.qianjisan.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 事项视图对象
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkspaceIssuePageVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 事项单号
     */
    private String issueNo;

    /**
     * 所属空间ID
     */
    private Long spaceId;

    /**
     * 空间名称（扩展字段）
     */
    private String spaceName;

    /**
     * 事项类型：1-任务、2-bug、3-需求、4-线上问题
     */
    private Integer issueType;

    /**
     * 概要
     */
    private String summary;

    /**
     * 状态：0-已完成、1-待处理、2-进行中、3-待审核
     */
    private Integer status;

    /**
     * 优先级：1-高、2-中、3-低
     */
    private Integer priority;

    /**
     * 经办人ID
     */
    private Long assigneeId;

    /**
     * 经办人工号（扩展字段）
     */
    private String assigneeCode;

    /**
     * 经办人姓名（扩展字段）
     */
    private String assigneeName;

    /**
     * 报告人ID
     */
    private Long reporterId;

    /**
     * 报告人工号（扩展字段）
     */
    private String reporterCode;

    /**
     * 报告人姓名（扩展字段）
     */
    private String reporterName;

    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /**
     * 截止日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    /**
     * 预估工时
     */
    private BigDecimal estimatedHours;

    /**
     * 实际工时
     */
    private BigDecimal actualHours;

    /**
     * 进度百分比：0-100
     */
    private Integer progress;

    /**
     * 标签
     */
    private String tags;

}
