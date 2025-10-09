package com.sky.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sky.context.BaseContext;
import com.sky.dto.DormitoryDto;
import com.sky.entity.Dormitory;
import com.sky.result.Result;
import com.sky.service.DormitoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 宿舍分类 前端控制器
 * </p>
 *
 * @author 李萌
 * @since 2024-06-01
 */
@Api(tags = "宿舍分类相关接口")
@RestController
@RequestMapping("/admin/dormitory")
@Slf4j
public class DormitoryController {
    @Autowired
    private DormitoryService dormitoryService;

    /**
     * 新增宿舍
     * @param dormitoryDto
     * @return
     */
    @ApiOperation("新增宿舍")
    @PostMapping
    public Result save(@RequestBody DormitoryDto dormitoryDto){
        log.info("新增宿舍：{}",dormitoryDto);
        Dormitory dormitory = BeanUtil.copyProperties(dormitoryDto, Dormitory.class);
        dormitory.setCreateUser(BaseContext.getCurrentId());//创建人
        //自动调用自动配置信息......
        dormitoryService.save(dormitory);
        return Result.success();
    }

    /**
     * 宿舍管理查询
     * @return
     */
    @ApiOperation("宿舍管理查询")
    @GetMapping
    public Result<List<Dormitory>> queryDormitory(){
        log.info("宿舍管理查询");
        List<Dormitory> dormitoryList = Db.lambdaQuery(Dormitory.class)
                .list();
        return Result.success(dormitoryList);
    }

    /**
     * 启动禁用宿舍分类
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status")
    @ApiOperation("启动禁用宿舍分类")
    public Result startOrStop(Integer status,Long id){
        log.info("启动禁用宿舍分类：{},{}",status,id);
        dormitoryService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * 根据id查询宿舍信息
     * @param id
     * @return
     */
    @ApiOperation("根据id查询宿舍信息")
    @GetMapping("/{id}")
    public Result<Dormitory> getById(@PathVariable Long id){
        log.info("根据id查询宿舍信息，参数为：{}",id);
        Dormitory dormitory = dormitoryService.getById(id);
        return Result.success(dormitory);
    }

    /**
     * 编辑宿舍信息
     * @param dormitoryDto
     * @return
     */
    @ApiOperation("编辑宿舍信息")
    @PutMapping
    public Result update(@RequestBody DormitoryDto dormitoryDto){
        log.info("编辑宿舍信息：{}",dormitoryDto);
        Dormitory dormitory = BeanUtil.copyProperties(dormitoryDto, Dormitory.class);
        //自动调用自动配置信息........
        dormitoryService.updateById(dormitory);
        return Result.success();
    }

    /**
     * 删除宿舍
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("删除宿舍")
    public Result deleteById(Long id){
        log.info("删除宿舍：{}",id);
        dormitoryService.deleteByCollegeId(id);
        return Result.success();
    }
}
