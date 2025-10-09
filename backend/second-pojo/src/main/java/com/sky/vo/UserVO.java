package com.sky.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "获取当前登录用户信息返回的数据格式")
public class UserVO implements Serializable {
    @ApiModelProperty("用户Id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("学院Id")
    private Long collegeId;

    @ApiModelProperty("宿舍Id")
    private Long dormitoryId;

    @ApiModelProperty("学院名称")
    private String collegeName;

    @ApiModelProperty("宿舍名称")
    private String dormitoryName;

}