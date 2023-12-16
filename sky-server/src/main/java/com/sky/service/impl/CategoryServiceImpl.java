package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 新增分类
     *
     * @param categoryDTO categoryDTO
     */
    @Override
    public void add(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        //设置分类状态为禁用
        category.setStatus(StatusConstant.DISABLE);
        //设置创建时间和创建人id
        //category.setCreateTime(LocalDateTime.now());
        //category.setCreateUser(BaseContext.getCurrentId());
        //设置更新时间和更新人id
        //category.setUpdateTime(LocalDateTime.now());
        //category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.insert(category);
    }

    /**
     * 分类分页查询
     *
     * @param categoryPageQueryDTO categoryPageQueryDTO
     * @return 分类结果
     */
    @Override
    public PageResult query(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        List<Category> records = page.getResult();
        long total = page.getTotal();
        PageResult pageResult = new PageResult();
        pageResult.setRecords(records);
        pageResult.setTotal(total);
        return pageResult;
    }

    /**
     * 根据id设置状态
     *
     * @param id     id
     * @param status 状态
     */
    @Override
    public void setStatus(Long id, Integer status) {
        Category category = Category.builder()
                .status(status)
                .id(id)
                .build();
        //设置更新人id以及更新时间
        //category.setUpdateUser(BaseContext.getCurrentId());
        //category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    /**
     * 修改分类信息
     *
     * @param categoryDTO categoryDTO
     */
    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        //设置更新人id以及更新时间
        //category.setUpdateUser(BaseContext.getCurrentId());
        //category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    /**
     * 根据id删除分类
     *
     * @param id id
     */
    // TODO 后续删除分类还需判断分类是否关联的菜品或者套餐
    @Override
    public void deleteById(Long id) {
        categoryMapper.deleteById(id);
    }

    /**
     * 根据类型获取list
     * @param type type
     * @return list
     */
    @Override
    public List<Category> getListByType(Integer type) {
        return categoryMapper.getListByType(type);
    }
}
