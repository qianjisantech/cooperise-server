package com.qianjisan.common.controller;

import com.qianjisan.common.reuqest.SendCodeRequest;
import com.qianjisan.common.service.IEmailService;
import com.qianjisan.common.service.impl.EmailServiceImpl;
import com.qianjisan.core.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "邮件管理", description = "邮件相关接口")
@RestController
@RequestMapping("/common-api/email")
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    private final IEmailService iEmailService;


    @Operation(summary = "发送邮箱验证码")
    @PostMapping("/send-code")
    public Result<String> sendVerificationCode(@RequestBody @Valid SendCodeRequest request) {
        try {
            String email = request.getEmail();
            log.info("[发送验证码] 邮箱: {}", email);
            iEmailService.sendVerificationCode(email);
            log.info("[发送验证码] 成功");
            return Result.success("发送验证码成功");
        } catch (Exception e) {
            log.error("[发送验证码] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

}
