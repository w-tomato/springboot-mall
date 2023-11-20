package com.example.mall.modules.product.mapper;

import com.example.mall.modules.product.entity.ProductOutbound;

public interface ProductOutboundMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductOutbound record);

    int insertSelective(ProductOutbound record);

    ProductOutbound selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductOutbound record);

    int updateByPrimaryKey(ProductOutbound record);
}