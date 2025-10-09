package com.sky.controller.user;


import cn.hutool.core.bean.BeanUtil;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.Orders;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrdersService;
import com.sky.vo.OrderSubmitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author 李萌
 * @since 2024-05-24
 */
@RestController("userOrderController")
@RequestMapping("/user/order")
@Slf4j
@Api(tags = "订单接口")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    /**
     * 用户下单
     * @param ordersSubmitDTO
     * @return
     */
    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        log.info("用户下单,详细信息：{}", ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = ordersService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * 订单支付---直接支付成功
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        String payment= ordersService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", ordersPaymentDTO);
        return Result.success(payment);
    }

    /**
     * 根据id查询订单
     *  id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询订单")
    public Result<OrderSubmitVO> getById(@PathVariable Long id){
        log.info("根据id查询订单：{}", id);
        Orders orders = ordersService.getById(id);
        OrderSubmitVO orderSubmitVO = BeanUtil.copyProperties(orders,OrderSubmitVO.class);
        orderSubmitVO.setOrderNumber(orders.getNumber());
        orderSubmitVO.setOrderAmount(orders.getAmount());
        return Result.success(orderSubmitVO);
    }
    /**
     * 卖家订单分页查询
     * @param ordersPageQueryDTO
     * @return
     */
    @GetMapping("/sell/page")
    @ApiOperation("订单分页查询")
    public Result<PageResult> sellConditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("订单分页查询,信息为：{}",ordersPageQueryDTO);
        PageResult pageResult = ordersService.sellConditionSearch(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 买家订单分页查询
     * @param ordersPageQueryDTO
     * @return
     */
    @GetMapping("/buy/page")
    @ApiOperation("订单分页查询")
    public Result<PageResult> buyConditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("订单分页查询,信息为：{}",ordersPageQueryDTO);
        PageResult pageResult = ordersService.buyConditionSearch(ordersPageQueryDTO);
        return Result.success(pageResult);
    }
}