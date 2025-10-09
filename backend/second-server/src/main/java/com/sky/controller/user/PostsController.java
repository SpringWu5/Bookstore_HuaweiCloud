package com.sky.controller.user;


import com.sky.dto.PostsPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.PostsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 求购表 前端控制器
 * </p>
 *
 * @author 李萌
 * @since 2024-06-06
 */
@Api(tags = "论坛帖子相关接口")
@RestController("userPostsController")
@RequestMapping("/user/posts")
@Slf4j
public class PostsController {
    @Autowired
    private PostsService postsService;

    /**
     * 求购分页查询(用户)
     * @param postsPageQueryDTO
     * @return
     */
    @ApiOperation("求购分页查询")
    @GetMapping("/page")
    public Result<PageResult> queryGoodsPage(PostsPageQueryDTO postsPageQueryDTO){
        log.info("求购分页查询，参数为：{}",postsPageQueryDTO);
        return Result.success(postsService.userpageQuery(postsPageQueryDTO));
    }


}
