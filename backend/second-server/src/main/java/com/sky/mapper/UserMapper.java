package com.sky.mapper;

import com.sky.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 * @author 李萌
 * @since 2024-06-01
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
