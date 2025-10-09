package com.sky.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.sky.context.BaseContext;
import com.sky.dto.GoodsDTO;
import com.sky.dto.GoodsPageQueryDTO;
import com.sky.entity.Goods;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品 前端控制器
 * </p>
 *
 * @author 李萌
 * @since 2024-06-03
 */
@Api(tags = "商品相关接口")
@RestController("adminGoodsController")
@RequestMapping("/admin/goods")
@Slf4j
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    /**
     * 新增商品
     * @param goodsDTO
     * @return
     */
    @ApiOperation("新增商品")
    @PostMapping
    public Result save(@RequestBody GoodsDTO goodsDTO){
        log.info("新增商品：{}",goodsDTO);
        Goods goods = BeanUtil.copyProperties(goodsDTO, Goods.class);
        goods.setCreateUser(BaseContext.getCurrentId());//创建人
        //自动调用自动配置信息......
        goodsService.save(goods);
        return Result.success();
    }

    /**
     * 商品分页查询
     * @param goodsPageQueryDTO
     * @return
     */
    @ApiOperation("商品分页查询")
    @GetMapping("/page")
    public Result<PageResult> queryGoodsPage(GoodsPageQueryDTO goodsPageQueryDTO){
        log.info("商品分页查询，参数为：{}",goodsPageQueryDTO);
        return Result.success(goodsService.pageQuery(goodsPageQueryDTO));
    }

    /**
     * 启动禁用商品
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status")
    @ApiOperation("启动禁用商品")
    public Result startOrStop(Integer status,Long id){
        log.info("启动禁用商品：{},{}",status,id);
        goodsService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * 根据id查询商品信息
     * @param id
     * @return
     */
    @ApiOperation("根据id查询商品信息")
    @GetMapping("/{id}")
    public Result<Goods> getById(@PathVariable Long id){
        log.info("根据id查询商品信息，参数为：{}",id);
        Goods goods = goodsService.getById(id);
        return Result.success(goods);
    }

    /**
     * 编辑商品信息
     * @param goodsDTO
     * @return
     */
    @ApiOperation("编辑商品信息")
    @PutMapping
    public Result update(@RequestBody GoodsDTO goodsDTO){
        log.info("编辑商品信息：{}",goodsDTO);
        goodsService.updateGoodsById(goodsDTO);
        return Result.success();
    }

    /**
     * 根据id删除商品信息
     * @param id
     * @return
     */
    @ApiOperation("根据id删除商品信息")
    @DeleteMapping("/{id}")
    public Result<Goods> deleteById(@PathVariable Long id){
        log.info("根据id删除商品信息，参数为：{}",id);
        goodsService.removeByGoodsId(id);
        return Result.success();
    }
}
