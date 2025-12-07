package com.qianjisan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.common.dto.WorkspaceIssueCommentQueryDTO;
import com.qianjisan.entity.IssueComment;
import com.qianjisan.mapper.IssueCommentMapper;
import com.qianjisan.service.IIssueCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * WorkspaceIssueComment服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
public class IssueCommentServiceImpl extends ServiceImpl<IssueCommentMapper, IssueComment> implements IIssueCommentService {

    @Override
    public Page<IssueComment> pageIssueComment(WorkspaceIssueCommentQueryDTO query) {
        log.info("[分页查询事项评论管理] 查询参数: {}", query);
        Page<IssueComment> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<IssueComment> queryWrapper = new LambdaQueryWrapper<>();
        if (query.getIssueId() != null) {
            queryWrapper.eq(IssueComment::getIssueId, query.getIssueId());
        }
        if (query.getUserId() != null) {
            queryWrapper.eq(IssueComment::getUserId, query.getUserId());
        }
        if (query.getParentId() != null) {
            queryWrapper.eq(IssueComment::getParentId, query.getParentId());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.like(IssueComment::getContent, query.getKeyword());
        }
        queryWrapper.orderByDesc(IssueComment::getCreateTime);
        page = this.page(page, queryWrapper);
        log.info("[分页查询事项评论管理] 成功，共 {} 条", page.getTotal());
        return page;
    }
}
