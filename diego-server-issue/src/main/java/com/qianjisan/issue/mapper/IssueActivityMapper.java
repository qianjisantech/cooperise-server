package com.qianjisan.issue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianjisan.issue.entity.IssueActivity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 事项活动记录 Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface IssueActivityMapper extends BaseMapper<IssueActivity> {

}
