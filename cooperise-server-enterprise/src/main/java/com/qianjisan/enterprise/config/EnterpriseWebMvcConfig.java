package com.qianjisan.enterprise.config;

import com.qianjisan.core.interceptor.EnterpriseInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 企业模块 Web MVC 配置
 *
 * @author cooperise
 * @since 2026-02-03
 */
@Configuration
@RequiredArgsConstructor
public class EnterpriseWebMvcConfig implements WebMvcConfigurer {

    private final EnterpriseInterceptor enterpriseInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(enterpriseInterceptor)
                .addPathPatterns("/enterprise-api/**")  // 拦截企业模块所有接口
                .excludePathPatterns(
                        "/swagger-ui/**",        // Swagger UI
                        "/swagger-resources/**", // Swagger资源
                        "/v3/api-docs/**",       // OpenAPI文档
                        "/doc.html",             // Knife4j文档
                        "/webjars/**",           // Knife4j资源
                        "/favicon.ico",          // 图标
                        "/error"                 // 错误页面
                );
    }
}