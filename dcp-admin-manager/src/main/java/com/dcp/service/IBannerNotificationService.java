package com.dcp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.common.vo.BannerNotificationVO;
import com.dcp.entity.BannerNotification;

import java.util.List;

/**
 * BannerNotification服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IBannerNotificationService extends IService<BannerNotification> {

    /**
     * 获取当前激活的Banner通知列表
     *
     * @return 激活的Banner列表VO
     */
    List<BannerNotificationVO> getActiveBanners();
}
