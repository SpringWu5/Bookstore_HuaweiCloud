package com.sky.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sky.context.BaseContext;
import com.sky.dto.GoodsDTO;
import com.sky.dto.GoodsPageQueryDTO;
import com.sky.entity.*;
import com.sky.mapper.GoodsMapper;
import com.sky.result.PageResult;
import com.sky.service.*;
import com.sky.vo.CollectionVO;
import com.sky.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-03
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Autowired
    private GoodsDetailService goodsDetailService;
    @Autowired
    private UserService userService;
    @Autowired
    CollegeService collegeService;
    @Autowired
    DormitoryService dormitoryService;
    /**
     * 商品分页查询
     * @param goodsPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(GoodsPageQueryDTO goodsPageQueryDTO) {
        String name = goodsPageQueryDTO.getName();
        String olds = goodsPageQueryDTO.getOlds();
        Long categoryId = goodsPageQueryDTO.getCategoryId();
        Integer status = goodsPageQueryDTO.getStatus();
        //1.构建分页条件
        //1.1分页
        Page<Goods> page = Page.of(goodsPageQueryDTO.getPage(), goodsPageQueryDTO.getPageSize());
        //1.2排序
        if(StrUtil.isNotBlank(goodsPageQueryDTO.getSortBy())){
            //不为空
            page.addOrder(new OrderItem(goodsPageQueryDTO.getSortBy(),goodsPageQueryDTO.getIsAsc()));
        }else {
            //为空
            page.addOrder(new OrderItem("update_time",false));
        }
        //2.分页查询
        Page<Goods> p = lambdaQuery()
                .like(name != null, Goods::getName, name)
                .eq(olds != null, Goods::getOlds, olds)
                .eq(categoryId != null, Goods::getCategoryId, categoryId)
                .eq(status != null, Goods::getStatus, status)
                .page(page);

        //3.1封装PageResult结果--总条数
        PageResult goodsPageResult = new PageResult();
        goodsPageResult.setTotal(p.getTotal());
        List<Goods> records = p.getRecords();
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
    @Transactional
    public void startOrStop(Integer status, Long id) {
        Goods goods = getById(id);
        goods.setStatus(status);
        updateById(goods);

        //删除对应的goods—Detail表
        List<GoodsDetail> goodsDetailList = Db.lambdaQuery(GoodsDetail.class)
                .eq(GoodsDetail::getGoodsId,goods.getId())
                .list();
        goodsDetailService.removeByIds(goodsDetailList);
    }

    /**
     * 商品分页查询(用户查询)
     * @param goodsPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQueryByuser(GoodsPageQueryDTO goodsPageQueryDTO) {
        String name = goodsPageQueryDTO.getName();
        String olds = goodsPageQueryDTO.getOlds();
        Long categoryId = goodsPageQueryDTO.getCategoryId();
        Integer status = goodsPageQueryDTO.getStatus();
        //1.构建分页条件
        //1.1分页
        Page<Goods> page = Page.of(goodsPageQueryDTO.getPage(), goodsPageQueryDTO.getPageSize());
        //1.2排序
        if(StrUtil.isNotBlank(goodsPageQueryDTO.getSortBy())){
            //不为空
            page.addOrder(new OrderItem(goodsPageQueryDTO.getSortBy(),goodsPageQueryDTO.getIsAsc()));
        }else {
            //为空
            page.addOrder(new OrderItem("update_time",false));
        }
        //2.分页查询
        Page<Goods> p = lambdaQuery()
                .like(name != null, Goods::getName, name)
                .eq(olds != null, Goods::getOlds, olds)
                .eq(categoryId != null, Goods::getCategoryId, categoryId)
                .eq(status != null, Goods::getStatus, status)
                .eq(Goods::getStatus,1)
                .page(page);

        //3.1封装PageResult结果--总条数
        PageResult goodsPageResult = new PageResult();
        goodsPageResult.setTotal(p.getTotal());
        List<Goods> records = p.getRecords();
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
     * 根据id查询商品信息
     * @param id
     * @return
     */
    @Override
    public GoodsVO queryById(Long id) {
        //先查询该商品表信息
        Goods goods =getById(id);
        //查询商品对应的卖家信息
        User user = Db.lambdaQuery(User.class)
                .eq(User::getId,goods.getCreateUser())
                .one();
        College college = Db.lambdaQuery(College.class)
                .eq(College::getId,user.getCollegeId())
                .one();
        Dormitory dormitory = Db.lambdaQuery(Dormitory.class)
                .eq(Dormitory::getId,user.getDormitoryId())
                .one();


        //查询商品详细表有无该数据
        Long userId = BaseContext.getCurrentId();
        GoodsDetail goodsDetail = Db.lambdaQuery(GoodsDetail.class)
                .eq(GoodsDetail::getUserId,userId)
                .eq(GoodsDetail::getGoodsId,id)
                .one();
        if (goodsDetail ==null){
            //在商品详细表插入数据--浏览过了
            GoodsDetail newGoodsDetail = new GoodsDetail();
            newGoodsDetail.setName(goods.getName());
            newGoodsDetail.setUserId(userId);
            newGoodsDetail.setGoodsId(id);
            newGoodsDetail.setLook(1L);
            newGoodsDetail.setCreateUser(userId);
            goodsDetailService.save(newGoodsDetail);
            //利用算法，给Goods表中该商品增加人气值
            Long lookTotal = goods.getLookTotal()+1;//浏览+1
            Long popularity = goods.getPopularity()+1;//人气值+1
            lambdaUpdate()
                    .set(Goods::getLookTotal,lookTotal)
                    .set(Goods::getPopularity,popularity)
                    .eq(Goods::getId,id)
                    .update();
            //封装vo
            GoodsVO goodsVO = BeanUtil.copyProperties(goods,GoodsVO.class);
            goodsVO.setUserId(goods.getCreateUser());
            goodsVO.setCollection(newGoodsDetail.getCollection());
            goodsVO.setPraise(newGoodsDetail.getPraise());
            goodsVO.setComment(newGoodsDetail.getComment());
            goodsVO.setLook(newGoodsDetail.getLook());
            goodsVO.setCollegeName(college.getName());
            goodsVO.setDormitoryName(dormitory.getName());
            user.setName(user.getName().substring(0,user.getName().length() - 1) + "*");
            goodsVO.setUserName(user.getName());
            goodsVO.setPhone(user.getPhone());
            //返回vo
            return goodsVO;
        }else {
            //封装vo
            GoodsVO goodsVO = BeanUtil.copyProperties(goods,GoodsVO.class);
            goodsVO.setUserId(goods.getCreateUser());
            goodsVO.setCollection(goodsDetail.getCollection());
            goodsVO.setPraise(goodsDetail.getPraise());
            goodsVO.setComment(goodsDetail.getComment());
            goodsVO.setLook(goodsDetail.getLook());
            goodsVO.setCollegeName(college.getName());
            goodsVO.setDormitoryName(dormitory.getName());
            user.setName(user.getName().substring(0,user.getName().length() - 1) + "*");
            goodsVO.setUserName(user.getName());
            goodsVO.setPhone(user.getPhone());
            //返回vo
            return goodsVO;
        }
    }

    /**
     * 编辑商品信息
     * @param goodsDTO
     * @return
     */
    @Transactional
    @Override
    public void updateGoodsById(GoodsDTO goodsDTO) {
        Goods goods= BeanUtil.copyProperties(goodsDTO, Goods.class);
        //自动调用自动配置信息........
        updateById(goods);
        //删除对应的goods—Detail表
        List<GoodsDetail> goodsDetailList = Db.lambdaQuery(GoodsDetail.class)
                .eq(GoodsDetail::getGoodsId,goods.getId())
                .list();
        goodsDetailService.removeByIds(goodsDetailList);
    }

    /**
     * 根据id删除商品信息
     * @param id
     * @return
     */
    @Transactional
    @Override
    public void removeByGoodsId(Long id) {
        removeById(id);
        //删除对应的goods—Detail表
        List<GoodsDetail> goodsDetailList = Db.lambdaQuery(GoodsDetail.class)
                .eq(GoodsDetail::getGoodsId,id)
                .list();
        goodsDetailService.removeByIds(goodsDetailList);


    }

    /**
     * 根据id修改点赞信息
     * @param id
     * @return
     */
    @Override
    @Transactional
    public void updatePraise(Long praiseTemp, Long id) {
        if(praiseTemp == 1) {
            Goods goods = getById(id);
            goods.setPraiseTotal(goods.getPraiseTotal()+1);//总点赞加1
            goods.setPopularity(goods.getPopularity()+5);//人气值+5
            updateById(goods);

            //修改商品详细表
            List<GoodsDetail> goodsDetailList = Db.lambdaQuery(GoodsDetail.class)
                    .eq(GoodsDetail::getGoodsId,id)
                    .list();
            for (GoodsDetail goodsDetail : goodsDetailList) {
                goodsDetail.setPraise(1L);
                goodsDetailService.updateById(goodsDetail);
            }
        }

        if(praiseTemp == 0) {
            Goods goods = getById(id);
            goods.setPraiseTotal(goods.getPraiseTotal()-1);//总点赞-1
            goods.setPopularity(goods.getPopularity()-5);//人气值-5
            updateById(goods);

            //修改商品详细表
            List<GoodsDetail> goodsDetailList = Db.lambdaQuery(GoodsDetail.class)
                    .eq(GoodsDetail::getGoodsId,id)
                    .list();
            for (GoodsDetail goodsDetail : goodsDetailList) {
                goodsDetail.setPraise(0L);
                goodsDetailService.updateById(goodsDetail);
            }
        }
    }

    /**
     * 根据id修改收藏信息
     * @param id
     * @return
     */
    @Override
    @Transactional
    public void updateCollection(Long collectionTemp, Long id) {
        if(collectionTemp == 1) {
            Goods goods = getById(id);
            goods.setCollectionTotal(goods.getCollectionTotal()+1);//总点赞加1
            goods.setPopularity(goods.getPopularity()+10);//人气值+10
            updateById(goods);

            //修改商品详细表
            List<GoodsDetail> goodsDetailList = Db.lambdaQuery(GoodsDetail.class)
                    .eq(GoodsDetail::getGoodsId,id)
                    .list();
            for (GoodsDetail goodsDetail : goodsDetailList) {
                goodsDetail.setCollection(1L);
                goodsDetailService.updateById(goodsDetail);
            }
        }

        if(collectionTemp == 0) {
            Goods goods = getById(id);
            goods.setCollectionTotal(goods.getCollectionTotal()-1);//总点赞-1
            goods.setPopularity(goods.getPopularity()-10);//人气值-10
            updateById(goods);

            //修改商品详细表
            List<GoodsDetail> goodsDetailList = Db.lambdaQuery(GoodsDetail.class)
                    .eq(GoodsDetail::getGoodsId,id)
                    .list();
            for (GoodsDetail goodsDetail : goodsDetailList) {
                goodsDetail.setCollection(0L);
                goodsDetailService.updateById(goodsDetail);
            }
        }
    }

    /**
     * 查询收藏列表
     * @param goodsPageQueryDTO
     * @return
     */
    @Override
    public PageResult collectionPageQueryByuser(GoodsPageQueryDTO goodsPageQueryDTO) {
        //1.构建分页条件
        //1.1分页
        Page<GoodsDetail> page = Page.of(goodsPageQueryDTO.getPage(), goodsPageQueryDTO.getPageSize());
        //1.2排序
        if(StrUtil.isNotBlank(goodsPageQueryDTO.getSortBy())){
            //不为空
            page.addOrder(new OrderItem(goodsPageQueryDTO.getSortBy(),goodsPageQueryDTO.getIsAsc()));
        }else {
            //为空
            page.addOrder(new OrderItem("update_time",false));
        }
        //2.分页查询
        Page<GoodsDetail> p = Db.lambdaQuery(GoodsDetail.class)
                .eq(GoodsDetail::getUserId,BaseContext.getCurrentId())
                .eq(GoodsDetail::getCollection,1)
                .page(page);

        //3.1封装PageResult结果--总条数
        PageResult goodsPageResult = new PageResult();
        goodsPageResult.setTotal(p.getTotal());
        List<GoodsDetail> records = p.getRecords();
        if (CollUtil.isEmpty(records)) {
            goodsPageResult.setRecords(Collections.emptyList());//返回空集合
            return goodsPageResult;
        }
        //3.2封装PageResult结果--结果集合
        List<CollectionVO> collectionVOList = BeanUtil.copyToList(records, CollectionVO.class);
        for ( CollectionVO collectionVO : collectionVOList ){
            Goods goods = lambdaQuery()
                    .eq(Goods::getId,collectionVO.getGoodsId())
                    .one();
            Category category = Db.lambdaQuery(Category.class)
                    .eq(Category::getId,goods.getCategoryId())
                    .one();
            collectionVO.setCategoryId(category.getId());
            collectionVO.setCategoryName(category.getName());
            collectionVO.setPrice(goods.getPrice());
            collectionVO.setImage(goods.getImage());
            collectionVO.setOlds(goods.getOlds());
            collectionVO.setPopularity(goods.getPopularity());
        }
        goodsPageResult.setRecords(collectionVOList);
        //4.返回
        return goodsPageResult;
    }

    /**
     * 我的商品分页查询(用户查询)
     * @param goodsPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageMyQueryByuser(GoodsPageQueryDTO goodsPageQueryDTO) {
        String name = goodsPageQueryDTO.getName();
        String olds = goodsPageQueryDTO.getOlds();
        Long categoryId = goodsPageQueryDTO.getCategoryId();
        Integer status = goodsPageQueryDTO.getStatus();
        //1.构建分页条件
        //1.1分页
        Page<Goods> page = Page.of(goodsPageQueryDTO.getPage(), goodsPageQueryDTO.getPageSize());
        //1.2排序
        if(StrUtil.isNotBlank(goodsPageQueryDTO.getSortBy())){
            //不为空
            page.addOrder(new OrderItem(goodsPageQueryDTO.getSortBy(),goodsPageQueryDTO.getIsAsc()));
        }else {
            //为空
            page.addOrder(new OrderItem("update_time",false));
        }
        //2.分页查询
        Page<Goods> p = lambdaQuery()
                .like(name != null, Goods::getName, name)
                .eq(olds != null, Goods::getOlds, olds)
                .eq(categoryId != null, Goods::getCategoryId, categoryId)
                .eq(status != null, Goods::getStatus, status)
                .eq(Goods::getCreateUser,BaseContext.getCurrentId())
                .page(page);

        //3.1封装PageResult结果--总条数
        PageResult goodsPageResult = new PageResult();
        goodsPageResult.setTotal(p.getTotal());
        List<Goods> records = p.getRecords();
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
