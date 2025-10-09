package com.sky.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sky.context.BaseContext;
import com.sky.dto.SeekPageQueryDTO;
import com.sky.entity.Seek;
import com.sky.entity.User;
import com.sky.mapper.SeekMapper;
import com.sky.result.PageResult;
import com.sky.service.SeekService;
import com.sky.service.UserService;
import com.sky.vo.SeekVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 求购表 服务实现类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-06
 */
@Service
public class SeekServiceImpl extends ServiceImpl<SeekMapper, Seek> implements SeekService {
    @Autowired
    private UserService userService;

    /**
     * 求购分页查询
     * @param seekPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(SeekPageQueryDTO seekPageQueryDTO) {
        String name = seekPageQueryDTO.getName();
        Long categoryId = seekPageQueryDTO.getCategoryId();
        Integer status = seekPageQueryDTO.getStatus();
        //1.构建分页条件
        //1.1分页
        Page<Seek> page = Page.of(seekPageQueryDTO.getPage(), seekPageQueryDTO.getPageSize());
        //1.2排序
        if(StrUtil.isNotBlank(seekPageQueryDTO.getSortBy())){
            //不为空
            page.addOrder(new OrderItem(seekPageQueryDTO.getSortBy(),seekPageQueryDTO.getIsAsc()));
        }else {
            //为空
            page.addOrder(new OrderItem("update_time",false));
        }
        //2.分页查询
        Page<Seek> p = lambdaQuery()
                .like(name != null, Seek::getName, name)
                .eq(categoryId != null, Seek::getCategoryId, categoryId)
                .eq(status != null, Seek::getStatus, status)
                .page(page);

        //3.1封装PageResult结果--总条数
        PageResult goodsPageResult = new PageResult();
        goodsPageResult.setTotal(p.getTotal());
        List<Seek> records = p.getRecords();
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
        Seek seek = getById(id);
        seek.setStatus(status);
        updateById(seek);
    }

    /**
     * 求购分页查询(用户)
     * @param seekPageQueryDTO
     * @return
     */
    @Override
    public PageResult userpageQuery(SeekPageQueryDTO seekPageQueryDTO) {
        String name = seekPageQueryDTO.getName();
        Long categoryId = seekPageQueryDTO.getCategoryId();
        Integer status = seekPageQueryDTO.getStatus();
        //1.构建分页条件
        //1.1分页
        Page<Seek> page = Page.of(seekPageQueryDTO.getPage(), seekPageQueryDTO.getPageSize());
        //1.2排序
        if(StrUtil.isNotBlank(seekPageQueryDTO.getSortBy())){
            //不为空
            page.addOrder(new OrderItem(seekPageQueryDTO.getSortBy(),seekPageQueryDTO.getIsAsc()));
        }else {
            //为空
            page.addOrder(new OrderItem("update_time",false));
        }
        //2.分页查询
        Page<Seek> p = lambdaQuery()
                .like(name != null, Seek::getName, name)
                .eq(categoryId != null, Seek::getCategoryId, categoryId)
                .eq(status != null, Seek::getStatus, status)
                .and(wrapper -> wrapper.eq(Seek::getStatus, 1).or().eq(Seek::getStatus, 2))
                .page(page);

        //3.1封装PageResult结果--总条数
        PageResult goodsPageResult = new PageResult();
        goodsPageResult.setTotal(p.getTotal());
        List<Seek> records = p.getRecords();
        if (CollUtil.isEmpty(records)) {
            goodsPageResult.setRecords(Collections.emptyList());//返回空集合
            return goodsPageResult;
        }
        //3.2封装PageResult结果--结果集合
        List<SeekVO> seekVOS = BeanUtil.copyToList(records, SeekVO.class);
        seekVOS.forEach(seekVO -> {
            User user = Db.lambdaQuery(User.class)
                    .eq(User::getId,seekVO.getCreateUser())
                    .one();
            seekVO.setUserAvatar(user.getAvatar());
            seekVO.setUserName(user.getName());
        });
        goodsPageResult.setRecords(seekVOS);
        //4.返回
        return goodsPageResult;
    }

    /**
     * 我的求购分页查询
     * @param seekPageQueryDTO
     * @return
     */
    @Override
    public PageResult myPageQuery(SeekPageQueryDTO seekPageQueryDTO) {
        String name = seekPageQueryDTO.getName();
        Long categoryId = seekPageQueryDTO.getCategoryId();
        Integer status = seekPageQueryDTO.getStatus();
        //1.构建分页条件
        //1.1分页
        Page<Seek> page = Page.of(seekPageQueryDTO.getPage(), seekPageQueryDTO.getPageSize());
        //1.2排序
        if(StrUtil.isNotBlank(seekPageQueryDTO.getSortBy())){
            //不为空
            page.addOrder(new OrderItem(seekPageQueryDTO.getSortBy(),seekPageQueryDTO.getIsAsc()));
        }else {
            //为空
            page.addOrder(new OrderItem("update_time",false));
        }
        //2.分页查询
        Page<Seek> p = lambdaQuery()
                .like(name != null, Seek::getName, name)
                .eq(categoryId != null, Seek::getCategoryId, categoryId)
                .eq(status != null, Seek::getStatus, status)
                .and(wrapper -> wrapper.eq(Seek::getStatus, 1).or().eq(Seek::getStatus, 2))
                .eq(Seek::getCreateUser, BaseContext.getCurrentId())
                .page(page);

        //3.1封装PageResult结果--总条数
        PageResult goodsPageResult = new PageResult();
        goodsPageResult.setTotal(p.getTotal());
        List<Seek> records = p.getRecords();

        if (CollUtil.isEmpty(records)) {
            goodsPageResult.setRecords(Collections.emptyList());//返回空集合
            return goodsPageResult;
        }
        //3.2封装PageResult结果--结果集合
        goodsPageResult.setRecords(records);
        //4.返回
        return goodsPageResult;
    }
}
