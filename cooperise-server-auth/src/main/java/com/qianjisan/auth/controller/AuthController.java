package com.qianjisan.auth.controller;

import com.qianjisan.auth.request.RegisterRequest;
import com.qianjisan.auth.service.IAuthService;
import com.qianjisan.core.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.qianjisan.auth.request.LoginRequest;
import com.qianjisan.auth.vo.LoginResponseVO;

@Tag(name = "认证管理", description = "用户认证相关接口")
@RestController
@RequestMapping("/auth-api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final IAuthService authService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<LoginResponseVO> login( @RequestBody @Valid LoginRequest request) {
        try {
            String email = request.getEmail();
            Boolean remember = request.getRemember();
            String password = request.getPassword();
            log.info("[用户登录] 邮箱: {}, 记住我: {}", email,remember);
            LoginResponseVO response = authService.login(email, password);
            log.info("[用户登录] 成功");
            return Result.success("登录成功",response);
        } catch (Exception e) {
            log.error("[用户登录] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error("登录失败，失败原因为："+e.getMessage());
        }
    }

    @Operation(summary = "用户登出")
    @PostMapping("/logout")
    public Result<Void> logout() {
        return Result.success();
    }



    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<String> register(@RequestBody @Valid RegisterRequest request) {

        try {
            String email = request.getEmail();
            String code = request.getCode();
            String password = request.getPassword();
            log.info("[用户注册] 邮箱: {}", email);
            authService.register(email, code, password);
            log.info("[用户注册] 成功");
            return Result.success("注册成功");
        } catch (Exception e) {
            log.error("[用户注册] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }
}
