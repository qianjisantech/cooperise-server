package com.qianjisan.enterprise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianjisan.enterprise.entity.EnterpriseMember;
import org.apache.ibatis.annotations.Mapper;

/**
 * 企业成员表 Mapper
 */
@Mapper
public interface EnterpriseMemberMapper extends BaseMapper<EnterpriseMember> {
}