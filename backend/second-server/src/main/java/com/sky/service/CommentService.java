package com.sky.service;

import com.sky.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.vo.CommentVO;

import java.util.List;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-14
 */
public interface CommentService extends IService<Comment> {


    /**
     * 查询评论
     * foreignName
     * foreignId
     * @return
     */
    List<CommentVO> findById(String foreignName, Integer foreignId);
}
