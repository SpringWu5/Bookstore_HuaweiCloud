package com.sky.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.sky.dto.AddressBookDTO;
import com.sky.dto.AddressPageQueryDTO;
import com.sky.entity.AddressBook;
import com.sky.entity.User;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 地址簿 前端控制器
 * </p>
 *
 * @author 李萌
 * @since 2024-06-10
 */
@Api(tags = "地址相关接口")
@RestController("adminAddressController")
@RequestMapping("/admin/address")
@Slf4j
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    /**
     * 地址分页查询
     * @param addressPageQueryDTO
     * @return
     */
    @ApiOperation("地址分页查询")
    @GetMapping("/page")
    public Result<PageResult> queryUserPage(AddressPageQueryDTO addressPageQueryDTO){
        log.info("地址分页查询，参数为：{}",addressPageQueryDTO);
        return Result.success(addressBookService.pageQuery(addressPageQueryDTO));
    }

    /**
     * 设置默认地址
     * @param isDefault
     * @param id
     * @return
     */
    @PostMapping("/isDefault")
    @ApiOperation("设置默认地址")
    public Result startOrStop(Integer isDefault,Long id){
        log.info("设置默认地址：{},{}",isDefault,id);
        addressBookService.startOrStop(isDefault,id);
        return Result.success();
    }

    /**
     * 编辑地址信息
     * @param addressBookDTO
     * @return
     */
    @ApiOperation("编辑地址信息")
    @PutMapping
    public Result update(@RequestBody AddressBookDTO addressBookDTO){
        log.info("编辑地址信息：{}",addressBookDTO);
        AddressBook addressBook = BeanUtil.copyProperties(addressBookDTO, AddressBook.class);
        //自动调用自动配置信息........
        addressBookService.updateById(addressBook);
        return Result.success();
    }

    /**
     * 根据id删除用户信息
     * @param id
     * @return
     */
    @ApiOperation("根据id删除用户信息")
    @DeleteMapping("/{id}")
    public Result<User> deleteById(@PathVariable Long id){
        log.info("根据id删除用户信息，参数为：{}",id);
        addressBookService.removeById(id);
        return Result.success();
    }
}
