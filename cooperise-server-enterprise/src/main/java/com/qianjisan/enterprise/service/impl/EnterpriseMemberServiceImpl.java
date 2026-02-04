package com.qianjisan.enterprise.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.core.PageVO;
import com.qianjisan.core.context.EnterpriseContextHolder;
import com.qianjisan.core.context.UserContext;
import com.qianjisan.core.context.UserContextHolder;
import com.qianjisan.enterprise.entity.EnterpriseMember;
import com.qianjisan.enterprise.mapper.EnterpriseMemberMapper;
import com.qianjisan.enterprise.request.EnterpriseMemberQueryRequest;
import com.qianjisan.enterprise.request.EnterpriseMemberRequest;
import com.qianjisan.enterprise.service.IEnterpriseMemberService;
import com.qianjisan.enterprise.vo.EnterpriseMemberVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 企业成员表 Service 实现
 */
@Slf4j
@Service
public class EnterpriseMemberServiceImpl extends ServiceImpl<EnterpriseMemberMapper, EnterpriseMember> implements IEnterpriseMemberService {

    @Override
    public boolean createEnterpriseMember(EnterpriseMemberRequest request) {
        EnterpriseMember entity = convertToEntity(request);
        
        // 从请求头获取企业ID
        Long enterpriseId = EnterpriseContextHolder.getEnterpriseId();
        entity.setEnterpriseId(enterpriseId);

        UserContext userContext = UserContextHolder.getUser();
        if (userContext != null) {
            entity.setCreateByCode(userContext.getUserCode());
            entity.setCreateByName(userContext.getUsername());
            entity.setUpdateByCode(userContext.getUserCode());
            entity.setUpdateByName(userContext.getUsername());
        }

        entity.setJoinTime(LocalDateTime.now());
        entity.setIsDeleted(0);
        return this.save(entity);
    }

    @Override
    public boolean updateEnterpriseMember(Long id, EnterpriseMemberRequest request) {
        EnterpriseMember entity = convertToEntity(request);
        entity.setId(id);

        UserContext userContext = UserContextHolder.getUser();
        if (userContext != null) {
            entity.setUpdateByCode(userContext.getUserCode());
            entity.setUpdateByName(userContext.getUsername());
        }

        return this.updateById(entity);
    }

    @Override
    public boolean deleteEnterpriseMember(Long id) {
        return this.removeById(id);
    }

    @Override
    public EnterpriseMemberVo getEnterpriseMemberById(Long id) {
        EnterpriseMember enterpriseMember = this.getById(id);
        return convertToVo(enterpriseMember);
    }

    @Override
    public PageVO<EnterpriseMemberVo> getEnterpriseMemberPage(EnterpriseMemberQueryRequest request) {
        QueryWrapper<EnterpriseMember> wrapper = new QueryWrapper<>();

        // 从请求头获取企业ID，如果没有提供则使用请求参数中的企业ID
        Long enterpriseId = EnterpriseContextHolder.getEnterpriseId() != null
            ? EnterpriseContextHolder.getEnterprise().getEnterpriseId()
            : request.getEnterpriseId();
        if (enterpriseId != null) {
            wrapper.eq("enterprise_id", enterpriseId);
        }

        if (request.getUserId() != null) {
            wrapper.eq("user_id", request.getUserId());
        }

        if (request.getRole() != null && !request.getRole().isBlank()) {
            wrapper.eq("role", request.getRole());
        }

        if (request.getStatus() != null) {
            wrapper.eq("status", request.getStatus());
        }

        if (request.getDepartment() != null && !request.getDepartment().isBlank()) {
            wrapper.like("department", request.getDepartment());
        }

        wrapper.orderByDesc("create_time");

        Page<EnterpriseMember> page = this.page(new Page<>(request.getCurrent(), request.getSize()), wrapper);

        PageVO<EnterpriseMemberVo> pageVO = new PageVO<>();
        pageVO.setRecords(page.getRecords().stream().map(this::convertToVo).collect(Collectors.toList()));
        pageVO.setTotal(page.getTotal());
        pageVO.setCurrent(page.getCurrent());
        pageVO.setSize(page.getSize());
        pageVO.setPages(page.getPages());
        return pageVO;
    }

    @Override
    public List<EnterpriseMemberVo> getMembersByEnterpriseId(Long enterpriseId) {
        // 从请求头获取企业ID，如果没有提供则使用参数中的企业ID
        Long currentEnterpriseId = EnterpriseContextHolder.getEnterprise().getEnterpriseId();
        List<EnterpriseMember> list = this.list(new QueryWrapper<EnterpriseMember>().eq("enterprise_id", currentEnterpriseId));
        return list.stream().map(this::convertToVo).collect(Collectors.toList());
    }

    @Override
    public EnterpriseMemberVo getMemberByUserId(Long userId) {
        // 从请求头获取企业ID，根据企业和用户ID查询
        Long enterpriseId = EnterpriseContextHolder.getEnterpriseId();
        EnterpriseMember enterpriseMember = this.getOne(new QueryWrapper<EnterpriseMember>()
            .eq("enterprise_id", enterpriseId)
            .eq("user_id", userId));
        return convertToVo(enterpriseMember);
    }

    @Override
    public EnterpriseMember convertToEntity(EnterpriseMemberRequest request) {
        if (request == null) return null;
        EnterpriseMember entity = new EnterpriseMember();
        entity.setEnterpriseId(request.getEnterpriseId());
        entity.setUserId(request.getUserId());
        entity.setRole(request.getRole());
        entity.setStatus(request.getStatus());
        entity.setPosition(request.getPosition());
        entity.setDepartment(request.getDepartment());
        return entity;
    }

    @Override
    public EnterpriseMemberVo convertToVo(EnterpriseMember enterpriseMember) {
        if (enterpriseMember == null) return null;
        EnterpriseMemberVo vo = new EnterpriseMemberVo();
        vo.setId(enterpriseMember.getId());
        vo.setEnterpriseId(enterpriseMember.getEnterpriseId());
        vo.setUserId(enterpriseMember.getUserId());
        vo.setRole(enterpriseMember.getRole());
        vo.setStatus(enterpriseMember.getStatus());
        vo.setPosition(enterpriseMember.getPosition());
        vo.setDepartment(enterpriseMember.getDepartment());
        vo.setJoinTime(enterpriseMember.getJoinTime());
        vo.setCreateTime(enterpriseMember.getCreateTime());
        vo.setUpdateTime(enterpriseMember.getUpdateTime());
        return vo;
    }
}