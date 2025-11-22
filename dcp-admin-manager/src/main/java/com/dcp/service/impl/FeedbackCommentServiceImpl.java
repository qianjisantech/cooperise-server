package com.dcp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.entity.FeedbackComment;
import com.dcp.mapper.FeedbackCommentMapper;
import com.dcp.service.IFeedbackCommentService;
import org.springframework.stereotype.Service;

/**
 * FeedbackComment服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Service
public class FeedbackCommentServiceImpl extends ServiceImpl<FeedbackCommentMapper, FeedbackComment> implements IFeedbackCommentService {

}
