package com.sky.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sky.context.BaseContext;
import com.sky.dto.ForumDto;
import com.sky.entity.Forum;
import com.sky.result.Result;
import com.sky.service.ForumService;
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
@Api(tags = "论坛分类相关接口")
@RestController
@RequestMapping("/admin/forum")
@Slf4j
public class ForumController {
    @Autowired
    private ForumService forumService;

    /**
     * 新增学院
     * @param forumDto
     * @return
     */
    @ApiOperation("新增学院")
    @PostMapping
    public Result save(@RequestBody ForumDto forumDto){
        log.info("新增学院：{}",forumDto);
        Forum forum= BeanUtil.copyProperties(forumDto, Forum.class);
        forum.setCreateUser(BaseContext.getCurrentId());//创建人
        //自动调用自动配置信息......
        forumService.save(forum);
        return Result.success();
    }

    /**
     * 学院管理查询
     * @return
     */
    @ApiOperation("学院管理查询")
    @GetMapping
    public Result<List<Forum>> queryForum(){
        log.info("学院管理查询");
        List<Forum> forumList = Db.lambdaQuery(Forum.class)
                .list();
        return Result.success(forumList);
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
        forumService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * 根据id查询学院信息
     * @param id
     * @return
     */
    @ApiOperation("根据id查询学院信息")
    @GetMapping("/{id}")
    public Result<Forum> getById(@PathVariable Long id){
        log.info("根据id查询学院信息，参数为：{}",id);
        Forum forum = forumService.getById(id);
        return Result.success(forum);
    }

    /**
     * 编辑学院信息
     * @param forumDto
     * @return
     */
    @ApiOperation("编辑学院信息")
    @PutMapping
    public Result update(@RequestBody ForumDto forumDto){
        log.info("编辑学院信息：{}",forumDto);
        Forum forum = BeanUtil.copyProperties(forumDto, Forum.class);
        //自动调用自动配置信息........
        forumService.updateById(forum);
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
        forumService.deleteByForumId(id);
        return Result.success();
    }

}
