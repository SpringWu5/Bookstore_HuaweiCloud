package com.sky.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //商品名称
    private String name;

    //商品分类id
    private Long categoryId;

    //图片
    private String image;

    //描述信息
    private String description;

    //0 停售 1 起售 2 强制下架
    private Integer status;

    //人气值
    private Long popularity;
}
