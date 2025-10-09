package com.sky.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDto;
import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 物品分类 前端控制器
 * </p>
 *
 * @author 李萌
 * @since 2024-06-03
 */
@Api(tags = "物品分类相关接口")
@RestController("adminCategoryController")
@RequestMapping("/admin/category")
@Slf4j

public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param categoryDto
     * @return
     */
    @ApiOperation("新增分类")
    @PostMapping
    public Result save(@RequestBody CategoryDto categoryDto){
        log.info("新增分类：{}",categoryDto);
        Category category= BeanUtil.copyProperties(categoryDto, Category.class);
        category.setCreateUser(BaseContext.getCurrentId());//创建人
        //自动调用自动配置信息......
        categoryService.save(category);
        return Result.success();
    }

    /**
     * 分类管理查询
     * @return
     */
    @ApiOperation("分类管理查询")
    @GetMapping
    public Result<List<Category>> queryCategory(){
        log.info("分类管理查询");
        List<Category> categoryList = Db.lambdaQuery(Category.class)
                .list();
        return Result.success(categoryList);
    }

    /**
     * 启动禁用分类
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status")
    @ApiOperation("启动禁用分类")
    public Result startOrStop(Long id,Integer status){
        log.info("启动禁用分类：{},{}",status,id);
        categoryService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * 根据id查询分类信息
     * @param id
     * @return
     */
    @ApiOperation("根据id查询分类信息")
    @GetMapping("/{id}")
    public Result<Category> getById(@PathVariable Long id){
        log.info("根据id查询分类信息，参数为：{}",id);
        Category category = categoryService.getById(id);
        return Result.success(category);
    }

    /**
     * 编辑分类信息
     * @param categoryDto
     * @return
     */
    @ApiOperation("编辑分类信息")
    @PutMapping
    public Result update(@RequestBody CategoryDto categoryDto){
        log.info("编辑分类信息：{}",categoryDto);
        Category category = BeanUtil.copyProperties(categoryDto, Category.class);
        //自动调用自动配置信息........
        categoryService.updateById(category);
        return Result.success();
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("删除分类")
    public Result deleteById(Long id){
        log.info("删除分类：{}",id);
        categoryService.deleteByCollegeId(id);
        return Result.success();
    }
}
