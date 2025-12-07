package com.qianjisan.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.Result;
import com.qianjisan.annotation.RequiresPermission;
import com.qianjisan.common.dto.TrackingLogQueryDTO;
import com.qianjisan.common.dto.TrackingReportQueryDTO;
import com.qianjisan.request.TrackingLogRequest;
import com.qianjisan.vo.EventTypeStatisticsVO;
import com.qianjisan.vo.PageVO;
import com.qianjisan.vo.TrackingLogVO;
import com.qianjisan.vo.UserActivityVO;
import com.qianjisan.service.ITrackingLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 埋点日志控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "埋点管理", description = "埋点相关接口")
@RestController
@RequestMapping("/tracking")
@RequiredArgsConstructor
@Slf4j
public class TrackingLogController {

    private final ITrackingLogService trackingLogService;

    @Operation(summary = "分页查询埋点日志")
    @RequiresPermission("record:tracking:view")
    @PostMapping("/page")
    public Result<PageVO<TrackingLogVO>> page(@RequestBody TrackingLogQueryDTO query) {
        log.info("[分页查询埋点日志] 查询参数: {}", query);
        Page<TrackingLogVO> page = trackingLogService.pageQuery(query);
        return Result.success(PageVO.of(page));
    }

    @Operation(summary = "保存埋点日志")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody TrackingLogRequest request) {
        log.info("[保存埋点日志] 请求参数: {}", request);
        trackingLogService.saveTrackingLog(request);
        return Result.success();
    }

    @Operation(summary = "查询埋点日志详情")
    @RequiresPermission("record:tracking:view")
    @GetMapping("/{id}")
    public Result<TrackingLogVO> getById(@PathVariable Long id) {
        log.info("[查询埋点日志详情] ID: {}", id);
        TrackingLogVO vo = trackingLogService.getTrackingLogById(id);
        return Result.success(vo);
    }

    @Operation(summary = "统计埋点类型数量（柱状图）", description = "展示年月日每个埋点类型的量")
    @RequiresPermission("record:tracking:view")
    @PostMapping("/report/event-type-statistics")
    public Result<List<EventTypeStatisticsVO>> eventTypeStatistics(@RequestBody TrackingReportQueryDTO query) {
        log.info("[统计埋点类型数量] 查询参数: {}", query);
        List<EventTypeStatisticsVO> result = trackingLogService.statisticsByEventType(query);
        return Result.success(result);
    }

    @Operation(summary = "统计用户活跃量", description = "展示用户的活跃量")
    @RequiresPermission("record:tracking:view")
    @PostMapping("/report/user-activity")
    public Result<List<UserActivityVO>> userActivity(@RequestBody TrackingReportQueryDTO query) {
        log.info("[统计用户活跃量] 查询参数: {}", query);
        List<UserActivityVO> result = trackingLogService.statisticsUserActivity(query);
        return Result.success(result);
    }
}
