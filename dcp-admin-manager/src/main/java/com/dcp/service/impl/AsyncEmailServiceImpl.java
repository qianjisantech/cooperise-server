package com.dcp.service.impl;

import com.dcp.service.IAsyncEmailService;
import com.dcp.service.IEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步邮件服务实现类
 *
 * 注意：此类专门用于异步邮件发送，与AuthServiceImpl分离，
 * 确保@Async注解能够正常工作（避免同类调用导致的代理失效问题）
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncEmailServiceImpl implements IAsyncEmailService {

    private final IEmailService emailService;

    /**
     * 异步发送验证码邮件
     *
     * @Async 注解使此方法在单独的线程中执行，不会阻塞主线程
     */
    @Async
    @Override
    public void sendVerificationCodeAsync(String email, String code) {
        try {
            log.info("[AsyncEmailService] 开始异步发送验证码邮件，邮箱: {}", email);
            emailService.sendVerificationCode(email, code);
            log.info("[AsyncEmailService] 异步发送验证码邮件成功，邮箱: {}", email);
        } catch (Exception e) {
            log.error("[AsyncEmailService] 异步发送验证码邮件失败，邮箱: {}, 错误: {}", email, e.getMessage(), e);
            // 异步方法中的异常不会影响主流程，只记录日志即可
        }
    }
}
