package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@ApiModel(description = "用户登录时传递的数据模型")
public class UserLoginDTO implements Serializable {
    @NotEmpty
    @Pattern(regexp = "^\\S{5,16}$")
    @ApiModelProperty("用户名")
    private String username;

    @NotEmpty
    @Pattern(regexp = "^\\S{5,16}$")
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("确认密码")
    private String RePassword;

}
