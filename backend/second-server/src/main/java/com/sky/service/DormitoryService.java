package com.sky.service;

import com.sky.entity.Dormitory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 宿舍分类 服务类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-01
 */
public interface DormitoryService extends IService<Dormitory> {

    /**
     * 启动禁用宿舍分类
     * @param status
     * @param id
     * @return
     */
    void startOrStop(Integer status, Long id);

    /**
     * 删除宿舍
     * @param id
     * @return
     */
    void deleteByCollegeId(Long id);
}
