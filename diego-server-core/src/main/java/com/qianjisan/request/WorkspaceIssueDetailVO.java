package com.qianjisan.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * 事项详情VO（包含扩展字段）
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkspaceIssueDetailVO extends WorkSpaceIssueRequest {

    /**
     * 子任务列表
     */
    private List<WorkspaceSubtaskVO> subtasks;

    /**
     * 评论列表
     */
    private List<WorkspaceIssueCommentVO> comments;

    /**
     * 附件列表
     */
    private List<WorkspaceIssueAttachmentVO> attachments;

    /**
     * 活动记录列表
     */
    private List<WorkspaceIssueActivityVO> activities;

    /**
     * 自定义字段（动态扩展字段）
     */
    private Map<String, Object> customFields;

    /**
     * 关联事项数量
     */
    private Integer relatedIssueCount;

    /**
     * 观察者列表
     */
    private List<UserVO> watchers;
}
