package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.dto.RePasswordDTO;
import com.sky.dto.UserLoginDTO;
import com.sky.dto.UserPageQueryDTO;
import com.sky.entity.User;
import com.sky.result.PageResult;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-01
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    User login(UserLoginDTO userLoginDTO);

    /**
     * 用户注册
     * @param userLoginDTO
     * @return
     */
    void register(UserLoginDTO userLoginDTO);

    /**
     * 用户分页查询
     * @param userPageQueryDTO
     * @return
     */
    PageResult pageQuery(UserPageQueryDTO userPageQueryDTO);

    /**
     * 启动禁用用户账号
     * @param locking
     * @param id
     * @return
     */
    void startOrStop(Integer locking, Long id);

     void rePwd(RePasswordDTO rePasswordDTO);

}
