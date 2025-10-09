package com.sky.controller.user;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sky.context.BaseContext;
import com.sky.dto.AddressBookDTO;
import com.sky.entity.AddressBook;
import com.sky.entity.College;
import com.sky.entity.Dormitory;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import com.sky.vo.AddressBookVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 地址簿 前端控制器
 * </p>
 *
 * @author 李萌
 * @since 2024-06-10
 */
@Api(tags = "地址相关接口")
@RestController("userAddressController")
@RequestMapping("/user/address")
@Slf4j
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增地址
     * @param addressBookDTO
     * @return
     */
    @ApiOperation("新增地址")
    @PostMapping
    public Result save(@RequestBody AddressBookDTO addressBookDTO){
        log.info("新增地址：{}",addressBookDTO);
        AddressBook addressBook = BeanUtil.copyProperties(addressBookDTO,AddressBook.class);
        addressBook.setCreateUser(BaseContext.getCurrentId());//创建人
        addressBook.setUserId(BaseContext.getCurrentId());//用户Id
        //自动调用自动配置信息......
        addressBookService.save(addressBook);
        return Result.success();
    }

    /**
     * 地址查询------用户端
     * @return
     */
    @ApiOperation("地址查询")
    @GetMapping
    public Result<List<AddressBookVO>> queryAddress(){
        log.info("地址查询");
        List<AddressBook> addressBookList = Db.lambdaQuery(AddressBook.class)
                .eq(AddressBook::getUserId,BaseContext.getCurrentId())
                .list();
        List<AddressBookVO> addressBookVOList = BeanUtil.copyToList(addressBookList,AddressBookVO.class);
        for(AddressBookVO addressBookVO : addressBookVOList){
            College college = Db.lambdaQuery(College.class)
                    .eq(College::getId,addressBookVO.getCollegeId())
                    .one();
            Dormitory dormitory = Db.lambdaQuery(Dormitory.class)
                    .eq(Dormitory::getId,addressBookVO.getDormitoryId())
                    .one();
            String addressTogether = "河南城建学院"+"/"+college.getName()+"/"+dormitory.getName()+"/"+addressBookVO.getDetail();
            addressBookVO.setTogetherAddress(addressTogether);
        }
        return Result.success(addressBookVOList);
    }

    /**
     * 查询全部地址
     * @return
     */
    @ApiOperation("查询全部地址")
    @GetMapping("/isDefault")
    public Result<List<AddressBookVO>> getAddressByIsDefault(){
        log.info("查询全部地址");
        List<AddressBook> addressBookList = Db.lambdaQuery(AddressBook.class)
                .eq(AddressBook::getUserId,BaseContext.getCurrentId())
                .list();
        List<AddressBookVO> addressBookVOList = BeanUtil.copyToList(addressBookList,AddressBookVO.class);
        for(AddressBookVO addressBookVO : addressBookVOList){
            College college = Db.lambdaQuery(College.class)
                    .eq(College::getId,addressBookVO.getCollegeId())
                    .one();
            Dormitory dormitory = Db.lambdaQuery(Dormitory.class)
                    .eq(Dormitory::getId,addressBookVO.getDormitoryId())
                    .one();
            String addressTogether = "河南城建学院"+"/"+college.getName()+"/"+dormitory.getName()+"/"+addressBookVO.getDetail();
            addressBookVO.setTogetherAddress(addressTogether);
        }
        return Result.success(addressBookVOList);
    }

}
