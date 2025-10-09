package com.sky.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sky.dto.AddressPageQueryDTO;
import com.sky.entity.AddressBook;
import com.sky.entity.College;
import com.sky.entity.Dormitory;
import com.sky.entity.User;
import com.sky.mapper.AddressBookMapper;
import com.sky.result.PageResult;
import com.sky.service.AddressBookService;
import com.sky.service.CollegeService;
import com.sky.service.DormitoryService;
import com.sky.vo.AddressBookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 地址簿 服务实现类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-10
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
    @Autowired
    private CollegeService collegeService;
    @Autowired
    private DormitoryService dormitoryService;
    /**
     * 地址分页查询
     * @param addressPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(AddressPageQueryDTO addressPageQueryDTO) {
        String consignee = addressPageQueryDTO.getConsignee();
        Long collegeId = addressPageQueryDTO.getCollegeId();
        Long dormitoryId = addressPageQueryDTO.getDormitoryId();
        Integer isDefault = addressPageQueryDTO.getIsDefault();
        //1.构建分页条件
        //1.1分页
        Page<AddressBook> page = Page.of(addressPageQueryDTO.getPage(), addressPageQueryDTO.getPageSize());
        //1.2排序
        if(StrUtil.isNotBlank(addressPageQueryDTO.getSortBy())){
            //不为空
            page.addOrder(new OrderItem(addressPageQueryDTO.getSortBy(),addressPageQueryDTO.getIsAsc()));
        }else {
            //为空
            page.addOrder(new OrderItem("update_time",false));
        }
        //2.分页查询
        Page<AddressBook> p = lambdaQuery()
                .like(consignee != null, AddressBook::getConsignee, consignee)
                .eq(collegeId != null, AddressBook::getCollegeId, collegeId)
                .eq(dormitoryId != null, AddressBook::getDormitoryId, dormitoryId)
                .eq(isDefault != null, AddressBook::getIsDefault, isDefault)
                .page(page);

        //3.1封装PageResult结果--总条数
        PageResult addressPageResult = new PageResult();
        addressPageResult.setTotal(p.getTotal());
        List<AddressBook> records = p.getRecords();
        if (CollUtil.isEmpty(records)) {
            addressPageResult.setRecords(Collections.emptyList());//返回空集合
            return addressPageResult;
        }
        //3.2封装PageResult结果--结果集合
        List<AddressBookVO> addressBookVOList = BeanUtil.copyToList(records,AddressBookVO.class);
        for(AddressBookVO addressBookVO : addressBookVOList){
            College college = Db.lambdaQuery(College.class)
                    .eq(College::getId,addressBookVO.getCollegeId())
                    .one();
            Dormitory dormitory = Db.lambdaQuery(Dormitory.class)
                    .eq(Dormitory::getId,addressBookVO.getDormitoryId())
                    .one();
            User user = Db.lambdaQuery(User.class)
                    .eq(User::getId,addressBookVO.getUserId())
                    .one();
            String addressTogether = "河南城建学院"+"/"+college.getName()+"/"+dormitory.getName()+"/"+addressBookVO.getDetail();
            addressBookVO.setTogetherAddress(addressTogether);
            addressBookVO.setUserName(user.getName());
        }
        addressPageResult.setRecords(addressBookVOList);
        //4.返回
        return addressPageResult;
    }

    /**
     * 设置默认地址
     * @param isDefault
     * @param id
     * @return
     */
    @Override
    public void startOrStop(Integer isDefault, Long id) {
        AddressBook addressBook = getById(id);

        //如果设置默认地址，需要将该用户的其他地址都设为普通地址
        if(isDefault == 1){
            List<AddressBook> addressBookList = lambdaQuery()
                    .eq(AddressBook::getUserId,addressBook.getUserId())
                    .list();
            for(AddressBook addressBookTemp:addressBookList){
                addressBookTemp.setIsDefault(0);
                updateById(addressBookTemp);
            }
        }

        //将选中地址设为默认\普通
        addressBook.setIsDefault(isDefault);
        updateById(addressBook);
    }
}
