package com.sky.vo;

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
public class PostsVO implements Serializable {

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

    //0 取消 1 未解决 2 解决
    private Integer status;

    //人气值
    private Long popularity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updateTime;

    private String userAvatar;

    private Long createUser;

    private String userName;

}
