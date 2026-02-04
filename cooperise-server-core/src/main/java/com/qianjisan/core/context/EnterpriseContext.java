package com.qianjisan.core.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 企业上下文信息
 *
 * @author cooperise
 * @since 2026-02-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseContext {

    /**
     * 企业ID
     */
    private Long enterpriseId;
}