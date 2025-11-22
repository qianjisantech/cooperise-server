package com.dcp.service;

/**
 * 异步邮件服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IAsyncEmailService {

    /**
     * 异步发送验证码邮件
     *
     * @param email 邮箱地址
     * @param code  验证码
     */
    void sendVerificationCodeAsync(String email, String code);
}
