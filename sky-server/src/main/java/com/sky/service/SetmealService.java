package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

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
}
