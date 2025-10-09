package com.sky.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsPageQueryDTO implements Serializable {

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("成色")
    private String olds;

    @ApiModelProperty("商品分类")
    private Long categoryId;

    @ApiModelProperty("商品状态")
    private Integer status;

    @ApiModelProperty("页码")
    private Integer page;

    @ApiModelProperty("页面大小")
    private Integer pageSize;

    @ApiModelProperty("排序字段")
    private String sortBy;

    @ApiModelProperty("是否降序")
    private Boolean isAsc=false;

}
