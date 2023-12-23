package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    @Select("select * from shopping_cart order by create_time desc ")
    List<ShoppingCart> list();

    List<ShoppingCart> getList(ShoppingCart shoppingCart);

    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void update(ShoppingCart cart);

    void insert(ShoppingCart shoppingCart);
}
