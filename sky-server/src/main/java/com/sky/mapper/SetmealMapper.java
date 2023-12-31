package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SetmealMapper {

    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);

    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    @Update("update setmeal set status=#{status} where id=#{id}")
    void setStatus(Long id, Integer status);

    @Select("select * from setmeal where id=#{id}")
    Setmeal getById(Long id);

    void update(Setmeal setmeal);

    void deletBatchByIds(List<Long> ids);

    @Select("select * from setmeal where category_id = #{categoryId}")
    List<Setmeal> getByCategoryId(Long categoryId);

    /**
     * 根据套餐id查询菜品选项
     *
     * @param setmealId
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

    @Select("select * from setmeal where category_id = #{categoryId} and status = #{status}")
    List<Setmeal> list(Setmeal setmeal);
}
