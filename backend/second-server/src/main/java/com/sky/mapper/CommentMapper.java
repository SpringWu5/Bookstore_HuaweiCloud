package com.sky.mapper;

import com.sky.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author 李萌
 * @since 2024-06-14
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
