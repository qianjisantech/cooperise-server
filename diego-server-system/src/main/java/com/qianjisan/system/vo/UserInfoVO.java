package com.qianjisan.system.vo;


import com.qianjisan.core.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户登录VO
 * 不包含敏感字段（如密码）
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfoVO extends BaseVO {

    /**
     * 用户编码
     */
    private Long id;
    /**
     * 邮箱
     */
    private String email;
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
