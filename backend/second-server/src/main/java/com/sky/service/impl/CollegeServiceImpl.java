package com.sky.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sky.constant.MessageConstant;
import com.sky.entity.College;
import com.sky.entity.User;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CollegeMapper;
import com.sky.service.CollegeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学院分类 服务实现类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-01
 */
@Service
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements CollegeService {

    /**
     * 启动禁用分类
     * @param status
     * @param id
     * @return
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        College college = getById(id);
        college.setStatus(status);
        updateById(college);
    }

    /**
     * 删除学院
     * @param id
     * @return
     */
    @Override
    public void deleteByCollegeId(Long id) {
        //判断当前学院分类下有无用户
        List<User> userList = Db.lambdaQuery(User.class)
                .eq(User::getCollegeId,id)
                .list();
        if(CollUtil.isNotEmpty(userList)){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        //删除学院
        removeById(id);
    }
}
