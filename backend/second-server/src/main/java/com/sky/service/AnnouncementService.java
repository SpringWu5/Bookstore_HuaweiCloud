package com.sky.service;

import com.sky.entity.Announcement;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 公告内容 服务类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-13
 */
public interface AnnouncementService extends IService<Announcement> {

    /**
     * 启动禁用分类
     * @param status
     * @param id
     * @return
     */
    void startOrStop(Integer status, Long id);
}
