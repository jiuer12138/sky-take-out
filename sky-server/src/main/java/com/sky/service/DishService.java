package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    /**
     * 添加菜品分类以及口味
     *
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    PageResult queryPage(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 批量删除菜品
     *
     * @param ids
     */
    void deleteBatchByIds(List<Long> ids);

    /**
     * 根据id获取菜品信息
     *
     * @param id
     * @return
     */
    DishVO getById(Long id);

    /**
     * 更新菜品信息以及关联的口味信息
     *
     * @param dishDTO
     */
    void updateWithFlavors(DishDTO dishDTO);

    /**
     * 根据id设置状态
     *
     * @param status
     * @param id
     */
    void setStatusById(Integer status, Long id);

    /**
     * 根据分类id或者name获取菜品
     *
     * @param categoryId
     * @param name
     * @return
     */
    List<Dish> getByCategoryIdOrName(Integer categoryId, String name);

    /**
     * 根据分类id获取菜品以及口味信息
     *
     * @param categoryId
     * @return
     */
    List<DishVO> getWithFlavorByCategoryId(Long categoryId);
}
