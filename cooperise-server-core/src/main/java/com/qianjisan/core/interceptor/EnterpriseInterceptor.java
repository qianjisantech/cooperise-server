package com.qianjisan.core.interceptor;

import com.qianjisan.core.context.EnterpriseContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 企业拦截器
 * 用于解析请求头中的企业ID，并设置企业上下文信息
 *
 * @author cooperise
 * @since 2026-02-03
 */
@Slf4j
@Component
public class EnterpriseInterceptor implements HandlerInterceptor {

    /**
     * 企业ID header名称
     */
    private static final String ENTERPRISE_ID_HEADER = "x-enterprise-id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 从请求头中获取企业ID
        String enterpriseIdHeader = request.getHeader(ENTERPRISE_ID_HEADER);

        if (StringUtils.hasText(enterpriseIdHeader)) {
            try {
                // 解析企业ID
                Long enterpriseId = Long.parseLong(enterpriseIdHeader);

                // 设置企业上下文
                EnterpriseContextHolder.setEnterprise(enterpriseId);

                log.debug("企业ID解析成功，企业ID: {}", enterpriseId);
            } catch (NumberFormatException e) {
                log.error("解析企业ID失败，格式不正确: {}", enterpriseIdHeader);
            }
        } else {
            log.debug("请求头中未提供企业ID");
        }

        // 继续执行后续处理
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 请求完成后清除企业上下文，防止内存泄漏
        EnterpriseContextHolder.clear();
    }
}