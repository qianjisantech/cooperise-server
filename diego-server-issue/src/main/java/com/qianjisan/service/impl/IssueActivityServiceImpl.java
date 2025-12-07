package com.qianjisan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.common.dto.WorkspaceIssueActivityQueryDTO;
import com.qianjisan.entity.IssueActivity;
import com.qianjisan.entity.WorkspaceIssueActivity;
import com.qianjisan.mapper.IssueActivityMapper;
import com.qianjisan.mapper.WorkspaceIssueActivityMapper;
import com.qianjisan.service.IIssueActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * WorkspaceIssueActivity服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
public class IssueActivityServiceImpl extends ServiceImpl<IssueActivityMapper, IssueActivity> implements IIssueActivityService {

    @Override
    public Page<IssueActivity> pageIssueActivity(WorkspaceIssueActivityQueryDTO query) {
        log.info("[分页查询事项活动记录管理] 查询参数: {}", query);
        Page<IssueActivity> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<IssueActivity> queryWrapper = new LambdaQueryWrapper<>();
        if (query.getIssueId() != null) {
            queryWrapper.eq(IssueActivity::getIssueId, query.getIssueId());
        }
        if (StringUtils.hasText(query.getActivityType())) {
            queryWrapper.eq(IssueActivity::getAction, query.getActivityType());
        }
        if (query.getUserId() != null) {
            queryWrapper.eq(IssueActivity::getUserId, query.getUserId());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                .like(IssueActivity::getAction, query.getKeyword())
                .or().like(IssueActivity::getField, query.getKeyword())
                .or().like(IssueActivity::getOldValue, query.getKeyword())
                .or().like(IssueActivity::getNewValue, query.getKeyword())
            );
        }
        queryWrapper.orderByDesc(IssueActivity::getCreateTime);
        page = this.page(page, queryWrapper);
        log.info("[分页查询事项活动记录管理] 成功，共 {} 条", page.getTotal());
        return page;
    }
}
