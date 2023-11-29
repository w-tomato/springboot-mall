package com.example.mall.modules.shoppingcart.mapper;

import com.example.mall.modules.shoppingcart.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShoppingCart record);

    int insertSelective(ShoppingCart record);

    ShoppingCart selectByPrimaryKey(Integer id);

    List<ShoppingCart> selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(ShoppingCart record);

    int updateByPrimaryKey(ShoppingCart record);

    List<ShoppingCart> selectByUserIdAndProductId(Integer userId, Long productId);
}