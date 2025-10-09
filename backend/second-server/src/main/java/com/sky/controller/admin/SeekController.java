package com.sky.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import com.sky.context.BaseContext;
import com.sky.dto.SeekDTO;
import com.sky.dto.SeekPageQueryDTO;
import com.sky.entity.Seek;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SeekService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 求购表 前端控制器
 * </p>
 *
 * @author 李萌
 * @since 2024-06-06
 */
@Api(tags = "求购物品相关接口")
@RestController("adminSeekController")
@RequestMapping("/admin/seek")
@Slf4j
public class SeekController {
    @Autowired
    private SeekService seekService;

    /**
     * 新增求购
     * @param seekDTO
     * @return
     */
    @ApiOperation("新增求购")
    @PostMapping
    public Result save(@RequestBody SeekDTO seekDTO){
        log.info("新增求购：{}",seekDTO);
        Seek seek = BeanUtil.copyProperties(seekDTO, Seek.class);
        seek.setCreateUser(BaseContext.getCurrentId());//创建人
        //自动调用自动配置信息......
        seekService.save(seek);
        return Result.success();
    }

    /**
     * 求购分页查询
     * @param seekPageQueryDTO
     * @return
     */
    @ApiOperation("求购分页查询")
    @GetMapping("/page")
    public Result<PageResult> queryGoodsPage(SeekPageQueryDTO seekPageQueryDTO){
        log.info("求购分页查询，参数为：{}",seekPageQueryDTO);
        return Result.success(seekService.pageQuery(seekPageQueryDTO));
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
        seekService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * 根据id查询商品信息
     * @param id
     * @return
     */
    @ApiOperation("根据id查询商品信息")
    @GetMapping("/{id}")
    public Result<Seek> getById(@PathVariable Long id){
        log.info("根据id查询商品信息，参数为：{}",id);
        Seek seek= seekService.getById(id);
        return Result.success(seek);
    }

    /**
     * 编辑商品信息
     * @param seekDTO
     * @return
     */
    @ApiOperation("编辑商品信息")
    @PutMapping
    public Result update(@RequestBody SeekDTO seekDTO){
        log.info("编辑商品信息：{}",seekDTO);
        Seek seek= BeanUtil.copyProperties(seekDTO, Seek.class);
        //自动调用自动配置信息........
        seekService.updateById(seek);
        return Result.success();
    }

    /**
     * 根据id删除商品信息
     * @param id
     * @return
     */
    @ApiOperation("根据id删除商品信息")
    @DeleteMapping("/{id}")
    public Result<Seek> deleteById(@PathVariable Long id){
        log.info("根据id删除商品信息，参数为：{}",id);
        seekService.removeById(id);
        return Result.success();
    }
}
