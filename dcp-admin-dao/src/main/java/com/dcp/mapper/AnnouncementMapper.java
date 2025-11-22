package com.dcp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dcp.entity.Announcement;
import org.apache.ibatis.annotations.Mapper;

/**
 * 公告 Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface AnnouncementMapper extends BaseMapper<Announcement> {

}
