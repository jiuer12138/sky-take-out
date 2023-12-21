package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 新增套餐
     *
     * @param setmealDTO
     */
    @Transactional
    public void save(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        //插入一条套餐信息
        setmealMapper.insert(setmeal);
        //获取插入的主键
        Long setmealId = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
        });
        //插入套餐相关菜品信息以及份数
        setmealDishMapper.insertBatch(setmealDishes);
    }

    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO
     * @return
     */
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        //开始分页
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> setmealVOS = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(setmealVOS.getTotal(), setmealVOS.getResult());
    }

    /**
     * 设置套餐状态
     *
     * @param id
     * @param status
     */
    public void setStatus(Long id, Integer status) {
        setmealMapper.setStatus(id, status);
    }

    /**
     * 根据ID获取套餐以及关联菜品信息
     *
     * @param id
     * @return
     */
    @Transactional
    public SetmealVO getSetmealWithDish(Long id) {
        //获取套餐基本信息
        Setmeal setmeal = setmealMapper.getById(id);
        //获取套餐关联的菜品信息
        List<SetmealDish> setmealDishes = setmealDishMapper.getDishesBySetmealId(id);
        //赋值给vo对象
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);

        return setmealVO;
    }

    /**
     * 更新套餐以及关联菜品信息
     *
     * @param setmealDTO
     */
    @Transactional
    public void updateWithDishes(SetmealDTO setmealDTO) {
        //更新套餐基本信息
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);
        //更新套餐关联的菜品信息
        //先删除关联的菜品信息
        setmealDishMapper.deleteBySetmealId(setmealDTO.getId());
        //插入新的关联菜品信息并对dish_id赋值
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealDTO.getId());
        });
        setmealDishMapper.insertBatch(setmealDishes);
    }

    /**
     * 批量删除套餐以及关联的菜品
     *
     * @param ids
     */
    @Transactional
    public void deleteWithDishesByIds(List<Long> ids) {
        //删除套餐基本信息
        setmealMapper.deletBatchByIds(ids);
        //删除套餐关联的菜品信息
        setmealDishMapper.deleteBySetmealIds(ids);
    }


    /**
     * 根据分类categoryId获取套餐信息
     *
     * @param categoryId
     * @return
     */
    public List<Setmeal> getSetmealsByCategoryId(Long categoryId) {
        //获取套餐基本信息
        return setmealMapper.getByCategoryId(categoryId);
    }
}
