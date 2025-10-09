package com.sky.controller.user;


import com.sky.context.BaseContext;
import com.sky.entity.Comment;
import com.sky.result.Result;
import com.sky.service.CommentService;
import com.sky.vo.CommentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author 李萌
 * @since 2024-06-14
 */
@Api(tags = "评论相关接口")
@Slf4j
@RestController
@RequestMapping("/user/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 新增评论
     * @param comment
     * @return
     */
    @ApiOperation("新增评论")
    @PostMapping
    public Result save(@RequestBody Comment comment){
        log.info("新增评论：{}",comment);
        comment.setCreateUser(BaseContext.getCurrentId());//创建人
        comment.setUserId(BaseContext.getCurrentId());//用户id
        comment.setCreateTime(LocalDateTime.now());//创建时间
        commentService.save(comment);
        return Result.success();
    }

    /**
     * 查询评论
     * foreignName
     * foreignId
     * @return
     */
    @ApiOperation("查询评论")
    @GetMapping
    public Result<List<CommentVO>> list(String foreignName, Integer foreignId){
        log.info("查询评论");
        List<CommentVO> commentVOList = commentService.findById(foreignName,foreignId);
        return Result.success(commentVOList);
    }


}
