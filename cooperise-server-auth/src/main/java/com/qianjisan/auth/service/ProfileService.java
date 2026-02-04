package com.qianjisan.auth.service;

import com.qianjisan.auth.vo.LoginResponseVO;
import com.qianjisan.auth.vo.UserProfileVO;


/**
 * 认证服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface ProfileService {


    /**
     * 获取用户权限信息
     *
     * @param userId 用户ID
     * @return 用户权限信息
     */
    UserProfileVO getUserProfile(Long userId);
}
