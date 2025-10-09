package com.sky.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.*;
import com.sky.exception.AddressBookBusinessException;
import com.sky.mapper.OrdersMapper;
import com.sky.result.PageResult;
import com.sky.service.OrderDetailService;
import com.sky.service.OrdersService;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrdersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-10
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private OrderDetailService orderDetailService;
    /**
     * 用户下单
     * @param ordersSubmitDTO
     * @return
     */
    @Override
    @Transactional
    public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {

        //1.处理业务异常（地址簿为空）
        AddressBook addressBook = Db.lambdaQuery(AddressBook.class)
                .eq(AddressBook::getId,ordersSubmitDTO.getAddressBookId())
                .one();
        if(addressBook == null){
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        //2.向订单表插入一条数据
        College college = Db.lambdaQuery(College.class)
                .eq(College::getId,addressBook.getCollegeId())
                .one();
        Dormitory dormitory = Db.lambdaQuery(Dormitory.class)
                .eq(Dormitory::getId,addressBook.getDormitoryId())
                .one();

        Orders orders = BeanUtil.copyProperties(ordersSubmitDTO,Orders.class);
        orders.setBuyId(BaseContext.getCurrentId());
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayStatus(Orders.UN_PAID);
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setNumber(String.valueOf(System.currentTimeMillis()));//使用时间戳作为订单号
        orders.setPhone(addressBook.getPhone());
        orders.setConsignee(addressBook.getConsignee());
        orders.setBuyId(BaseContext.getCurrentId());
        orders.setAddress("河南城建学院"+"/"+college.getName()+"/"+dormitory.getName()+"/"+addressBook.getDetail());
        save(orders);
        //3.向订单明细表插入一条数据
        User user =Db.lambdaQuery(User.class)
                .eq(User::getId,BaseContext.getCurrentId())
                .one();
        Goods goods = Db.lambdaQuery(Goods.class)
                .eq(Goods::getId,ordersSubmitDTO.getGoodsId())
                .one();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setName(user.getName());
        orderDetail.setOrderId(orders.getId());
        orderDetail.setGoodsId(ordersSubmitDTO.getGoodsId());
        orderDetail.setAmount(ordersSubmitDTO.getAmount());
        orderDetail.setImage(goods.getImage());

        orderDetailService.save(orderDetail);
        //5.封装VO返回结果
        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .id(orders.getId())
                .orderNumber(orders.getNumber())
                .orderAmount(orders.getAmount())
                .orderTime(orders.getOrderTime())
                .build();
        return orderSubmitVO;
    }

    /**
     * 订单支付---直接支付成功
     * @param ordersPaymentDTO
     * @return
     */
    public String payment(OrdersPaymentDTO ordersPaymentDTO) {
        // 根据订单号查询订单
        Orders order = Db.lambdaQuery(Orders.class)
                .eq(Orders::getNumber, ordersPaymentDTO.getOrderNumber())
                .one();
        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Orders orders = Orders.builder()
                .id(order.getId())
                .status(Orders.TO_BE_CONFIRMED)
                .payStatus(Orders.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();
        updateById(orders);

        return "支付成功";
    }

    /**
     * 订单分页查询
     * @param ordersPageQueryDTO
     * @return
     */
    @Override
    public PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        //1.1分页
        Page<Orders> Ipage = Page.of(ordersPageQueryDTO.getPage(),ordersPageQueryDTO.getPageSize());
        //1.2排序
        Ipage.addOrder(new OrderItem("order_time",false));

        //2.分页查询
        Page<Orders> p = lambdaQuery()
                .like(ordersPageQueryDTO.getNumber() !=null,Orders::getNumber,ordersPageQueryDTO.getNumber())
                .like(ordersPageQueryDTO.getPhone() !=null,Orders::getPhone,ordersPageQueryDTO.getPhone())
                .eq(ordersPageQueryDTO.getStatus() != null, Orders::getStatus, ordersPageQueryDTO.getStatus())
                .eq(ordersPageQueryDTO.getUserId() != null, Orders::getBuyId,ordersPageQueryDTO.getUserId())
                .ge(ordersPageQueryDTO.getBeginTime() != null, Orders::getOrderTime,ordersPageQueryDTO.getBeginTime())
                .le(ordersPageQueryDTO.getEndTime() != null, Orders::getOrderTime,ordersPageQueryDTO.getEndTime())
                .ne(Orders::getStatus,1)
                .page(Ipage);

        //3.1封装PageResult结果--总条数
        PageResult ordersPageResult = new PageResult();
        ordersPageResult.setTotal(p.getTotal());
        List<Orders> records = p.getRecords();
        if (CollUtil.isEmpty(records)) {
            ordersPageResult.setRecords(Collections.emptyList());//返回空集合
            return ordersPageResult;
        }
        //3.2封装PageResult结果--结果集合
        List<OrdersVO> ordersVOList = BeanUtil.copyToList(records,OrdersVO.class);
        for(OrdersVO ordersVO : ordersVOList){
            User buyUser = Db.lambdaQuery(User.class)
                    .eq(User::getId,ordersVO.getBuyId())
                    .one();
            User sellUser = Db.lambdaQuery(User.class)
                    .eq(User::getId,ordersVO.getSellId())
                    .one();
            ordersVO.setBuyName(buyUser.getName());
            ordersVO.setSellName(sellUser.getName());
        }
        ordersPageResult.setRecords(ordersVOList);
        //4.返回
        return ordersPageResult;
    }

    /**
     * 接单
     * Integer status,
     * Long id
     */
    public void confirm(Integer status,Long id) {
        Orders orders = Orders.builder()
                .id(id)
                .status(status)
                .build();
        if(status == 5){
            orders.setEndDeliveryTime(LocalDateTime.now());
        }
        updateById(orders);
    }

    /**
     * 拒单
     * @return
     */
    @Override
    public void rejection(String reason, Long id) {
        Orders orders = Orders.builder()
                .id(id)
                .status(7)
                .rejectionReason(reason)
                .cancelTime(LocalDateTime.now())
                .build();
        updateById(orders);
    }

    /**
     * 退单
     * @return
     */
    @Override
    public void cancel(String reason, Long id) {
        Orders orders = Orders.builder()
                .id(id)
                .status(7)
                .cancelReason(reason)
                .cancelTime(LocalDateTime.now())
                .build();
        Orders ordersTemp = getById(id);
        if(ordersTemp.getStatus()== 1){
            orders.setStatus(6);
        }
        updateById(orders);
    }

    /**
     * 卖家订单分页查询
     * @param ordersPageQueryDTO
     * @return
     */
    @Override
    public PageResult sellConditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        //1.1分页
        Page<Orders> Ipage = Page.of(ordersPageQueryDTO.getPage(),ordersPageQueryDTO.getPageSize());
        //1.2排序
        Ipage.addOrder(new OrderItem("order_time",false));

        //2.分页查询
        Page<Orders> p = lambdaQuery()
                .like(ordersPageQueryDTO.getNumber() !=null,Orders::getNumber,ordersPageQueryDTO.getNumber())
                .like(ordersPageQueryDTO.getPhone() !=null,Orders::getPhone,ordersPageQueryDTO.getPhone())
                .eq(ordersPageQueryDTO.getStatus() != null, Orders::getStatus, ordersPageQueryDTO.getStatus())
                .eq(ordersPageQueryDTO.getUserId() != null, Orders::getBuyId,ordersPageQueryDTO.getUserId())
                .ge(ordersPageQueryDTO.getBeginTime() != null, Orders::getOrderTime,ordersPageQueryDTO.getBeginTime())
                .le(ordersPageQueryDTO.getEndTime() != null, Orders::getOrderTime,ordersPageQueryDTO.getEndTime())
                .eq(Orders::getSellId,BaseContext.getCurrentId())
                .ne(Orders::getStatus,1)
                .ne(Orders::getStatus,6)
                .page(Ipage);

        //3.1封装PageResult结果--总条数
        PageResult ordersPageResult = new PageResult();
        ordersPageResult.setTotal(p.getTotal());
        List<Orders> records = p.getRecords();
        if (CollUtil.isEmpty(records)) {
            ordersPageResult.setRecords(Collections.emptyList());//返回空集合
            return ordersPageResult;
        }
        //3.2封装PageResult结果--结果集合
        List<OrdersVO> ordersVOList = BeanUtil.copyToList(records,OrdersVO.class);
        for(OrdersVO ordersVO : ordersVOList){
            User buyUser = Db.lambdaQuery(User.class)
                    .eq(User::getId,ordersVO.getBuyId())
                    .one();
            User sellUser = Db.lambdaQuery(User.class)
                    .eq(User::getId,ordersVO.getSellId())
                    .one();
            ordersVO.setBuyName(buyUser.getName());
            ordersVO.setSellName(sellUser.getName());
        }
        ordersPageResult.setRecords(ordersVOList);
        //4.返回
        return ordersPageResult;
    }

    /**
     * 买家订单分页查询
     * @param ordersPageQueryDTO
     * @return
     */
    @Override
    public PageResult buyConditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        //1.1分页
        Page<Orders> Ipage = Page.of(ordersPageQueryDTO.getPage(),ordersPageQueryDTO.getPageSize());
        //1.2排序
        Ipage.addOrder(new OrderItem("order_time",false));

        //2.分页查询
        Page<Orders> p = lambdaQuery()
                .like(ordersPageQueryDTO.getNumber() !=null,Orders::getNumber,ordersPageQueryDTO.getNumber())
                .like(ordersPageQueryDTO.getPhone() !=null,Orders::getPhone,ordersPageQueryDTO.getPhone())
                .eq(ordersPageQueryDTO.getStatus() != null, Orders::getStatus, ordersPageQueryDTO.getStatus())
                .eq(ordersPageQueryDTO.getUserId() != null, Orders::getBuyId,ordersPageQueryDTO.getUserId())
                .ge(ordersPageQueryDTO.getBeginTime() != null, Orders::getOrderTime,ordersPageQueryDTO.getBeginTime())
                .le(ordersPageQueryDTO.getEndTime() != null, Orders::getOrderTime,ordersPageQueryDTO.getEndTime())
                .eq(Orders::getBuyId,BaseContext.getCurrentId())
                .page(Ipage);

        //3.1封装PageResult结果--总条数
        PageResult ordersPageResult = new PageResult();
        ordersPageResult.setTotal(p.getTotal());
        List<Orders> records = p.getRecords();
        if (CollUtil.isEmpty(records)) {
            ordersPageResult.setRecords(Collections.emptyList());//返回空集合
            return ordersPageResult;
        }
        //3.2封装PageResult结果--结果集合
        List<OrdersVO> ordersVOList = BeanUtil.copyToList(records,OrdersVO.class);
        for(OrdersVO ordersVO : ordersVOList){
            User buyUser = Db.lambdaQuery(User.class)
                    .eq(User::getId,ordersVO.getBuyId())
                    .one();
            User sellUser = Db.lambdaQuery(User.class)
                    .eq(User::getId,ordersVO.getSellId())
                    .one();
            ordersVO.setBuyName(buyUser.getName());
            ordersVO.setSellName(sellUser.getName());
        }
        ordersPageResult.setRecords(ordersVOList);
        //4.返回
        return ordersPageResult;
    }
}
