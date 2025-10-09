package com.sky.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.RePasswordDTO;
import com.sky.dto.UserLoginDTO;
import com.sky.dto.UserPageQueryDTO;
import com.sky.entity.User;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.UserMapper;
import com.sky.result.PageResult;
import com.sky.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        User user = lambdaQuery()
                .eq(User::getUsername, username)
                .one();

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // 进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());//加密
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (user.getLocking() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return user;
    }

    /**
     * 用户注册
     * @param userLoginDTO
     * @return
     */
    @Override
    public void register(UserLoginDTO userLoginDTO) {
        User user = BeanUtil.copyProperties(userLoginDTO,User.class);
        //加密
        user.setPassword(DigestUtils.md5DigestAsHex(userLoginDTO.getPassword().getBytes()));
        //注册
        save(user);
    }

    /**
     * 用户分页查询
     * @param userPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(UserPageQueryDTO userPageQueryDTO) {
        String name = userPageQueryDTO.getName();
        Long collegeId = userPageQueryDTO.getCollegeId();
        Long dormitoryId = userPageQueryDTO.getDormitoryId();
        Integer locking = userPageQueryDTO.getLocking();
        //1.构建分页条件
        //1.1分页
        Page<User> page = Page.of(userPageQueryDTO.getPage(), userPageQueryDTO.getPageSize());
        //1.2排序
        if(StrUtil.isNotBlank(userPageQueryDTO.getSortBy())){
            //不为空
            page.addOrder(new OrderItem(userPageQueryDTO.getSortBy(),userPageQueryDTO.getIsAsc()));
        }else {
            //为空
            page.addOrder(new OrderItem("update_time",false));
        }
        //2.分页查询
        Page<User> p = lambdaQuery()
                .like(name != null, User::getName, name)
                .eq(collegeId != null, User::getCollegeId, collegeId)
                .eq(dormitoryId != null, User::getDormitoryId, dormitoryId)
                .eq(locking != null, User::getLocking, locking)
                .eq(User::getStatus,1)
                .page(page);

        //3.1封装PageResult结果--总条数
        PageResult userPageResult = new PageResult();
        userPageResult.setTotal(p.getTotal());
        List<User> records = p.getRecords();
        if (CollUtil.isEmpty(records)) {
            userPageResult.setRecords(Collections.emptyList());//返回空集合
            return userPageResult;
        }
        //3.2封装PageResult结果--结果集合
        userPageResult.setRecords(records);
        //4.返回
        return userPageResult;
    }

    /**
     * 启动禁用用户账号
     * @param locking
     * @param id
     * @return
     */
    @Override
    public void startOrStop(Integer locking, Long id) {
        User user = getById(id);
        user.setLocking(locking);
        updateById(user);
    }

    /**
     * 修改密码
     *
     * @param rePasswordDTO
     * @return
     */
    @Override
    public void rePwd(RePasswordDTO rePasswordDTO) {
        String oldPwd = rePasswordDTO.getOldPwd();
        String newPwd = rePasswordDTO.getNewPwd();
        String rePwd = rePasswordDTO.getRePwd();

        Long id = BaseContext.getCurrentId();
        User user = getById(id);

        //密码比对
        // 进行md5加密，然后再进行比对
        oldPwd = DigestUtils.md5DigestAsHex(oldPwd.getBytes());//加密
        if (!oldPwd.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR_OLD);
        } else if (!Objects.equals(newPwd, rePwd)) {
                throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR_TWO);
            }else {
            // 进行md5加密，修改密码
            newPwd = DigestUtils.md5DigestAsHex(newPwd.getBytes());//加密
            lambdaUpdate()
                .set(User::getPassword,newPwd)
                .eq(User::getId,id)
                .update();
            }
    }
}
