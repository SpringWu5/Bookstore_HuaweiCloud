package com.sky.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 商品
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seek implements Serializable {

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

    //评论数
    private Long commentTotal;

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
