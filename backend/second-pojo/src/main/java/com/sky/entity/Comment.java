package com.sky.entity;

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
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //评论内容
    private String content;

    //用户id
    private Long userId;

    //用户名字
    private String userName;

    //用户头像
    private String avatar;

    //作用模块id
    private Long foreignId;

    //作用模块名字
    private String foreignName;

    //父评论用户id
    private Long pid;

    //回复对象
    private String target;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createTime;

    private Long createUser;

}
