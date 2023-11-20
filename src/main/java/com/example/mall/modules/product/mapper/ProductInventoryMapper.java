package com.example.mall.modules.product.mapper;

import com.example.mall.modules.product.entity.ProductInventory;

public interface ProductInventoryMapper {
    int deleteByPrimaryKey(Integer productId);

    int insert(ProductInventory record);

    int insertSelective(ProductInventory record);

    ProductInventory selectByPrimaryKey(Integer productId);

    int updateByPrimaryKeySelective(ProductInventory record);

    int updateByPrimaryKey(ProductInventory record);
}