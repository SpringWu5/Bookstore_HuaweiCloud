package com.sky.controller.user;


import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sky.entity.Announcement;
import com.sky.result.Result;
import com.sky.service.AnnouncementService;
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
 * 求购表 前端控制器
 * </p>
 *
 * @author 李萌
 * @since 2024-06-06
 */
@Api(tags = "系统公告相关接口")
@RestController("userAnnouncementController")
@RequestMapping("/user/announcement")
@Slf4j
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

    /**
     * 系统公告查询(用户)
     * @return
     */
    @ApiOperation("公告查询")
    @GetMapping
    public Result<List<Announcement>> queryAnnouncement(){
        log.info("公告查询");
        List<Announcement> announcementList = Db.lambdaQuery(Announcement.class)
                .eq(Announcement::getStatus,1)
                .list();
        return Result.success(announcementList);
    }
}
