package com.sky.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sky.context.BaseContext;
import com.sky.dto.CollegeDto;
import com.sky.entity.College;
import com.sky.result.Result;
import com.sky.service.CollegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 学院分类 前端控制器
 * </p>
 *
 * @author 李萌
 * @since 2024-06-01
 */
@Api(tags = "学院分类相关接口")
@RestController
@RequestMapping("/admin/college")
@Slf4j
public class CollegeController {
    @Autowired
    private CollegeService collegeService;

    /**
     * 新增学院
     * @param collegeDto
     * @return
     */
    @ApiOperation("新增学院")
    @PostMapping
    public Result save(@RequestBody CollegeDto collegeDto){
        log.info("新增学院：{}",collegeDto);
        College college= BeanUtil.copyProperties(collegeDto, College.class);
        college.setCreateUser(BaseContext.getCurrentId());//创建人
        //自动调用自动配置信息......
        collegeService.save(college);
        return Result.success();
    }

    /**
     * 学院管理查询
     * @return
     */
    @ApiOperation("学院管理查询")
    @GetMapping
    public Result<List<College>> queryCollege(){
        log.info("学院管理查询");
        List<College> collegeList = Db.lambdaQuery(College.class)
                .list();
        return Result.success(collegeList);
    }

    /**
     * 启动禁用分类
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status")
    @ApiOperation("启动禁用学院分类")
    public Result startOrStop(Long id,Integer status){
        log.info("启动禁用学院分类：{},{}",status,id);
        collegeService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * 根据id查询学院信息
     * @param id
     * @return
     */
    @ApiOperation("根据id查询学院信息")
    @GetMapping("/{id}")
    public Result<College> getById(@PathVariable Long id){
        log.info("根据id查询学院信息，参数为：{}",id);
        College college = collegeService.getById(id);
        return Result.success(college);
    }

    /**
     * 编辑学院信息
     * @param collegeDto
     * @return
     */
    @ApiOperation("编辑学院信息")
    @PutMapping
    public Result update(@RequestBody CollegeDto collegeDto){
        log.info("编辑学院信息：{}",collegeDto);
        College college = BeanUtil.copyProperties(collegeDto, College.class);
        //自动调用自动配置信息........
        collegeService.updateById(college);
        return Result.success();
    }

    /**
     * 删除学院
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("删除学院")
    public Result deleteById(Long id){
        log.info("删除学院：{}",id);
        collegeService.deleteByCollegeId(id);
        return Result.success();
    }

}
