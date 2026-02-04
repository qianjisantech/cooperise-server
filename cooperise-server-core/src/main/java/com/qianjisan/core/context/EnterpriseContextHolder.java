package com.qianjisan.core.context;

import com.qianjisan.core.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 企业上下文持有者
 * 使用ThreadLocal存储当前请求的企业信息
 *
 * @author cooperise
 * @since 2026-02-03
 */
public class EnterpriseContextHolder {

    private static final ThreadLocal<EnterpriseContext> CONTEXT_HOLDER = new ThreadLocal<>();
    private static final Logger log = LoggerFactory.getLogger(EnterpriseContextHolder.class);

    /**
     * 设置当前企业信息
     *
     * @param enterpriseContext 企业上下文
     */
    public static void setEnterprise(EnterpriseContext enterpriseContext) {
        CONTEXT_HOLDER.set(enterpriseContext);
    }

    /**
     * 设置当前企业信息
     *
     * @param enterpriseId 企业ID
     */
    public static void setEnterprise(Long enterpriseId) {
        EnterpriseContext enterpriseContext = new EnterpriseContext(enterpriseId);
        CONTEXT_HOLDER.set(enterpriseContext);
    }

    /**
     * 获取当前企业信息
     *
     * @return 企业上下文
     */
    public static EnterpriseContext getEnterprise() {
        EnterpriseContext enterpriseContext = CONTEXT_HOLDER.get();
        if (enterpriseContext == null) {
            throw new BusinessException("企业ID未提供");
        }
        return enterpriseContext;
    }

    /**
     * 获取当前企业信息（可能为 null）
     * 不会抛出异常，供在权限放开或未指定企业的场景安全调用
     *
     * @return 企业上下文或 null
     */
    public static EnterpriseContext getEnterpriseIfPresent() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 获取当前企业ID
     *
     * @return 企业ID
     */
    public static Long getEnterpriseId() {
        EnterpriseContext enterpriseContext = CONTEXT_HOLDER.get();
        if (enterpriseContext == null || enterpriseContext.getEnterpriseId() == null) {
            throw new BusinessException("企业ID未提供");
        }
        return enterpriseContext.getEnterpriseId();
    }

    /**
     * 清除当前企业信息
     */
    public static void clear() {
        CONTEXT_HOLDER.remove();
    }
}