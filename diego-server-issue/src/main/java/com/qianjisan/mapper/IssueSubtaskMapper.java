package com.qianjisan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianjisan.entity.WorkspaceSubtask;
import org.apache.ibatis.annotations.Mapper;

/**
 * 子任务 Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface IssueSubtaskMapper extends BaseMapper<WorkspaceSubtask> {

}
