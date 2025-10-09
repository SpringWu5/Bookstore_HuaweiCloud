package com.sky.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sky.context.BaseContext;
import com.sky.dto.AnnouncementDto;
import com.sky.entity.Announcement;
import com.sky.result.Result;
import com.sky.service.AnnouncementService;
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
@Api(tags = "公告相关接口")
@RestController
@RequestMapping("/admin/announcement")
@Slf4j
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

    /**
     * 新增学院
     * @param announcementDto
     * @return
     */
    @ApiOperation("新增公告")
    @PostMapping
    public Result save(@RequestBody AnnouncementDto announcementDto){
        log.info("新增学院：{}",announcementDto);
        Announcement announcement = BeanUtil.copyProperties(announcementDto, Announcement.class);
        announcement.setCreateUser(BaseContext.getCurrentId());//创建人
        //自动调用自动配置信息......
        announcementService.save(announcement);
        return Result.success();
    }

    /**
     * 学院管理查询
     * @return
     */
    @ApiOperation("公告查询")
    @GetMapping
    public Result<List<Announcement>> queryAnnouncement(){
        log.info("公告查询");
        List<Announcement> announcementList = Db.lambdaQuery(Announcement.class)
                .list();
        return Result.success(announcementList);
    }

    /**
     * 启动禁用分类
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status")
    @ApiOperation("启动禁用公告")
    public Result startOrStop(Long id,Integer status){
        log.info("启动禁用公告：{},{}",status,id);
        announcementService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * 根据id查询学院信息
     * @param id
     * @return
     */
    @ApiOperation("根据id查询公告信息")
    @GetMapping("/{id}")
    public Result<Announcement> getById(@PathVariable Long id){
        log.info("根据id查询公告信息，参数为：{}",id);
        Announcement announcement = announcementService.getById(id);
        return Result.success(announcement);
    }

    /**
     * 编辑学院信息
     * @param announcementDto
     * @return
     */
    @ApiOperation("编辑公告信息")
    @PutMapping
    public Result update(@RequestBody AnnouncementDto announcementDto){
        log.info("编辑公告信息：{}",announcementDto);
        Announcement announcement = BeanUtil.copyProperties(announcementDto, Announcement.class);
        //自动调用自动配置信息........
        announcementService.updateById(announcement);
        return Result.success();
    }

    /**
     * 删除学院
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("删除公告")
    public Result deleteById(Long id){
        log.info("删除公告：{}",id);
        announcementService.removeById(id);
        return Result.success();
    }

}
