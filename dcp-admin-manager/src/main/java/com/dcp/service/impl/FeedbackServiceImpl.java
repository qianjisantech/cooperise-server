package com.dcp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.entity.Feedback;
import com.dcp.mapper.FeedbackMapper;
import com.dcp.service.IFeedbackService;
import org.springframework.stereotype.Service;

/**
 * Feedback服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements IFeedbackService {

}
