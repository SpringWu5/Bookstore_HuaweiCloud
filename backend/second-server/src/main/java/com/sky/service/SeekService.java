package com.sky.service;

import com.sky.dto.SeekPageQueryDTO;
import com.sky.entity.Seek;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.result.PageResult;

/**
 * <p>
 * 求购表 服务类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-06
 */
public interface SeekService extends IService<Seek> {

    /**
     * 求购分页查询
     * @param seekPageQueryDTO
     * @return
     */
    PageResult pageQuery(SeekPageQueryDTO seekPageQueryDTO);

    /**
     * 启动禁用商品
     * @param status
     * @param id
     * @return
     */
    void startOrStop(Integer status, Long id);

    /**
     * 求购分页查询(用户)
     * @param seekPageQueryDTO
     * @return
     */
    PageResult userpageQuery(SeekPageQueryDTO seekPageQueryDTO);

    /**
     * 我的求购分页查询
     * @param seekPageQueryDTO
     * @return
     */
    PageResult myPageQuery(SeekPageQueryDTO seekPageQueryDTO);
}
