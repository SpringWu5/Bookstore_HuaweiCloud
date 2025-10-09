package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //商品id
    private Long goodsId;

    //商品名称
    private String name;

    //商品分类id
    private Long categoryId;

    //商品分类id
    private String categoryName;

    //商品价格
    private BigDecimal price;

    //图片
    private String image;

    //成色
    private String olds;

    //人气值
    private Long popularity;

    //收藏
    private Long collection;

}
