package com.sky.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sky.constant.MessageConstant;
import com.sky.entity.Dormitory;
import com.sky.entity.User;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DormitoryMapper;
import com.sky.service.DormitoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 宿舍分类 服务实现类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-01
 */
@Service
public class DormitoryServiceImpl extends ServiceImpl<DormitoryMapper, Dormitory> implements DormitoryService {

    /**
     * 启动禁用宿舍分类
     * @param status
     * @param id
     * @return
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Dormitory dormitory = getById(id);
        dormitory.setStatus(status);
        updateById(dormitory);
    }

    /**
     * 删除宿舍
     * @param id
     * @return
     */
    @Override
    public void deleteByCollegeId(Long id) {
        //判断当前宿舍分类下有无用户
        List<User> userList = Db.lambdaQuery(User.class)
                .eq(User::getCollegeId,id)
                .list();
        if(CollUtil.isNotEmpty(userList)){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL );
        }
        //删除宿舍
        removeById(id);
    }
}
