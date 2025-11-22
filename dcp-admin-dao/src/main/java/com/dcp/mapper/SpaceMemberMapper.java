package com.dcp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dcp.entity.SpaceMember;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * 空间成员 Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface SpaceMemberMapper extends BaseMapper<SpaceMember> {

    /**
     * 物理删除空间成员
     *
     * @param id 成员ID
     * @return 影响行数
     */
    @Delete("DELETE FROM space_member WHERE id = #{id}")
    int deleteByIdPhysically(Long id);
}
