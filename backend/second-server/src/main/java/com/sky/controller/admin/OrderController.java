package com.sky.controller.admin;


import com.sky.dto.OrdersPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单管理
 */
@RestController("adminOrderController")
@RequestMapping("/admin/order")
@Slf4j
@Api(tags = "订单管理接口")
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    /**
     * 订单分页查询
     * @param ordersPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("订单分页查询")
    public Result<PageResult> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("订单分页查询,信息为：{}",ordersPageQueryDTO);
        PageResult pageResult = ordersService.conditionSearch(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 接单
     * @return
     */
    @PostMapping("/confirm")
    @ApiOperation("接单")
    public Result confirm(Integer status,Long id) {
        ordersService.confirm(status,id);
        return Result.success();
    }

    /**
     * 拒单
     * @return
     */
    @PostMapping("/rejection")
    @ApiOperation("拒单")
    public Result rejection(String reason,Long id) {
        ordersService.rejection(reason,id);
        return Result.success();
    }

    /**
     * 退单
     * @return
     */
    @PostMapping("/cancel")
    @ApiOperation("退单")
    public Result cancel(String reason,Long id) {
        ordersService.cancel(reason,id);
        return Result.success();
    }

}