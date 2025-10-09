package com.sky.vo;

import lombok.Data;

@Data
public class ValuationResultVO {
    private String price;      // 估价价格
    private String condition;  // 商品成色
    private String category;   // 商品类别
} 