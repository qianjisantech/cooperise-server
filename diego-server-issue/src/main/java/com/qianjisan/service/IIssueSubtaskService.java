package com.qianjisan.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.common.dto.WorkspaceSubtaskQueryDTO;
import com.qianjisan.entity.IssueSubtask;


/**
 * WorkspaceSubtask服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IIssueSubtaskService extends IService<IssueSubtask> {

    /**
     * 分页查询子任务管理
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<IssueSubtask> pageSubtask(WorkspaceSubtaskQueryDTO query);
}
