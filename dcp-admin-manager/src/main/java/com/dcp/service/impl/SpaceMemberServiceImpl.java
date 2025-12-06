package com.dcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.common.dto.SpaceMemberQueryDTO;
import com.dcp.common.exception.BusinessException;
import com.dcp.common.request.SpaceMemberRequest;
import com.dcp.common.vo.SpaceMemberVO;
import com.dcp.entity.SpaceMember;
import com.dcp.mapper.SpaceMemberMapper;
import com.dcp.rbac.entity.SysUser;
import com.dcp.rbac.mapper.SysUserMapper;
import com.dcp.service.ISpaceMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * SpaceMember服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Service
@RequiredArgsConstructor
public class SpaceMemberServiceImpl extends ServiceImpl<SpaceMemberMapper, SpaceMember> implements ISpaceMemberService {

    private final SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> getAvailableUsers(Long spaceId) {
        // 1. 查询该空间已有的用户ID列表
        LambdaQueryWrapper<SpaceMember> memberWrapper = new LambdaQueryWrapper<>();
        memberWrapper.eq(SpaceMember::getSpaceId, spaceId);
        memberWrapper.select(SpaceMember::getUserId);
        List<Long> existingUserIds = list(memberWrapper).stream()
                .map(SpaceMember::getUserId)
                .collect(Collectors.toList());

        // 2. 查询所有未被逻辑删除且状态正常的用户
        LambdaQueryWrapper<SysUser> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(SysUser::getStatus, 1); // 状态正常

        // 3. 排除已在该空间的用户
        if (!existingUserIds.isEmpty()) {
            userWrapper.notIn(SysUser::getId, existingUserIds);
        }

        // 4. 只返回需要的字段
        userWrapper.select(SysUser::getId, SysUser::getName, SysUser::getUserCode, SysUser::getEmail);

        return sysUserMapper.selectList(userWrapper);
    }

    @Override
    public Page<SpaceMemberVO> pageWithUserInfo(SpaceMemberQueryDTO query) {
        // 1. 分页查询空间成员
        Page<SpaceMember> memberPage = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<SpaceMember> wrapper = new LambdaQueryWrapper<>();

        if (query.getSpaceId() != null) {
            wrapper.eq(SpaceMember::getSpaceId, query.getSpaceId());
        }
        if (query.getUserId() != null) {
            wrapper.eq(SpaceMember::getUserId, query.getUserId());
        }
        if (StringUtils.hasText(query.getRole())) {
            wrapper.eq(SpaceMember::getRole, query.getRole());
        }

        wrapper.orderByDesc(SpaceMember::getJoinTime);
        memberPage = page(memberPage, wrapper);

        // 2. 获取所有用户ID
        List<Long> userIds = memberPage.getRecords().stream()
                .map(SpaceMember::getUserId)
                .distinct()
                .collect(Collectors.toList());

        // 3. 批量查询用户信息
        Map<Long, SysUser> userMap = userIds.isEmpty() ? Map.of() :
                sysUserMapper.selectBatchIds(userIds).stream()
                        .collect(Collectors.toMap(SysUser::getId, u -> u));

        // 4. 组装VO
        Page<SpaceMemberVO> voPage = new Page<>(memberPage.getCurrent(), memberPage.getSize(), memberPage.getTotal());
        List<SpaceMemberVO> voList = memberPage.getRecords().stream().map(member -> {
            SpaceMemberVO vo = new SpaceMemberVO();
            BeanUtils.copyProperties(member, vo);

            // 填充用户信息
            SysUser user = userMap.get(member.getUserId());
            if (user != null) {
                vo.setName(user.getName());
                vo.setEmail(user.getEmail());
                vo.setAvatar(user.getAvatar());
                vo.setPhone(user.getPhone());
            }

            return vo;
        }).collect(Collectors.toList());

        voPage.setRecords(voList);
        return voPage;
    }

    @Override
    public boolean deleteByIdPhysically(Long id) {
        // 使用自定义的物理删除方法，直接执行 DELETE SQL，不受 @TableLogic 影响
        return baseMapper.deleteByIdPhysically(id) > 0;
    }

    @Override
    public boolean canDeleteMember(Long id) {
        // 查询成员信息
        SpaceMember member = getById(id);
        if (member == null) {
            return false;
        }

        // 如果是管理员（admin），不允许删除
        return !"admin".equals(member.getRole());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMember(SpaceMemberRequest request) {
        // 参数校验
        if (request.getSpaceId() == null) {
            throw new BusinessException("空间ID不能为空");
        }
        if (request.getUserId() == null) {
            throw new BusinessException("用户ID不能为空");
        }
        if (!StringUtils.hasText(request.getRole())) {
            throw new BusinessException("角色不能为空");
        }

        // 转换为实体
        SpaceMember member = new SpaceMember();
        BeanUtils.copyProperties(request, member);

        // 保存
        boolean success = save(member);
        if (!success) {
            throw new BusinessException("添加成员失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMember(Long id, SpaceMemberRequest request) {
        // 参数校验
        if (!StringUtils.hasText(request.getRole())) {
            throw new BusinessException("角色不能为空");
        }

        // 检查成员是否存在
        SpaceMember existMember = getById(id);
        if (existMember == null) {
            throw new BusinessException("成员不存在");
        }

        // 检查是否为管理员
        if ("admin".equals(existMember.getRole())) {
            throw new BusinessException("管理员角色不可修改");
        }

        // 转换为实体并更新
        SpaceMember member = new SpaceMember();
        member.setId(id);
        member.setRole(request.getRole());

        boolean success = updateById(member);
        if (!success) {
            throw new BusinessException("更新成员失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMember(Long id) {
        // 检查是否可以删除
        if (!canDeleteMember(id)) {
            throw new BusinessException("管理员不能删除，空间必须至少有一个管理员");
        }

        // 物理删除
        boolean success = deleteByIdPhysically(id);
        if (!success) {
            throw new BusinessException("删除成员失败");
        }
    }

    @Override
    public boolean isSpaceMember(Long spaceId, Long userId) {
        if (spaceId == null || userId == null) {
            return false;
        }

        LambdaQueryWrapper<SpaceMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SpaceMember::getSpaceId, spaceId);
        wrapper.eq(SpaceMember::getUserId, userId);

        return count(wrapper) > 0;
    }

    @Override
    public List<Long> getUserSpaceIds(Long userId) {
        if (userId == null) {
            return List.of();
        }

        LambdaQueryWrapper<SpaceMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SpaceMember::getUserId, userId);
        wrapper.select(SpaceMember::getSpaceId);

        return list(wrapper).stream()
                .map(SpaceMember::getSpaceId)
                .collect(Collectors.toList());
    }

    @Override
    public void checkSpacePermission(Long spaceId, Long userId) {
        if (!isSpaceMember(spaceId, userId)) {
            throw new com.dcp.common.exception.DataPermissionException("您没有权限访问该空间的数据");
        }
    }
}
