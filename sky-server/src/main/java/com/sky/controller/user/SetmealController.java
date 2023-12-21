package com.sky.controller.user;

import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Slf4j
@Api(tags = "套餐接口")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @GetMapping("list")
    public Result<List<Setmeal>> list(Long categoryId) {
        log.info("根据分类id:{}获取菜品", categoryId);
        List<Setmeal> setmeals = setmealService.getSetmealsByCategoryId(categoryId);
        return Result.success(setmeals);
    }
}
