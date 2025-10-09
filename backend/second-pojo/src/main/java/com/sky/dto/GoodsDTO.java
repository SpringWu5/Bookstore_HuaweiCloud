package com.sky.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GoodsDTO implements Serializable {

    private Long id;

    //商品名称
    private String name;

    //商品分类id
    private Long categoryId;

    //商品价格
    private BigDecimal price;

    //图片
    private String image;

    //成色
    private String olds;

    //描述信息
    private String description;

    //人气值(管理员可以修改人气值，用户交钱就可以提升)
    private Long popularity;

    private Long collectionTotal;

    private Long praiseTotal;

    private Long collectionTemp;

}
