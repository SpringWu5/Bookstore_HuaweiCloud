package com.sky.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.entity.Announcement;
import com.sky.mapper.AnnouncementMapper;
import com.sky.service.AnnouncementService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公告内容 服务实现类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-13
 */
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements AnnouncementService {

    /**
     * 启动禁用分类
     * @param status
     * @param id
     * @return
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Announcement announcement = getById(id);
        announcement.setStatus(status);
        updateById(announcement);
    }
}
