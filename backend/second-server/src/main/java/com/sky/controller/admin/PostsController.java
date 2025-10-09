package com.sky.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.sky.context.BaseContext;
import com.sky.dto.PostsDTO;
import com.sky.dto.PostsPageQueryDTO;
import com.sky.entity.Posts;
import com.sky.entity.Seek;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.PostsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 求购表 前端控制器
 * </p>
 *
 * @author 李萌
 * @since 2024-06-06
 */
@Api(tags = "帖子相关接口")
@RestController("adminPostsController")
@RequestMapping("/admin/posts")
@Slf4j
public class PostsController {
    @Autowired
    private PostsService postsService;

    /**
     * 新增求购
     * @param PostsDTO
     * @return
     */
    @ApiOperation("新增帖子")
    @PostMapping
    public Result save(@RequestBody PostsDTO PostsDTO){
        log.info("新增帖子：{}",PostsDTO);
        Posts posts = BeanUtil.copyProperties(PostsDTO, Posts.class);
        posts.setCreateUser(BaseContext.getCurrentId());//创建人
        //自动调用自动配置信息......
        postsService.save(posts);
        return Result.success();
    }

    /**
     * 求购分页查询
     * @param postsPageQueryDTO
     * @return
     */
    @ApiOperation("求购分页查询")
    @GetMapping("/page")
    public Result<PageResult> queryGoodsPage(PostsPageQueryDTO postsPageQueryDTO){
        log.info("求购分页查询，参数为：{}",postsPageQueryDTO);
        return Result.success(postsService.pageQuery(postsPageQueryDTO));
    }

    /**
     * 启动禁用商品
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status")
    @ApiOperation("启动禁用帖子")
    public Result startOrStop(Integer status,Long id){
        log.info("启动禁用帖子：{},{}",status,id);
        postsService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * 根据id查询商品信息
     * @param id
     * @return
     */
    @ApiOperation("根据id查询商品信息")
    @GetMapping("/{id}")
    public Result<Posts> getById(@PathVariable Long id){
        log.info("根据id查询商品信息，参数为：{}",id);
        Posts posts= postsService.getById(id);
        return Result.success(posts);
    }

    /**
     * 编辑商品信息
     * @param postsDTO
     * @return
     */
    @ApiOperation("编辑帖子信息")
    @PutMapping
    public Result update(@RequestBody PostsDTO postsDTO){
        log.info("编辑商品信息：{}",postsDTO);
        Posts posts= BeanUtil.copyProperties(postsDTO, Posts.class);
        //自动调用自动配置信息........
        postsService.updateById(posts);
        return Result.success();
    }

    /**
     * 根据id删除商品信息
     * @param id
     * @return
     */
    @ApiOperation("根据id删除商品信息")
    @DeleteMapping("/{id}")
    public Result<Seek> deleteById(@PathVariable Long id){
        log.info("根据id删除商品信息，参数为：{}",id);
        postsService.removeById(id);
        return Result.success();
    }
}
