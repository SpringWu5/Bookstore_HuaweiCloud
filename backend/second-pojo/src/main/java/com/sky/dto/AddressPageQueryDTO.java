package com.sky.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddressPageQueryDTO implements Serializable {

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("学院id")
    private Long collegeId;

    @ApiModelProperty("宿舍id")
    private Long dormitoryId;

    @ApiModelProperty("默认状态")
    private Integer isDefault;

    @ApiModelProperty("页码")
    private Integer page;

    @ApiModelProperty("页面大小")
    private Integer pageSize;

    @ApiModelProperty("排序字段")
    private String sortBy;

    @ApiModelProperty("是否升序")
    private Boolean isAsc=true;

}
