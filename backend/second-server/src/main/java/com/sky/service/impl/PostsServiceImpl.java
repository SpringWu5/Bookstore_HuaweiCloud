package com.sky.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sky.dto.PostsPageQueryDTO;
import com.sky.entity.Posts;
import com.sky.entity.User;
import com.sky.mapper.PostsMapper;
import com.sky.result.PageResult;
import com.sky.service.PostsService;
import com.sky.vo.PostsVO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 帖子表 服务实现类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-13
 */
@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements PostsService {

    /**
     * 求购分页查询
     * @param postsPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(PostsPageQueryDTO postsPageQueryDTO) {
        String name = postsPageQueryDTO.getName();
        Long categoryId = postsPageQueryDTO.getCategoryId();
        Integer status = postsPageQueryDTO.getStatus();
        //1.构建分页条件
        //1.1分页
        Page<Posts> page = Page.of(postsPageQueryDTO.getPage(), postsPageQueryDTO.getPageSize());
        //1.2排序
        if(StrUtil.isNotBlank(postsPageQueryDTO.getSortBy())){
            //不为空
            page.addOrder(new OrderItem(postsPageQueryDTO.getSortBy(),postsPageQueryDTO.getIsAsc()));
        }else {
            //为空
            page.addOrder(new OrderItem("update_time",false));
        }
        //2.分页查询
        Page<Posts> p = lambdaQuery()
                .like(name != null, Posts::getName, name)
                .eq(categoryId != null, Posts::getCategoryId, categoryId)
                .eq(status != null, Posts::getStatus, status)
                .page(page);

        //3.1封装PageResult结果--总条数
        PageResult goodsPageResult = new PageResult();
        goodsPageResult.setTotal(p.getTotal());
        List<Posts> records = p.getRecords();
        if (CollUtil.isEmpty(records)) {
            goodsPageResult.setRecords(Collections.emptyList());//返回空集合
            return goodsPageResult;
        }
        //3.2封装PageResult结果--结果集合
        goodsPageResult.setRecords(records);
        //4.返回
        return goodsPageResult;
    }

    /**
     * 启动禁用商品
     * @param status
     * @param id
     * @return
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Posts posts = getById(id);
        posts.setStatus(status);
        updateById(posts);
    }

    /**
     * 求购分页查询(用户)
     * @param postsPageQueryDTO
     * @return
     */
    @Override
    public PageResult userpageQuery(PostsPageQueryDTO postsPageQueryDTO) {
        String name = postsPageQueryDTO.getName();
        Long categoryId = postsPageQueryDTO.getCategoryId();
        Integer status = postsPageQueryDTO.getStatus();
        //1.构建分页条件
        //1.1分页
        Page<Posts> page = Page.of(postsPageQueryDTO.getPage(), postsPageQueryDTO.getPageSize());
        //1.2排序
        if(StrUtil.isNotBlank(postsPageQueryDTO.getSortBy())){
            //不为空
            page.addOrder(new OrderItem(postsPageQueryDTO.getSortBy(),postsPageQueryDTO.getIsAsc()));
        }else {
            //为空
            page.addOrder(new OrderItem("update_time",false));
        }
        //2.分页查询
        Page<Posts> p = lambdaQuery()
                .like(name != null, Posts::getName, name)
                .eq(categoryId != null, Posts::getCategoryId, categoryId)
                .eq(status != null, Posts::getStatus, status)
                .eq(Posts::getStatus, 1)
                .page(page);

        //3.1封装PageResult结果--总条数
        PageResult goodsPageResult = new PageResult();
        goodsPageResult.setTotal(p.getTotal());
        List<Posts> records = p.getRecords();
        if (CollUtil.isEmpty(records)) {
            goodsPageResult.setRecords(Collections.emptyList());//返回空集合
            return goodsPageResult;
        }
        //3.2封装PageResult结果--结果集合
        List<PostsVO> postsVOS = BeanUtil.copyToList(records, PostsVO.class);
        postsVOS.forEach(postsVO -> {
            User user = Db.lambdaQuery(User.class)
                    .eq(User::getId,postsVO.getCreateUser())
                    .one();
            postsVO.setUserAvatar(user.getAvatar());
            postsVO.setUserName(user.getName());
        });
        goodsPageResult.setRecords(postsVOS);
        //4.返回
        return goodsPageResult;
    }
}
