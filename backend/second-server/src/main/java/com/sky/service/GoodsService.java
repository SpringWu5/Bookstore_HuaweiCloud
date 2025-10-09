package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.dto.GoodsDTO;
import com.sky.dto.GoodsPageQueryDTO;
import com.sky.entity.Goods;
import com.sky.result.PageResult;
import com.sky.vo.GoodsVO;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-03
 */
public interface GoodsService extends IService<Goods> {

    /**
     * 商品分页查询
     * @param goodsPageQueryDTO
     * @return
     */
    PageResult pageQuery(GoodsPageQueryDTO goodsPageQueryDTO);

    /**
     * 启动禁用商品
     * @param status
     * @param id
     * @return
     */
    void startOrStop(Integer status, Long id);

    /**
     * 商品分页查询(用户查询)
     * @param goodsPageQueryDTO
     * @return
     */
    PageResult pageQueryByuser(GoodsPageQueryDTO goodsPageQueryDTO);

    /**
     * 根据id查询商品信息
     * @param id
     * @return
     */
    GoodsVO queryById(Long id);

    /**
     * 根据id修改点赞信息
     * @param id
     * @return
     */
    void updatePraise(Long praiseTemp, Long id);

    /**
     * 编辑商品信息
     * @param goodsDTO
     * @return
     */
    void updateGoodsById(GoodsDTO goodsDTO);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    void removeByGoodsId(Long id);

    /**
     * 根据id修改收藏信息
     * @param id
     * @return
     */
    void updateCollection(Long collectionTemp, Long id);

    /**
     * 收藏商品分页查询(用户查询)
     * @param goodsPageQueryDTO
     * @return
     */
    PageResult collectionPageQueryByuser(GoodsPageQueryDTO goodsPageQueryDTO);

    /**
     * 我的商品分页查询(用户查询)
     * @param goodsPageQueryDTO
     * @return
     */
    PageResult pageMyQueryByuser(GoodsPageQueryDTO goodsPageQueryDTO);
}
