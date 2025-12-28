package com.qianjisan.core.request;

import com.qianjisan.core.request.SpaceVO;
import com.qianjisan.core.request.UserVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户档案VO（包含权限和空间信息）
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class UserProfileVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户基本信息
     */
    private UserVO userInfo;

    /**
     * 菜单权限列表
     */
    private String[] menuPermissions;

    /**
     * 角色列表
     */
    private String[] roles;

    /**
     * 可访问的空间列表
     */
    private List<SpaceVO> spaces;

    /**
     * 可访问的空间ID列表
     */
    private Long[] spaceIds;

    /**
     * 数据权限配置
     */
    private DataPermissions dataPermissions;

    /**
     * 数据权限内部类
     */
    @Data
    public static class DataPermissions implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 是否拥有所有空间的访问权限
         */
        private Boolean allSpaces;

        /**
         * 拥有的空间ID列表
         */
        private Long[] ownedSpaces;
    }
}
