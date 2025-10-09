package com.sky.service;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.result.PageResult;
import com.sky.vo.OrderSubmitVO;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author 李萌
 * @since 2024-06-10
 */
public interface OrdersService extends IService<Orders> {

    /**
     * 用户下单
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付---直接支付成功
     * @param ordersPaymentDTO
     * @return
     */
    String payment(OrdersPaymentDTO ordersPaymentDTO);

    /**
     * 订单分页查询
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 接单
     *
     * @return
     */
    void confirm(Integer status,Long id);

    /**
     * 拒单
     * @return
     */
    void rejection(String reason, Long id);

    /**
     * 退单
     * @return
     */
    void cancel(String reason, Long id);

    /**
     * 卖家订单分页查询
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult sellConditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 买家订单分页查询
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult buyConditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);
}
