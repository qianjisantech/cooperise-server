package com.dcp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcp.common.Result;
import com.dcp.common.annotation.RequiresPermission;
import com.dcp.common.dto.FeedbackQueryDTO;
import com.dcp.common.vo.FeedbackVO;
import com.dcp.entity.Feedback;
import com.dcp.entity.FeedbackComment;
import com.dcp.service.IFeedbackService;
import com.dcp.service.IFeedbackCommentService;
import com.dcp.service.IFeedbackLikeService;
import com.dcp.common.context.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 反馈管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "反馈管理", description = "Feedback相关接口")
@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
@Slf4j
public class FeedbackController {

    private final IFeedbackService feedbackService;
    private final IFeedbackCommentService feedbackCommentService;
    private final IFeedbackLikeService feedbackLikeService;

    @Operation(summary = "创建反馈管理")
    @RequiresPermission("feedback:add")
    @PostMapping
    public Result<Feedback> create(@RequestBody Feedback entity) {
        try {
            log.info("[创建反馈] 请求参数: {}", entity);

            // 参数校验
            if (!StringUtils.hasText(entity.getTitle())) {
                return Result.error("反馈标题不能为空");
            }
            if (!StringUtils.hasText(entity.getContent())) {
                return Result.error("反馈内容不能为空");
            }

            feedbackService.save(entity);
            log.info("[创建反馈] 成功，反馈ID: {}", entity.getId());
            return Result.success(entity);
        } catch (Exception e) {
            log.error("[创建反馈] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新反馈管理")
    @RequiresPermission("feedback:edit")
    @PutMapping("/{id}")
    public Result<Feedback> update(@PathVariable Long id, @RequestBody Feedback entity) {
        try {
            log.info("[更新反馈] ID: {}, 请求参数: {}", id, entity);

            // 检查反馈是否存在
            Feedback existFeedback = feedbackService.getById(id);
            if (existFeedback == null) {
                return Result.error("反馈不存在");
            }

            entity.setId(id);
            feedbackService.updateById(entity);
            log.info("[更新反馈] 成功");
            return Result.success(entity);
        } catch (Exception e) {
            log.error("[更新反馈] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除反馈管理")
    @RequiresPermission("feedback:delete")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            log.info("[删除反馈] ID: {}", id);

            // 检查反馈是否存在
            Feedback feedback = feedbackService.getById(id);
            if (feedback == null) {
                return Result.error("反馈不存在");
            }

            feedbackService.removeById(id);
            log.info("[删除反馈] 成功");
            return Result.success();
        } catch (Exception e) {
            log.error("[删除反馈] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询反馈管理")
    @RequiresPermission("feedback:view")
    @GetMapping("/{id}")
    public Result<Feedback> getById(@PathVariable Long id) {
        try {
            log.info("[查询反馈] ID: {}", id);
            Feedback entity = feedbackService.getById(id);
            if (entity == null) {
                return Result.error("反馈不存在");
            }
            return Result.success(entity);
        } catch (Exception e) {
            log.error("[查询反馈] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "查询反馈管理列表")
    @RequiresPermission("feedback:view")
    @GetMapping("/list")
    public Result<List<FeedbackVO>> list() {
        try {
            log.info("[查询反馈列表]");
            Long currentUserId = UserContextHolder.getUserId();
            List<Feedback> list = feedbackService.list();
            // 转换为 VO 并添加评论数、点赞数和点赞状态
            List<FeedbackVO> voList = list.stream()
                .map(feedback -> {
                    FeedbackVO vo = new FeedbackVO();
                    BeanUtils.copyProperties(feedback, vo);
                    // 统计评论数（如果feedback表中没有comments字段）
                    Integer commentCount = Math.toIntExact(feedbackCommentService.count(
                        new LambdaQueryWrapper<FeedbackComment>()
                            .eq(FeedbackComment::getFeedbackId, feedback.getId())
                    ));
                    vo.setComments(commentCount);
                    // 设置点赞数
                    vo.setLikes(feedback.getLikes() != null ? feedback.getLikes() : 0);
                    // 设置当前用户是否已点赞
                    vo.setLiked(feedbackLikeService.isLiked(feedback.getId(), currentUserId));
                    return vo;
                })
                .collect(Collectors.toList());
            return Result.success(voList);
        } catch (Exception e) {
            log.error("[查询反馈列表] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询反馈管理")
    @RequiresPermission("feedback:view")
    @PostMapping("/page")
    public Result<Page<FeedbackVO>> page(@RequestBody FeedbackQueryDTO query) {
        log.info("[分页查询反馈] 查询参数: {}", query);
        try {
            Long currentUserId = UserContextHolder.getUserId();
            Page<Feedback> page = new Page<>(query.getCurrent(), query.getSize());

            LambdaQueryWrapper<Feedback> queryWrapper = new LambdaQueryWrapper<>();

            if (query.getType() != null) {
                queryWrapper.eq(Feedback::getType, query.getType());
            }
            if (query.getStatus() != null) {
                queryWrapper.eq(Feedback::getStatus, query.getStatus());
            }
            if (query.getPriority() != null) {
                queryWrapper.eq(Feedback::getPriority, query.getPriority());
            }
            if (query.getSubmitterId() != null) {
                queryWrapper.eq(Feedback::getSubmitterId, query.getSubmitterId());
            }
            if (query.getAssigneeId() != null) {
                queryWrapper.eq(Feedback::getAssigneeId, query.getAssigneeId());
            }
            if (StringUtils.hasText(query.getKeyword())) {
                queryWrapper.and(wrapper -> wrapper
                    .like(Feedback::getTitle, query.getKeyword())
                    .or()
                    .like(Feedback::getContent, query.getKeyword())
                );
            }

            queryWrapper.orderByDesc(Feedback::getCreateTime);

            page = feedbackService.page(page, queryWrapper);

            // 转换为 VO Page 并添加评论数、点赞数和点赞状态
            Page<FeedbackVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
            List<FeedbackVO> voList = page.getRecords().stream()
                .map(feedback -> {
                    FeedbackVO vo = new FeedbackVO();
                    BeanUtils.copyProperties(feedback, vo);
                    // 统计评论数
                    Integer commentCount = Math.toIntExact(feedbackCommentService.count(
                        new LambdaQueryWrapper<FeedbackComment>()
                            .eq(FeedbackComment::getFeedbackId, feedback.getId())
                    ));
                    vo.setComments(commentCount);
                    // 设置点赞数
                    vo.setLikes(feedback.getLikes() != null ? feedback.getLikes() : 0);
                    // 设置当前用户是否已点赞
                    vo.setLiked(feedbackLikeService.isLiked(feedback.getId(), currentUserId));
                    return vo;
                })
                .collect(Collectors.toList());
            voPage.setRecords(voList);

            log.info("[分页查询反馈] 成功，共 {} 条", voPage.getTotal());
            return Result.success(voPage);
        } catch (Exception e) {
            log.error("[分页查询反馈] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "点赞反馈")
    @PostMapping("/{id}/like")
    public Result<Void> likeFeedback(@PathVariable Long id) {
        try {
            log.info("[点赞反馈] 反馈ID: {}", id);

            // 检查反馈是否存在
            Feedback feedback = feedbackService.getById(id);
            if (feedback == null) {
                return Result.error("反馈不存在");
            }

            Long userId = UserContextHolder.getUserId();
            boolean success = feedbackLikeService.likeFeedback(id, userId);
            if (success) {
                log.info("[点赞反馈] 成功");
                return Result.success();
            } else {
                return Result.error("已经点赞过了");
            }
        } catch (Exception e) {
            log.error("[点赞反馈] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "取消点赞反馈")
    @PostMapping("/{id}/unlike")
    public Result<Void> unlikeFeedback(@PathVariable Long id) {
        try {
            log.info("[取消点赞反馈] 反馈ID: {}", id);

            // 检查反馈是否存在
            Feedback feedback = feedbackService.getById(id);
            if (feedback == null) {
                return Result.error("反馈不存在");
            }

            Long userId = UserContextHolder.getUserId();
            boolean success = feedbackLikeService.unlikeFeedback(id, userId);
            if (success) {
                log.info("[取消点赞反馈] 成功");
                return Result.success();
            } else {
                return Result.error("还未点赞");
            }
        } catch (Exception e) {
            log.error("[取消点赞反馈] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "获取反馈的评论列表")
    @GetMapping("/{id}/comments")
    public Result<List<FeedbackComment>> getComments(@PathVariable Long id) {
        try {
            log.info("[获取反馈评论列表] 反馈ID: {}", id);

            // 检查反馈是否存在
            Feedback feedback = feedbackService.getById(id);
            if (feedback == null) {
                return Result.error("反馈不存在");
            }

            List<FeedbackComment> comments = feedbackCommentService.list(
                new LambdaQueryWrapper<FeedbackComment>()
                    .eq(FeedbackComment::getFeedbackId, id)
                    .orderByDesc(FeedbackComment::getCreateTime)
            );
            log.info("[获取反馈评论列表] 成功，共 {} 条", comments.size());
            return Result.success(comments);
        } catch (Exception e) {
            log.error("[获取反馈评论列表] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "添加反馈评论")
    @PostMapping("/{id}/comments")
    public Result<FeedbackComment> addComment(@PathVariable Long id, @RequestBody FeedbackComment comment) {
        try {
            log.info("[添加反馈评论] 反馈ID: {}, 评论内容: {}", id, comment.getContent());

            // 检查反馈是否存在
            Feedback feedback = feedbackService.getById(id);
            if (feedback == null) {
                return Result.error("反馈不存在");
            }

            // 参数校验
            if (!StringUtils.hasText(comment.getContent())) {
                return Result.error("评论内容不能为空");
            }

            comment.setFeedbackId(id);
            comment.setUserId(UserContextHolder.getUserId());
            feedbackCommentService.save(comment);

            log.info("[添加反馈评论] 成功");
            return Result.success(comment);
        } catch (Exception e) {
            log.error("[添加反馈评论] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除反馈评论")
    @RequiresPermission("feedback:comment:delete")
    @DeleteMapping("/{feedbackId}/comments/{commentId}")
    public Result<Void> deleteComment(@PathVariable Long feedbackId, @PathVariable Long commentId) {
        try {
            log.info("[删除反馈评论] 反馈ID: {}, 评论ID: {}", feedbackId, commentId);

            // 检查评论是否存在
            FeedbackComment comment = feedbackCommentService.getById(commentId);
            if (comment == null) {
                return Result.error("评论不存在");
            }

            // 检查评论是否属于该反馈
            if (!comment.getFeedbackId().equals(feedbackId)) {
                return Result.error("评论不属于该反馈");
            }

            feedbackCommentService.removeById(commentId);
            log.info("[删除反馈评论] 成功");
            return Result.success();
        } catch (Exception e) {
            log.error("[删除反馈评论] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }
}
