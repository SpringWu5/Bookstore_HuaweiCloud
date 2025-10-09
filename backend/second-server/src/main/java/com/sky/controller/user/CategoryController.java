package com.sky.controller.user;


import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController("userCategoryController")
@RequestMapping("/user/category")
@Slf4j

public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 用户端分类管理查询
     * @return
     */
    @ApiOperation("分类管理查询")
    @GetMapping
    public Result<List<Category>> userQueryCategory(){
        log.info("分类管理查询");
        List<Category> categoryList = Db.lambdaQuery(Category.class)
                .eq(Category::getStatus,1)
                .list();
        Category newCategory = new Category();
        newCategory.setId(null);
        newCategory.setName("全部");
        categoryList.add(0, newCategory);
        return Result.success(categoryList);
    }
}
