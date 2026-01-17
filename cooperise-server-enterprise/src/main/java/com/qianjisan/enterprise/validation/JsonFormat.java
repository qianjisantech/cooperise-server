package com.qianjisan.enterprise.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSON 格式验证注解
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = JsonFormatValidator.class)
public @interface JsonFormat {

    String message() default "字段值必须是有效的 JSON 格式";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
