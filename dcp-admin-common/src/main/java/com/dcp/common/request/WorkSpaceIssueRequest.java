package com.dcp.common.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 事项视图对象
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class WorkSpaceIssueRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 空间信息（可选，如果传了需要校验内部字段）
     */
    @Valid
    private SpaceDTO space;

    /**
     * 事项单号（可选，可能由系统自动生成）
     */
    private String issueNo;

    /**
     * 事项类型：任务、bug、需求、线上问题
     */
    @NotNull(message = "事项类型不能为空")
    private Integer issueType;

    /**
     * 概要
     */
    @NotBlank(message = "概要不能为空")
    @Size(max = 200, message = "概要长度不能超过200个字符")
    private String summary;

    /**
     * 详细描述（必填）
     */
    @NotBlank(message = "详细描述不能为空")
    @Size(max = 5000, message = "详细描述长度不能超过5000个字符")
    private String description;

    /**
     * 状态：0-已取消，1-待处理、2-进行中、3-已完成
     */
    @NotNull(message = "状态不能为空")
    @Min(value = 0, message = "状态值必须在0-3之间")
    @Max(value = 3, message = "状态值必须在0-3之间")
    private Integer status;

    /**
     * 优先级：1-高、2-中、3-低
     */
    @NotNull(message = "优先级不能为空")
    @Min(value = 1, message = "优先级值必须在1-3之间")
    @Max(value = 3, message = "优先级值必须在1-3之间")
    private Integer priority;

    /**
     * 经办人信息（必填）
     */
    @NotNull(message = "经办人不能为空")
    @Valid
    private AssigneeDTO assignee;

    /**
     * 报告人信息（可选，如果传了需要校验内部字段）
     */
    @Valid
    private ReporterDTO reporter;


    /**
     * 开始日期（必填）
     */
    @NotNull(message = "开始日期不能为空")
    private LocalDate startDate;

    /**
     * 截止日期（必填）
     */
    @NotNull(message = "截止日期不能为空")
    private LocalDate dueDate;

    /**
     * 预估工时（必填）
     */
    @NotNull(message = "预估工时不能为空")
    @DecimalMin(value = "0.0", message = "预估工时不能为负数")
    private BigDecimal estimatedHours;

    /**
     * 实际工时（可选）
     */
    @DecimalMin(value = "0.0", message = "实际工时不能为负数")
    private BigDecimal actualHours;

    /**
     * 进度百分比：0-100（可选）
     */
    @Min(value = 0, message = "进度必须在0-100之间")
    @Max(value = 100, message = "进度必须在0-100之间")
    private Integer progress;

    /**
     * 父事项ID（可选）
     */
    private Long parentId;

    /**
     * 标签（可选）
     */
    @Size(max = 500, message = "标签长度不能超过500个字符")
    private String tags;

    /**
     * 排期信息（JSON格式）（可选）
     */
    private String schedule;

    /**
     * 附件数量（扩展字段，只读）
     */
    @Min(value = 0, message = "附件数量不能为负数")
    private Integer attachmentCount;

    /**
     * 评论数量（扩展字段，只读）
     */
    @Min(value = 0, message = "评论数量不能为负数")
    private Integer commentCount;

    /**
     * 空间信息 DTO
     */
    @Data
    public static class SpaceDTO {
        /**
         * 空间ID
         */
        @NotNull(message = "空间ID不能为空")
        private Long spaceId;

        /**
         * 空间名称
         */
        @NotBlank(message = "空间名称不能为空")
        @Size(max = 100, message = "空间名称长度不能超过100个字符")
        private String spaceName;

        /**
         * 空间关键词
         */
        @Size(max = 50, message = "空间关键词长度不能超过50个字符")
        private String spaceKeyword;
    }

    /**
     * 经办人信息 DTO
     */
    @Data
    public static class AssigneeDTO {
        /**
         * 经办人ID
         */
        @NotNull(message = "经办人ID不能为空")
        private Long assigneeId;

        /**
         * 经办人工号（扩展字段）
         */
        @Size(max = 50, message = "经办人工号长度不能超过50个字符")
        private String assigneeCode;

        /**
         * 经办人姓名（扩展字段）
         */
        @NotBlank(message = "经办人姓名不能为空")
        @Size(max = 100, message = "经办人姓名长度不能超过100个字符")
        private String assigneeName;
    }

    /**
     * 报告人信息 DTO
     */
    @Data
    public static class ReporterDTO {
        /**
         * 报告人ID
         */
        @NotNull(message = "报告人ID不能为空")
        private Long reporterId;

        /**
         * 报告人工号（扩展字段）
         */
        @Size(max = 50, message = "报告人工号长度不能超过50个字符")
        private String reporterCode;

        /**
         * 报告人姓名（扩展字段）
         */
        @NotBlank(message = "报告人姓名不能为空")
        @Size(max = 100, message = "报告人姓名长度不能超过100个字符")
        private String reporterName;
    }
}
