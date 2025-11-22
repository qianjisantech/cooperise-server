package com.dcp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dcp.entity.SysNotification;
import org.apache.ibatis.annotations.Mapper;

/**
 * 通知 Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface NotificationMapper extends BaseMapper<SysNotification> {

}
