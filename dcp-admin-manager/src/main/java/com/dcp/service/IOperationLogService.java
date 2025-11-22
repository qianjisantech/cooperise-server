package com.dcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.common.dto.OperationLogQueryDTO;
import com.dcp.common.vo.OperationLogVO;
import com.dcp.entity.SysOperationLog;

/**
 * 操作日志服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IOperationLogService extends IService<SysOperationLog> {

    /**
     * 分页查询操作日志
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<OperationLogVO> pageQuery(OperationLogQueryDTO query);

    /**
     * 保存操作日志
     *
     * @param sysOperationLog 操作日志
     */
    void saveLog(SysOperationLog sysOperationLog);
}
