package com.qianjisan.enterprise.validation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JSON 格式验证器测试类
 */
public class JsonFormatValidatorTest {

    private final JsonFormatValidator validator = new JsonFormatValidator();

    @Test
    public void testValidJson() {
        // 有效的 JSON
        assertTrue(validator.isValid("{\"key\": \"value\"}", null));
        assertTrue(validator.isValid("[1, 2, 3]", null));
        assertTrue(validator.isValid("\"string\"", null));
        assertTrue(validator.isValid("123", null));
        assertTrue(validator.isValid("true", null));
        assertTrue(validator.isValid("null", null));
        assertTrue(validator.isValid("{}", null));
        assertTrue(validator.isValid("[]", null));
    }

    @Test
    public void testInvalidJson() {
        // 无效的 JSON
        assertFalse(validator.isValid("{key: \"value\"}", null));
        assertFalse(validator.isValid("{'key': 'value'}", null));
        assertFalse(validator.isValid("{\"key\": \"value\",}", null));
        assertFalse(validator.isValid("{\"key\": \"value\"", null));
        assertFalse(validator.isValid("invalid", null));
    }

    @Test
    public void testNullOrEmpty() {
        // null 或空值应该通过验证
        assertTrue(validator.isValid(null, null));
        assertTrue(validator.isValid("", null));
        assertTrue(validator.isValid("   ", null));
    }
}
