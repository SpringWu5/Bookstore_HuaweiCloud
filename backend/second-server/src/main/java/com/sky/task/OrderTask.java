package com.sky.task;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sky.entity.Orders;
import com.sky.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 自定义定时任务
 */
@Component
@Slf4j
public class OrderTask {
    @Autowired
    private OrdersService ordersService;
    /**
     * 定时任务，每隔1分钟，触发一次
     * 处理超时订单方法
     */
    @Scheduled(cron = "0 * * * * ?")//秒 分 时 日 月 周（每分钟执行一次）
    public void processTimeoutOrder(){
        log.info("定时处理超时订单：{}", LocalDateTime.now());

        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);//当前时间-15min
        List<Orders> ordersList = Db.lambdaQuery(Orders.class)
                .eq(Orders::getStatus,Orders.PENDING_PAYMENT)
                .lt(Orders::getOrderTime,time)
                .list();
        if(ordersList != null && ordersList.size() > 0){
            for (Orders orders : ordersList){
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelReason("订单超时，自动取消");
                orders.setCancelTime(LocalDateTime.now());
                ordersService.updateById(orders);
            }
        }
    }

    /**
     * 每天凌晨1点自动执行
     * 处理一直处于派送中的订单
     */
    @Scheduled(cron = "0 0 1 * *  ?")//每天凌晨1点自动执行
    public void processDeliveryOrder(){
        log.info("定时处理一直处于派送中的订单：{}", LocalDateTime.now());

        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);//当前时间-15min
        List<Orders> ordersList = Db.lambdaQuery(Orders.class)
                .eq(Orders::getStatus,Orders.DELIVERY_IN_PROGRESS)
                .lt(Orders::getOrderTime,time)
                .list();
        if(ordersList != null && ordersList.size() > 0){
            for (Orders orders : ordersList){
                orders.setStatus(Orders.COMPLETED);
                ordersService.updateById(orders);
            }
        }
    }
}
