package com.sky.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVO implements Serializable {

    private static final long serialVersionUID = 1L;

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

    //0 停售 1 起售 2 强制下架
    private Integer status;
    //收藏数
    private Long collectionTotal;

    //点赞数
    private Long praiseTotal;

    //评论数
    private Long commentTotal;

    //浏览数
    private Long lookTotal;

    //用户Id
    private Long userId;

    //收藏数
    private Long collection;

    //点赞数
    private Long praise;

    //评论数
    private Long comment;

    //浏览数
    private Long look;

    //人气值
    private Long popularity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updateTime;

    //学院名字
    private String collegeName;

    //宿舍楼
    private String dormitoryName;

    //卖家姓名
    private String userName;

    //卖家电话
    private String phone;

}
