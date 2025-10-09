package com.sky.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 地址簿
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressBookVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //用户id
    private Long userId;

    //用户姓名
    private String userName;

    //收货人
    private String consignee;

    //手机号
    private String phone;

    //性别 0 女 1 男
    private String sex;

    //学院
    private Long collegeId;

    //宿舍
    private Long dormitoryId;

    //详细地址
    private String detail;

    //标签
    private String label;

    //是否默认 0否 1是
    private Integer isDefault;

    //拼接地址
    private String togetherAddress;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updateTime;



}
