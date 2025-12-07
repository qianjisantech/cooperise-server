package qianjisan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qianjisan.entity.FeedbackLike;

/**
 * 反馈点赞 Mapper 接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface FeedbackLikeMapper extends BaseMapper<FeedbackLike> {

}
