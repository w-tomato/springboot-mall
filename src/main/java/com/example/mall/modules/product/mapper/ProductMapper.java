package com.example.mall.modules.product.mapper;

import com.example.mall.modules.product.entity.Product;
import com.example.mall.modules.product.entity.ProductBO;
import com.example.mall.modules.product.entity.ProductInventory;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectAll();

    List<ProductBO> selectByName(String name, String status);
}