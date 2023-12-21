package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 添加菜品分类以及口味
     *
     * @param dishDTO
     */
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        //插入菜品相关信息
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insert(dish);

        //获取生成的主键值
        Long id = dish.getId();
        //插入口味相关数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(id));
            dishFlavorMapper.insertBatch(flavors);
        }

    }

    public PageResult queryPage(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        //查询dish表和category表
        Page<DishVO> dishVOS = dishMapper.queryPage(dishPageQueryDTO);
        return new PageResult(dishVOS.getTotal(), dishVOS.getResult());
    }

    /**
     * 批量删除菜品
     *
     * @param ids
     */
    @Transactional
    public void deleteBatchByIds(List<Long> ids) {
        //判断当前菜品是否能够删除---是否存在起售中的菜品？？
        // for (Long id : ids) {
        //    Dish dish = dishMapper.getById(id);
        //     Integer status = dish.getStatus();
        //     if (Objects.equals(status, StatusConstant.ENABLE)){
        //        throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
        //     }
        // }

        //判断当前菜品是否能够删除---是否存在起售中的菜品？？
        List<Dish> dishes = dishMapper.getByIds(ids);
        for (Dish dish : dishes) {
            Integer status = dish.getStatus();
            if (Objects.equals(status, StatusConstant.ENABLE)) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        //判断当前菜品是否能够删除---是否被套餐关联了？？
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIds != null && !setmealIds.isEmpty()) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //刪除菜品表中的菜品数据
        //for (Long id : ids) {
        //     dishMapper.deletById(id);
        //    //刪除菜品关联的口味数据
        //   dishFlavorMapper.deleteByDishId(id);
        //}

        //刪除菜品表中的菜品数据
        dishMapper.deleteByIds(ids);
        //刪除菜品关联的口味数据
        dishFlavorMapper.deleteByDishIds(ids);
    }

    /**
     * 根据id获取菜品信息
     *
     * @param id
     * @return
     */
    public DishVO getById(Long id) {
        DishVO dishVO = new DishVO();
        // 根据id获取菜品基本信息
        Dish dish = dishMapper.getById(id);
        BeanUtils.copyProperties(dish, dishVO);
        //根据dishId获取口味信息
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    /**
     * 更新菜品信息以及关联的口味信息
     *
     * @param dishDTO
     */
    @Transactional
    public void updateWithFlavors(DishDTO dishDTO) {
        // 更新菜品信息
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);
        //更新相关的口味信息
        //先根据id删除口味信息
        dishFlavorMapper.deleteByDishId(dishDTO.getId());
        //重新插入口味信息
        //插入口味相关数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(dishDTO.getId()));
            dishFlavorMapper.insertBatch(flavors);
        }

    }

    /**
     * 根据id设置状态
     *
     * @param status
     * @param id
     */
    public void setStatusById(Integer status, Long id) {
        dishMapper.setStatusById(status, id);
    }

    /**
     * 根据分类id或者name获取菜品
     *
     * @param categoryId
     * @param name
     * @return
     */
    public List<Dish> getByCategoryIdOrName(Integer categoryId, String name) {
        return dishMapper.getByCategoryIdOrName(categoryId, name);
    }

    /**
     * 根据分类id获取菜品以及口味信息
     *
     * @param categoryId
     * @return
     */
    @Transactional
    public List<DishVO> getWithFlavorByCategoryId(Long categoryId) {
        ArrayList<DishVO> dishVOS = new ArrayList<>();
        //获取菜品信息
        List<Dish> dishes = dishMapper.getByCategoryId(categoryId);
        ArrayList<Long> dishIds = new ArrayList<>();
        dishes.forEach(dish -> {
            dishIds.add(dish.getId());
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(dish,dishVO);
            dishVOS.add(dishVO);
        });

        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishIds(dishIds);
        //根据菜品id分组
        Map<Long, List<DishFlavor>> collects = dishFlavors.stream().collect(
                Collectors.groupingBy(
                        DishFlavor::getDishId
                )
        );
        dishVOS.forEach(dishVO -> {
            dishVO.setFlavors(collects.get(dishVO.getId()));
        });
        return dishVOS;
    }
}
