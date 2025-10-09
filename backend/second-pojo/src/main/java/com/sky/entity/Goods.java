package com.sky.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class Goods implements Serializable {

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

    //人气值
    private Long popularity;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

}
