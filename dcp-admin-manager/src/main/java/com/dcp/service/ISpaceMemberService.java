package com.dcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.common.dto.SpaceMemberQueryDTO;
import com.dcp.common.vo.SpaceMemberVO;
import com.dcp.entity.SpaceMember;
import com.dcp.rbac.entity.SysUser;

import java.util.List;

/**
 * SpaceMember服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface ISpaceMemberService extends IService<SpaceMember> {

    /**
     * 获取可添加到空间的用户列表
     * 排除已在该空间的用户和已被逻辑删除的用户
     *
     * @param spaceId 空间ID
     * @return 可添加的用户列表
     */
    List<SysUser> getAvailableUsers(Long spaceId);

    /**
     * 分页查询空间成员（带用户信息）
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<SpaceMemberVO> pageWithUserInfo(SpaceMemberQueryDTO query);

    /**
     * 物理删除空间成员
     *
     * @param id 成员ID
     * @return 是否删除成功
     */
    boolean deleteByIdPhysically(Long id);

    /**
     * 检查是否可以删除该成员（管理员不能删除）
     *
     * @param id 成员ID
     * @return 是否可以删除
     */
    boolean canDeleteMember(Long id);

    /**
     * 添加空间成员（业务方法）
     *
     * @param request 成员请求
     */
    void addMember(com.dcp.common.request.SpaceMemberRequest request);

    /**
     * 更新空间成员（业务方法）
     *
     * @param id 成员ID
     * @param request 成员请求
     */
    void updateMember(Long id, com.dcp.common.request.SpaceMemberRequest request);

    /**
     * 删除空间成员（业务方法）
     *
     * @param id 成员ID
     */
    void deleteMember(Long id);

    /**
     * 检查用户是否是指定空间的成员
     *
     * @param spaceId 空间ID
     * @param userId 用户ID
     * @return 是否是空间成员
     */
    boolean isSpaceMember(Long spaceId, Long userId);

    /**
     * 获取用户所在的所有空间ID列表
     *
     * @param userId 用户ID
     * @return 空间ID列表
     */
    List<Long> getUserSpaceIds(Long userId);

    /**
     * 检查用户是否有访问该空间的权限
     *
     * @param spaceId 空间ID
     * @param userId 用户ID
     * @throws com.dcp.common.exception.DataPermissionException 如果没有权限
     */
    void checkSpacePermission(Long spaceId, Long userId);
}
