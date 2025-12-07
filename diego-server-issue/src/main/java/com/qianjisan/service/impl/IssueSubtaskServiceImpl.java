package com.qianjisan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.common.dto.WorkspaceSubtaskQueryDTO;
import com.qianjisan.entity.IssueSubtask;
import com.qianjisan.entity.WorkspaceSubtask;
import com.qianjisan.mapper.IssueSubtaskMapper;
import com.qianjisan.mapper.WorkspaceSubtaskMapper;
import com.qianjisan.service.IIssueSubtaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * WorkspaceSubtask服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
public class IssueSubtaskServiceImpl extends ServiceImpl<IssueSubtaskMapper, IssueSubtask> implements IIssueSubtaskService {

    @Override
    public Page<IssueSubtask> pageSubtask(WorkspaceSubtaskQueryDTO query) {
        log.info("[分页查询子任务管理] 查询参数: {}", query);
        Page<IssueSubtask> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<IssueSubtask> queryWrapper = new LambdaQueryWrapper<>();
        if (query.getIssueId() != null) {
            queryWrapper.eq(IssueSubtask::getIssueId, query.getIssueId());
        }
        if (query.getCompleted() != null) {
            queryWrapper.eq(IssueSubtask::getCompleted, query.getCompleted());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.like(IssueSubtask::getTitle, query.getKeyword());
        }
        queryWrapper.orderByDesc(IssueSubtask::getCreateTime);
        page = this.page(page, queryWrapper);
        log.info("[分页查询子任务管理] 成功，共 {} 条", page.getTotal());
        return page;
    }
}
