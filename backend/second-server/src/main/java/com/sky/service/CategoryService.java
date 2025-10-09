package com.sky.service;

import com.sky.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 物品分类 服务类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-03
 */
public interface CategoryService extends IService<Category> {

    /**
     * 启动禁用分类
     * @param status
     * @param id
     * @return
     */
    void startOrStop(Integer status, Long id);

    /**
     * 删除分类
     * @param id
     * @return
     */
    void deleteByCollegeId(Long id);
}
