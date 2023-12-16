package com.sky.service;

import com.sky.dto.DishDTO;

public interface DishService {
    /**
     * 添加菜品分类以及口味
     * @param dishDTO
     */
     void saveWithFlavor(DishDTO dishDTO);
}
