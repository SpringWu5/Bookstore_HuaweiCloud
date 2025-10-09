package com.sky.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.sky.entity.Comment;
import com.sky.mapper.CommentMapper;
import com.sky.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.vo.CommentVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-14
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {


    /**
     * 查询评论
     * foreignName
     * foreignId
     * @return
     */
    @Override
    public List<CommentVO> findById(String foreignName, Integer foreignId) {
        List<Comment> list = lambdaQuery()
                .eq(Comment::getForeignName,foreignName)
                .eq(Comment::getForeignId,foreignId)
                .eq(Comment::getPid,0)
                .list();
        List<CommentVO> commentVOList = BeanUtil.copyToList(list, CommentVO.class);
        for(CommentVO commentVO : commentVOList){
            List<Comment> commentList = lambdaQuery()
                    .eq(Comment::getForeignName,foreignName)
                    .eq(Comment::getForeignId,foreignId)
                    .eq(Comment::getPid,commentVO.getId())
                    .list();
            commentVO.setChildren(commentList);
        }
        return commentVOList;
    }
}
