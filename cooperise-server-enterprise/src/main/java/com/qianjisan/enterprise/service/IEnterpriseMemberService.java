package com.qianjisan.enterprise.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.core.PageVO;
import com.qianjisan.enterprise.entity.EnterpriseMember;
import com.qianjisan.enterprise.request.EnterpriseMemberQueryRequest;
import com.qianjisan.enterprise.request.EnterpriseMemberRequest;
import com.qianjisan.enterprise.vo.EnterpriseMemberVo;

import java.util.List;

/**
 * 企业成员表 Service
 */
public interface IEnterpriseMemberService extends IService<EnterpriseMember> {

    /**
     * 创建企业成员
     */
    boolean createEnterpriseMember(EnterpriseMemberRequest request);

    /**
     * 更新企业成员
     */
    boolean updateEnterpriseMember(Long id, EnterpriseMemberRequest request);

    /**
     * 删除企业成员
     */
    boolean deleteEnterpriseMember(Long id);

    /**
     * 根据ID查询企业成员
     */
    EnterpriseMemberVo getEnterpriseMemberById(Long id);

    /**
     * 分页查询企业成员
     */
    PageVO<EnterpriseMemberVo> getEnterpriseMemberPage(EnterpriseMemberQueryRequest request);

    /**
     * 根据企业ID查询成员列表
     */
    List<EnterpriseMemberVo> getMembersByEnterpriseId(Long enterpriseId);

    /**
     * 根据用户ID查询企业成员信息
     */
    EnterpriseMemberVo getMemberByUserId(Long userId);

    /**
     * 将EnterpriseMemberRequest转换为EnterpriseMember实体
     */
    EnterpriseMember convertToEntity(EnterpriseMemberRequest request);

    /**
     * 将EnterpriseMember实体转换为EnterpriseMemberVo
     */
    EnterpriseMemberVo convertToVo(EnterpriseMember enterpriseMember);
}