package com.dcp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dcp.entity.SysOperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<SysOperationLog> {
}
