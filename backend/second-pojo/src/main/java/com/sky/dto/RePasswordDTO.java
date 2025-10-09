package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@ApiModel(description = "用户登录时传递的数据模型")
public class RePasswordDTO implements Serializable {
    @NotEmpty
    @Pattern(regexp = "^\\S{5,16}$")
    @ApiModelProperty("旧密码")
    private String oldPwd;

    @NotEmpty
    @Pattern(regexp = "^\\S{5,16}$")
    @ApiModelProperty("新密码")
    private String newPwd;

    @NotEmpty
    @Pattern(regexp = "^\\S{5,16}$")
    @ApiModelProperty("确认密码")
    private String rePwd;

}
