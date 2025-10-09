package com.sky.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 物品分类 服务实现类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-03
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    /**
     * 启动禁用分类
     * @param status
     * @param id
     * @return
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Category category = getById(id);
        category.setStatus(status);
        updateById(category);
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    //TODO 未完成
    @Override
    public void deleteByCollegeId(Long id) {
        //判断当前分类下有无商品
//        List<Goods> goodsList = Db.lambdaQuery(Goods.class)
//                .eq(Goods::getCategoryId,id)
//                .list();
//        if(CollUtil.isNotEmpty(goodsList)){
//            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_GOODS);
//        }
        //删除分类
        removeById(id);
    }
}
