package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);

    Page<DishVO> queryPage(DishPageQueryDTO dishPageQueryDTO);

    @Select("SELECT * FROM dish WHERE id=#{id}")
    Dish getById(Long id);

    @Delete("delete from dish where id=#{id}")
    void deletById(Long id);

    void deleteByIds(List<Long> ids);

    List<Dish> getByIds(List<Long> ids);

    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);
}
