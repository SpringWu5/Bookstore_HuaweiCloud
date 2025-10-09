package com.sky.controller.user;


import com.sky.dto.GoodsPageQueryDTO;
import com.sky.entity.Goods;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.GoodsService;
import com.sky.vo.GoodsVO;
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
@RestController("userGoodsController")
@RequestMapping("/user/goods")
@Slf4j
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 商品分页查询(用户查询)
     * @param goodsPageQueryDTO
     * @return
     */
    @ApiOperation("商品分页查询")
    @GetMapping("/page")
    public Result<PageResult> queryGoodsPage(GoodsPageQueryDTO goodsPageQueryDTO){
        log.info("商品分页查询，参数为：{}",goodsPageQueryDTO);
        return Result.success(goodsService.pageQueryByuser(goodsPageQueryDTO));
    }


    /**
     * 根据id查询商品信息
     * @param id
     * @return
     */
    @ApiOperation("根据id查询商品信息")
    @GetMapping("/{id}")
    public Result<GoodsVO> getById(@PathVariable Long id){
        log.info("根据id查询商品信息，参数为：{}",id);
        GoodsVO goodsVO = goodsService.queryById(id);
        return Result.success(goodsVO);
    }


    /**
     * 根据id修改点赞信息
     * @param id
     * @return
     */
    @ApiOperation("根据id修改点赞信息")
    @PostMapping("/praise")
    public Result updatePraise(Long praiseTemp,Long id){
        log.info("根据id修改点赞信息，参数为：{}，{}",id,praiseTemp);
        goodsService.updatePraise(praiseTemp,id);
        return Result.success();
    }

    /**
     * 根据id修改收藏信息
     * @param id
     * @return
     */
    @ApiOperation("根据id修改收藏信息")
    @PostMapping("/collection")
    public Result<Goods> updateCollection(Long collectionTemp,Long id){
        log.info("根据id修改收藏信息，参数为：{}，{}",id, collectionTemp);
        goodsService.updateCollection(collectionTemp,id);
        return Result.success();
    }

    /**
     * 收藏商品分页查询(用户查询)
     * @param goodsPageQueryDTO
     * @return
     */
    @ApiOperation("收藏商品分页查询")
    @GetMapping("/collection/page")
    public Result<PageResult> collectionQueryGoodsPage(GoodsPageQueryDTO goodsPageQueryDTO){
        log.info("商品分页查询，参数为：{}",goodsPageQueryDTO);
        return Result.success(goodsService.collectionPageQueryByuser(goodsPageQueryDTO));
    }

    /**
     * 我的商品分页查询(用户查询)
     * @param goodsPageQueryDTO
     * @return
     */
    @ApiOperation("商品分页查询")
    @GetMapping("/myGoods/page")
    public Result<PageResult> queryMyGoodsPage(GoodsPageQueryDTO goodsPageQueryDTO){
        log.info("商品分页查询，参数为：{}",goodsPageQueryDTO);
        return Result.success(goodsService.pageMyQueryByuser(goodsPageQueryDTO));
    }


}
