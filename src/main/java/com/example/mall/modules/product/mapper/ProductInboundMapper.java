package com.example.mall.modules.product.mapper;

import com.example.mall.modules.product.entity.ProductInbound;

public interface ProductInboundMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductInbound record);

    int insertSelective(ProductInbound record);

    ProductInbound selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductInbound record);

    int updateByPrimaryKey(ProductInbound record);
}