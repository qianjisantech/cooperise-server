package com.qianjisan.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.common.dto.TrackingLogQueryDTO;
import com.qianjisan.common.dto.TrackingReportQueryDTO;
import com.qianjisan.common.request.TrackingLogRequest;
import com.qianjisan.common.vo.EventTypeStatisticsVO;
import com.qianjisan.common.vo.TrackingLogVO;
import com.qianjisan.common.vo.UserActivityVO;
import com.qianjisan.entity.TrackingLog;

import java.util.List;

/**
 * 埋点日志服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface ISysTrackingLogService extends IService<TrackingLog> {

    /**
     * 分页查询埋点日志
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<TrackingLogVO> pageQuery(TrackingLogQueryDTO query);

    /**
     * 保存埋点日志
     *
     * @param request 请求对象
     */
    void saveTrackingLog(TrackingLogRequest request);

    /**
     * 根据ID查询埋点日志详情
     *
     * @param id 日志ID
     * @return 日志详情
     */
    TrackingLogVO getTrackingLogById(Long id);

    /**
     * 统计埋点类型数量（按时间维度）
     * 用于柱状图展示年月日每个埋点类型的量
     *
     * @param query 查询条件
     * @return 统计结果
     */
    List<EventTypeStatisticsVO> statisticsByEventType(TrackingReportQueryDTO query);

    /**
     * 统计用户活跃量（按时间维度）
     *
     * @param query 查询条件
     * @return 统计结果
     */
    List<UserActivityVO> statisticsUserActivity(TrackingReportQueryDTO query);
}
