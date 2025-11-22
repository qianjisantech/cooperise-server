package com.dcp.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dcp.rbac.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

}
