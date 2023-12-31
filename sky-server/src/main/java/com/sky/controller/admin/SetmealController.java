package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminSetmealController")
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐相关接口")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PostMapping
    @ApiOperation("新增套餐")
    @CacheEvict(cacheNames = "setmealCache",key = "#setmealDTO.categoryId")
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐信息：{}", setmealDTO);
        setmealService.save(setmealDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("套餐分页查询参数：{}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("status/{status}")
    @ApiOperation("修改套餐状态")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result setStatus(@PathVariable Integer status,Long id){
        log.info("设置套餐id:{}状态:{}",id,status);
        setmealService.setStatus(id,status);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("获取套餐详情")
    public Result<SetmealVO> getSetmealWithDish(@PathVariable Long id){
        log.info("根据id:{}获取套餐信息",id);
        SetmealVO setmealVO = setmealService.getSetmealWithDish(id);
        return Result.success(setmealVO);
    }

    @PutMapping
    @ApiOperation("更新套餐以及关联菜品")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result updateWithDishes(@RequestBody SetmealDTO setmealDTO){
        log.info("更新套餐以及关联菜品信息参数：{}",setmealDTO);
        setmealService.updateWithDishes(setmealDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("批量删除套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result deleteWithDishes(@RequestParam List<Long> ids){
        log.info("批量删除菜品的ids:{}",ids);
        setmealService.deleteWithDishesByIds(ids);
        return Result.success();
    }
}
