package com.qianjisan.enterprise.config;

import com.qianjisan.enterprise.validation.JsonFormatValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * 验证配置类
 * 确保自定义验证器被正确注册
 */
@Configuration
public class ValidationConfig {

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        return validator;
    }
}
