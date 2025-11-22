package com.dcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.common.dto.SpaceQueryDTO;
import com.dcp.common.request.SpaceRequest;
import com.dcp.common.vo.SpaceVO;
import com.dcp.entity.Space;

import java.util.List;

/**
 * 空间服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface ISpaceService extends IService<Space> {

    /**
     * 根据ID查询空间
     *
     * @param id 空间ID
     * @return 空间VO
     */
    SpaceVO getSpaceById(Long id);

    /**
     * 查询所有空间列表
     *
     * @return 空间VO列表
     */
    List<SpaceVO> listAllSpaces();

    /**
     * 分页查询空间
     *
     * @param query 查询条件
     * @return 分页结果VO
     */
    Page<SpaceVO> pageQuery(SpaceQueryDTO query);

    /**
     * 根据负责人ID查询空间列表
     *
     * @param ownerId 负责人ID
     * @return 空间VO列表
     */
    List<SpaceVO> listByOwner(Long ownerId);

    /**
     * 创建空间并自动添加创建人为管理员
     *
     * @param space 空间信息
     * @param creatorId 创建人ID
     * @return 是否创建成功
     */
    boolean createSpaceWithMember(Space space, Long creatorId);

    /**
     * 创建空间（业务方法）
     *
     * @param request 空间请求
     * @param currentUserId 当前用户ID
     */
    void createSpace(SpaceRequest request, Long currentUserId);

    /**
     * 更新空间（业务方法）
     *
     * @param id 空间ID
     * @param request 空间请求
     */
    void updateSpace(Long id, SpaceRequest request);

    /**
     * 删除空间（业务方法）
     *
     * @param id 空间ID
     */
    void deleteSpace(Long id);
}
