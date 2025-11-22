package com.dcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.common.context.UserContextHolder;
import com.dcp.common.dto.TrackingLogQueryDTO;
import com.dcp.common.exception.BusinessException;
import com.dcp.common.request.TrackingLogRequest;
import com.dcp.common.util.BeanConverter;
import com.dcp.common.vo.TrackingLogVO;
import com.dcp.entity.TrackingLog;
import com.dcp.mapper.TrackingLogMapper;
import com.dcp.rbac.entity.SysUser;
import com.dcp.rbac.service.ISysUserService;
import com.dcp.service.ITrackingLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 埋点日志服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TrackingLogServiceImpl extends ServiceImpl<TrackingLogMapper, TrackingLog> implements ITrackingLogService {

    private final ISysUserService userService;

    @Override
    public Page<TrackingLogVO> pageQuery(TrackingLogQueryDTO query) {
        Page<TrackingLog> page = new Page<>(query.getCurrent(), query.getSize());

        LambdaQueryWrapper<TrackingLog> wrapper = new LambdaQueryWrapper<>();

        // 用户ID
        if (query.getUserId() != null) {
            wrapper.eq(TrackingLog::getUserId, query.getUserId());
        }

        // 用户名
        if (StringUtils.hasText(query.getUsername())) {
            wrapper.like(TrackingLog::getUsername, query.getUsername());
        }

        // 事件类型
        if (StringUtils.hasText(query.getEventType())) {
            wrapper.eq(TrackingLog::getEventType, query.getEventType());
        }

        // 事件名称
        if (StringUtils.hasText(query.getEventName())) {
            wrapper.like(TrackingLog::getEventName, query.getEventName());
        }

        // 页面URL
        if (StringUtils.hasText(query.getPageUrl())) {
            wrapper.like(TrackingLog::getPageUrl, query.getPageUrl());
        }

        // 时间范围
        if (StringUtils.hasText(query.getStartTime())) {
            LocalDateTime startTime = LocalDateTime.parse(query.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            wrapper.ge(TrackingLog::getCreateTime, startTime);
        }
        if (StringUtils.hasText(query.getEndTime())) {
            LocalDateTime endTime = LocalDateTime.parse(query.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            wrapper.le(TrackingLog::getCreateTime, endTime);
        }

        // 关键词搜索
        if (StringUtils.hasText(query.getKeyword())) {
            wrapper.and(w -> w
                .like(TrackingLog::getEventName, query.getKeyword())
                .or()
                .like(TrackingLog::getPageUrl, query.getKeyword())
                .or()
                .like(TrackingLog::getPageTitle, query.getKeyword())
                .or()
                .like(TrackingLog::getElementText, query.getKeyword())
            );
        }

        // 按创建时间倒序
        wrapper.orderByDesc(TrackingLog::getCreateTime);

        Page<TrackingLog> resultPage = page(page, wrapper);
        return BeanConverter.convertPage(resultPage, TrackingLogVO::new);
    }

    @Override
    public void saveTrackingLog(TrackingLogRequest request) {
        TrackingLog trackingLog = new TrackingLog();
        BeanUtils.copyProperties(request, trackingLog);

        // 获取当前用户信息
        Long userId = UserContextHolder.getUserId();
        if (userId != null) {
            trackingLog.setUserId(userId);
            SysUser user = userService.getById(userId);
            if (user != null) {
                trackingLog.setUsername(user.getUsername());
                trackingLog.setUserCode(user.getUserCode());
            }
        }

        // 获取IP地址和User-Agent
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest httpRequest = attributes.getRequest();
            trackingLog.setIpAddress(getIpAddress(httpRequest));
            trackingLog.setUserAgent(httpRequest.getHeader("User-Agent"));
        }

        boolean success = save(trackingLog);
        if (!success) {
            throw new BusinessException("保存埋点日志失败");
        }
    }

    @Override
    public TrackingLogVO getTrackingLogById(Long id) {
        TrackingLog trackingLog = getById(id);
        if (trackingLog == null) {
            throw new BusinessException("埋点日志不存在");
        }
        return BeanConverter.convert(trackingLog, TrackingLogVO::new);
    }

    /**
     * 获取IP地址
     */
    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 对于多级代理，取第一个IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
