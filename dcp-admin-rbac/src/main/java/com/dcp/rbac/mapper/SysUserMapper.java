package com.dcp.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dcp.rbac.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
