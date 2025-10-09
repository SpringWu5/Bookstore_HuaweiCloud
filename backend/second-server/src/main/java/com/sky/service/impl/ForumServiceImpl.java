package com.sky.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sky.constant.MessageConstant;
import com.sky.entity.Forum;
import com.sky.entity.Posts;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.ForumMapper;
import com.sky.service.ForumService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 论坛分类 服务实现类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-13
 */
@Service
public class ForumServiceImpl extends ServiceImpl<ForumMapper, Forum> implements ForumService {
    /**
     * 启动禁用分类
     * @param status
     * @param id
     * @return
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Forum forum = getById(id);
        forum.setStatus(status);
        updateById(forum);
    }

    /**
     * 删除学院
     * @param id
     * @return
     */
    @Override
    public void deleteByForumId(Long id) {
        //判断当前学院分类下有无用户
        List<Posts> postsList = Db.lambdaQuery(Posts.class)
                .eq(Posts::getCategoryId,id)
                .list();
        if(CollUtil.isNotEmpty(postsList)){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_POSTS);
        }
        //删除学院
        removeById(id);
    }
}
