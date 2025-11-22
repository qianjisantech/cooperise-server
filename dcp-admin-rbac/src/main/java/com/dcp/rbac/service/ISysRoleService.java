package com.dcp.rbac.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.rbac.request.AssignPermissionRequest;
import com.dcp.rbac.request.SysRoleQueryRequest;
import com.dcp.rbac.request.SysRoleRequest;
import com.dcp.rbac.vo.SysRoleVO;
import com.dcp.rbac.entity.SysRole;

import java.util.List;

/**
 * 角色服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 保存角色
     *
     * @param request 角色请求
     */
    void saveRole(SysRoleRequest request);

    /**
     * 更新角色
     *
     * @param id 角色ID
     * @param request 角色请求
     */
    void updateRoleById(Long id, SysRoleRequest request);

    /**
     * 分页查询角色
     *
     * @param request 查询请求
     * @return 分页结果
     */
    Page<SysRoleVO> queryPage(SysRoleQueryRequest request);

    /**
     * 获取所有角色列表
     *
     * @return 角色列表
     */
    List<SysRoleVO> getRoleList();

    /**
     * 获取角色详情
     *
     * @param id 角色ID
     * @return 角色VO
     */
    SysRoleVO getRoleDetail(Long id);

    /**
     * 分配角色权限
     *
     * @param request 分配权限请求
     */
    void assignPermissions(Long roleId,AssignPermissionRequest request);

    /**
     * 获取角色已分配的菜单ID列表
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    List<Long> getRoleMenuIds(Long roleId);
}
