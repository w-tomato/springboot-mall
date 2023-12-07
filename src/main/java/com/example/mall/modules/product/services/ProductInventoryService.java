package com.example.mall.modules.product.services;

import com.example.mall.modules.product.entity.ProductInventory;
import com.example.mall.modules.product.mapper.ProductInventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author w-tomato
 * @description
 * @date 2023/12/1
 */
@Service
public class ProductInventoryService {

    @Autowired
    private ProductInventoryMapper productInventoryMapper;

    public ProductInventory selectInventoryByProductId(Long productId) {
        return productInventoryMapper.selectByPrimaryKey(productId);
    }

}
