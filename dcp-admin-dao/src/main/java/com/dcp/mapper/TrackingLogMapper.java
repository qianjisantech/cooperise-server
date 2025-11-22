package com.dcp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dcp.entity.TrackingLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 埋点日志Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface TrackingLogMapper extends BaseMapper<TrackingLog> {

}
