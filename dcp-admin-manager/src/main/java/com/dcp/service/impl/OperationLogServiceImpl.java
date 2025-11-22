package com.dcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.common.dto.OperationLogQueryDTO;
import com.dcp.common.util.BeanConverter;
import com.dcp.common.vo.OperationLogVO;
import com.dcp.entity.SysOperationLog;
import com.dcp.mapper.OperationLogMapper;
import com.dcp.service.IOperationLogService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 操作日志服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, SysOperationLog> implements IOperationLogService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Page<OperationLogVO> pageQuery(OperationLogQueryDTO query) {
        Page<SysOperationLog> page = new Page<>(query.getCurrent(), query.getSize());

        LambdaQueryWrapper<SysOperationLog> queryWrapper = new LambdaQueryWrapper<>();

        // 用户ID条件
        if (query.getUserId() != null) {
            queryWrapper.eq(SysOperationLog::getUserId, query.getUserId());
        }

        // 用户名条件
        if (StringUtils.hasText(query.getUsername())) {
            queryWrapper.like(SysOperationLog::getUsername, query.getUsername());
        }

        // 请求方法条件
        if (StringUtils.hasText(query.getRequestMethod())) {
            queryWrapper.eq(SysOperationLog::getRequestMethod, query.getRequestMethod());
        }

        // 请求路径条件(模糊查询)
        if (StringUtils.hasText(query.getRequestUrl())) {
            queryWrapper.like(SysOperationLog::getRequestUrl, query.getRequestUrl());
        }

        // IP地址条件
        if (StringUtils.hasText(query.getIpAddress())) {
            queryWrapper.eq(SysOperationLog::getIpAddress, query.getIpAddress());
        }

        // 时间范围条件
        if (StringUtils.hasText(query.getStartTime())) {
            LocalDateTime startTime = LocalDateTime.parse(query.getStartTime(), DATE_TIME_FORMATTER);
            queryWrapper.ge(SysOperationLog::getCreateTime, startTime);
        }
        if (StringUtils.hasText(query.getEndTime())) {
            LocalDateTime endTime = LocalDateTime.parse(query.getEndTime(), DATE_TIME_FORMATTER);
            queryWrapper.le(SysOperationLog::getCreateTime, endTime);
        }

        // 关键词搜索
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                .like(SysOperationLog::getUsername, query.getKeyword())
                .or()
                .like(SysOperationLog::getRequestUrl, query.getKeyword())
                .or()
                .like(SysOperationLog::getIpAddress, query.getKeyword())
            );
        }

        // 按创建时间倒序
        queryWrapper.orderByDesc(SysOperationLog::getCreateTime);

        Page<SysOperationLog> resultPage = page(page, queryWrapper);
        return BeanConverter.convertPage(resultPage, OperationLogVO::new);
    }

    @Override
    public void saveLog(SysOperationLog sysOperationLog) {
        save(sysOperationLog);
    }
}
