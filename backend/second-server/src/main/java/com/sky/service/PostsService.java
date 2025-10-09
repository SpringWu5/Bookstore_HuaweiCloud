package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.dto.PostsPageQueryDTO;
import com.sky.entity.Posts;
import com.sky.result.PageResult;

/**
 * <p>
 * 帖子表 服务类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-13
 */
public interface PostsService extends IService<Posts> {

    /**
     * 求购分页查询
     * @param postsPageQueryDTO
     * @return
     */
    PageResult pageQuery(PostsPageQueryDTO postsPageQueryDTO);

    /**
     * 启动禁用商品
     * @param status
     * @param id
     * @return
     */
    void startOrStop(Integer status, Long id);

    /**
     * 求购分页查询(用户)
     * @param postsPageQueryDTO
     * @return
     */
    PageResult userpageQuery(PostsPageQueryDTO postsPageQueryDTO);
}
