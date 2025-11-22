package com.dcp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dcp.entity.SysChangelog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 发布日志 Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface ChangelogMapper extends BaseMapper<SysChangelog> {

}
