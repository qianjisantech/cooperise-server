package com.qianjisan.enterprise.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * JSON 格式验证器
 */
@Slf4j
public class JsonFormatValidator implements ConstraintValidator<JsonFormat, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void initialize(JsonFormat constraintAnnotation) {
        // 初始化方法，可以在这里进行一些初始化工作
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 如果值为空，则认为验证通过
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        // 去除前后空格
        String trimmedValue = value.trim();

        // 如果是空字符串，也认为验证通过
        if (trimmedValue.isEmpty()) {
            return true;
        }

        try {
            // 尝试解析 JSON
            objectMapper.readTree(trimmedValue);
            return true;
        } catch (IOException e) {
            // JSON 解析失败，记录日志并返回 false
            log.debug("JSON 格式验证失败: {}", value, e);
            return false;
        }
    }
}
