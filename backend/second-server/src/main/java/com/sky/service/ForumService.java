package com.sky.service;

import com.sky.entity.Forum;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 论坛分类 服务类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-13
 */
public interface ForumService extends IService<Forum> {
    /**
     * 启动禁用分类
     * @param status
     * @param id
     * @return
     */
    void startOrStop(Integer status, Long id);

    /**
     * 删除学院
     * @param id
     * @return
     */
    void deleteByForumId(Long id);
}
