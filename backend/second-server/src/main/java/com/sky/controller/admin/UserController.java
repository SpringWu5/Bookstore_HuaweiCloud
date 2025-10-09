package com.sky.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sky.constant.JwtClaimsConstant;
import com.sky.context.BaseContext;
import com.sky.dto.RePasswordDTO;
import com.sky.dto.UserDTO;
import com.sky.dto.UserLoginDTO;
import com.sky.dto.UserPageQueryDTO;
import com.sky.entity.College;
import com.sky.entity.Dormitory;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import com.sky.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author 李萌
 * @since 2024-06-01
 */
@Api(tags = "用户相关接口")
@RestController
@RequestMapping("/user/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody @Validated UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);
        User user = userService.login(userLoginDTO);
        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);
        //构建Redis中的key，规则：userId_token
        String key = user.getId() + "_" + token;
        //存储Redis
        redisTemplate.opsForValue().set(key,token,2, TimeUnit.HOURS);

        //把user的数据copy到UserloyeeLoginVO中
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .token(token)
                .status(user.getStatus())
                .build();

        return Result.success(userLoginVO);
    }

    /**
     * 用户注册
     * @param userLoginDTO
     * @return
     */
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result register(@RequestBody @Validated UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        log.info("用户注册用户名: {},密码已加密", username);
        //查询用户
        User user = Db.lambdaQuery(User.class)
                .eq(User::getUsername,username)
                .one();
        if (user == null) {
            //没有占用
            //判定二次输入密码是否一致
            //不一致时
            if(!Objects.equals(userLoginDTO.getPassword(), userLoginDTO.getRePassword())){
                return Result.error("两次输入密码不一致");
            }else{
                //注册
                userService.register(userLoginDTO);
                return Result.success();
            }
        } else {
            //占用
            return Result.error("用户名已被占用");
        }
    }

    /**
     * 用户退出
     * @return
     */
    @ApiOperation("用户退出")
    @PostMapping("/logout")
    public Result logout(@RequestHeader("token") String token) {
        log.info("用户退出");

        //构建Redis中的key，规则：userId_token
        String key = BaseContext.getCurrentId() + "_" + token;
        //删除Redis
        redisTemplate.delete(key);
        return Result.success();
    }

    /**
     * 用户分页查询
     * @param userPageQueryDTO
     * @return
     */
    @ApiOperation("用户分页查询")
    @GetMapping("/page")
    public Result<PageResult> queryUserPage(UserPageQueryDTO userPageQueryDTO){
        log.info("员工分页查询，参数为：{}",userPageQueryDTO);
        return Result.success(userService.pageQuery(userPageQueryDTO));
    }

    /**
     * 启动禁用用户账号
     * @param locking
     * @param id
     * @return
     */
    @PostMapping("/locking")
    @ApiOperation("启动禁用用户账号")
    public Result startOrStop(Integer locking,Long id){
        log.info("启动禁用用户账号：{},{}",locking,id);
        userService.startOrStop(locking,id);
        return Result.success();
    }

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @ApiOperation("根据id查询用户信息")
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Long id){
        log.info("根据id查询用户信息，参数为：{}",id);
        User user = userService.getById(id);
        return Result.success(user);
    }

    /**
     * 编辑用户信息
     * @param userDTO
     * @return
     */
    @ApiOperation("编辑用户信息")
    @PutMapping
    public Result update(@RequestBody @Validated UserDTO userDTO){
        log.info("编辑用户信息：{}",userDTO);
        User user= BeanUtil.copyProperties(userDTO, User.class);
        //自动调用自动配置信息........
        userService.updateById(user);
        return Result.success();
    }

    /**
     * 根据id删除用户信息
     * @param id
     * @return
     */
    @ApiOperation("根据id删除用户信息")
    @DeleteMapping("/{id}")
    public Result<User> deleteById(@PathVariable Long id){
        log.info("根据id删除用户信息，参数为：{}",id);
        userService.removeById(id);
        return Result.success();
    }

    /**
     * 查询登录用户用户信息
     * @return
     */
    @ApiOperation("查询登录用户用户信息")
    @GetMapping("/userInfo")
    public Result<UserVO> getLoginUser(){
        log.info("查询登录用户用户信息");
        Long id = BaseContext.getCurrentId();
        if (id == null) {
            return Result.error("未登录或登录已过期");
        }
        User user = userService.getById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        College college = Db.lambdaQuery(College.class)
                .eq(College::getId,user.getCollegeId())
                .one();
        Dormitory dormitory = Db.lambdaQuery(Dormitory.class)
                .eq(Dormitory::getId,user.getDormitoryId())
                .one();
        userVO.setCollegeName(college != null ? college.getName() : null);
        userVO.setDormitoryName(dormitory != null ? dormitory.getName() : null);
        return Result.success(userVO);
    }

    /**
     * 修改密码
     * @param rePasswordDTO
     * @return
     */
    @ApiOperation("修改密码")
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody RePasswordDTO rePasswordDTO,@RequestHeader("token") String token){
        log.info("修改密码，参数为：{}",rePasswordDTO);
        if(Objects.equals(rePasswordDTO.getOldPwd(), rePasswordDTO.getNewPwd())){
            return Result.error("新密码和旧密码不能一样") ;
        }
        userService.rePwd(rePasswordDTO);
        //构建Redis中的key，规则：userId_token
        String key = BaseContext.getCurrentId() + "_" + token;
        //删除Redis
        redisTemplate.delete(key);

        return Result.success();
    }
}
