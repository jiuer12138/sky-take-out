package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    /**
     * 新增套餐
     *
     * @param setmealDTO
     */
    void save(SetmealDTO setmealDTO);

    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 设置套餐状态
     *
     * @param id
     * @param status
     */
    void setStatus(Long id, Integer status);

    /**
     * 根据ID获取套餐以及关联菜品信息
     *
     * @param id
     * @return
     */
    SetmealVO getSetmealWithDish(Long id);

    /**
     * 更新套餐以及关联菜品信息
     *
     * @param setmealDTO
     */
    void updateWithDishes(SetmealDTO setmealDTO);

    /**
     * 批量删除套餐以及关联的菜品
     *
     * @param ids
     */
    void deleteWithDishesByIds(List<Long> ids);

    /**
     * 根据分类categoryId获取套餐信息
     *
     * @param categoryId
     * @return
     */
    List<Setmeal> getSetmealsByCategoryId(Long categoryId);

    /**
     * 根据id查询菜品选项
     *
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);
}
