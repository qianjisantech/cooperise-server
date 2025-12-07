package com.qianjisan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.common.dto.WorkspaceIssueAttachmentQueryDTO;
import com.qianjisan.entity.IssueAttachment;
import com.qianjisan.entity.WorkspaceIssueAttachment;
import com.qianjisan.mapper.IssueAttachmentMapper;
import com.qianjisan.mapper.WorkspaceIssueAttachmentMapper;
import com.qianjisan.service.IIssueAttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * WorkspaceIssueAttachment服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
public class IssueAttachmentServiceImpl extends ServiceImpl<IssueAttachmentMapper, IssueAttachment> implements IIssueAttachmentService {

    @Override
    public Page<IssueAttachment> pageIssueAttachment(WorkspaceIssueAttachmentQueryDTO query) {
        log.info("[分页查询事项附件管理] 查询参数: {}", query);
        Page<IssueAttachment> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<IssueAttachment> queryWrapper = new LambdaQueryWrapper<>();
        if (query.getIssueId() != null) {
            queryWrapper.eq(IssueAttachment::getIssueId, query.getIssueId());
        }
        if (query.getUploaderId() != null) {
            queryWrapper.eq(IssueAttachment::getUploaderId, query.getUploaderId());
        }
        if (StringUtils.hasText(query.getFileType())) {
            queryWrapper.eq(IssueAttachment::getFileType, query.getFileType());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.like(IssueAttachment::getFileName, query.getKeyword());
        }
        queryWrapper.orderByDesc(IssueAttachment::getCreateTime);
        page = this.page(page, queryWrapper);
        log.info("[分页查询事项附件管理] 成功，共 {} 条", page.getTotal());
        return page;
    }
}
