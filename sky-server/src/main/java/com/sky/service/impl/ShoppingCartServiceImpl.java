package com.sky.service.impl;

import com.sky.entity.ShoppingCart;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    /**
     * 查看购物车
     *
     * @return
     */
    public List<ShoppingCart> list() {
        return shoppingCartMapper.list();
    }
}
