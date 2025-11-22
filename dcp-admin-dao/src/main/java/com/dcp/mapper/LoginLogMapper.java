package com.dcp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dcp.entity.SysLoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录日志 Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface LoginLogMapper extends BaseMapper<SysLoginLog> {

}
