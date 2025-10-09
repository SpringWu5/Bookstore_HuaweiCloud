package com.sky.service;

import com.sky.dto.AddressPageQueryDTO;
import com.sky.entity.AddressBook;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.result.PageResult;

/**
 * <p>
 * 地址簿 服务类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-10
 */
public interface AddressBookService extends IService<AddressBook> {

    /**
     * 地址分页查询
     * @param addressPageQueryDTO
     * @return
     */
    PageResult pageQuery(AddressPageQueryDTO addressPageQueryDTO);

    /**
     * 设置默认地址
     * @param isDefault
     * @param id
     * @return
     */
    void startOrStop(Integer isDefault, Long id);
}
