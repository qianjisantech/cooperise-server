package com.qianjisan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianjisan.entity.WorkspaceIssueAttachment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 事项附件 Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface IssueAttachmentMapper extends BaseMapper<WorkspaceIssueAttachment> {

}
