package qianjisan.cs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import qianjisan.cs.entity.Feedback;

/**
 * 反馈 Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {

}
