package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminCategoryController")
@Api(tags = "分类相关接口")
@Slf4j
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @ApiOperation("添加分类")
    public Result add(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类：{}", categoryDTO);
        categoryService.add(categoryDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> list(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分类分页查询:{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.query(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("根据id设置状态")
    public Result setStatus(@PathVariable Integer status, Long id) {
        log.info("根据id:{}设置状态:{}", id, status);
        categoryService.setStatus(id, status);
        return Result.success();
    }


    @PutMapping
    @ApiOperation("修改分类")
    public Result update(@RequestBody CategoryDTO categoryDTO) {
        log.info("修改分类信息:{}", categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("删除分类")
    public Result delete(Long id) {
        log.info("根据id:{}删除分类", id);
        categoryService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("根据类型获取下拉列表")
    public Result<List<Category>> list(Integer type) {
        log.info("根据type:{}查询list", type);
        List<Category> list = categoryService.getListByType(type);
        return Result.success(list);
    }
}
