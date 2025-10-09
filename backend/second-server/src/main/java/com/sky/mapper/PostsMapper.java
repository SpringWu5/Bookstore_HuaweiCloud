package com.sky.mapper;

import com.sky.entity.Posts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 帖子表 Mapper 接口
 * </p>
 *
 * @author 李萌
 * @since 2024-06-13
 */
@Mapper
public interface PostsMapper extends BaseMapper<Posts> {

}
