package com.dcp.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户VO
 * 不包含敏感字段（如密码）
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfoVO extends BaseVO {



    /**
     * 姓名
     */
    private String name;

    /**
     * 用户编码
     */
    private  String userCode;

    /**
     * 头像URL
     */
    private String avatar;

}
