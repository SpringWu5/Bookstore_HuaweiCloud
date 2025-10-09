package com.sky.service;

import com.sky.entity.College;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 学院分类 服务类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-01
 */
public interface CollegeService extends IService<College> {

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
    void deleteByCollegeId(Long id);
}
