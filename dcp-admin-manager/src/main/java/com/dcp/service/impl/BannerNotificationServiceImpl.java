package com.dcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.common.util.BeanConverter;
import com.dcp.common.vo.BannerNotificationVO;
import com.dcp.entity.BannerNotification;
import com.dcp.mapper.BannerNotificationMapper;
import com.dcp.service.IBannerNotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * BannerNotification服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Service
public class BannerNotificationServiceImpl extends ServiceImpl<BannerNotificationMapper, BannerNotification> implements IBannerNotificationService {

    @Override
    public List<BannerNotificationVO> getActiveBanners() {
        LambdaQueryWrapper<BannerNotification> queryWrapper = new LambdaQueryWrapper<>();
        LocalDateTime now = LocalDateTime.now();
        queryWrapper.eq(BannerNotification::getStatus, "active")
                .le(BannerNotification::getStartTime, now)
                .ge(BannerNotification::getEndTime, now)
                .orderByDesc(BannerNotification::getCreateTime);
        List<BannerNotification> list = list(queryWrapper);
        return BeanConverter.convertList(list, BannerNotificationVO::new);
    }
}
