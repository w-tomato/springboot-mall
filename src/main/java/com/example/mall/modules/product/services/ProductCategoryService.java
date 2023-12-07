package com.example.mall.modules.product.services;

import com.example.mall.modules.product.entity.ProductCategory;
import com.example.mall.modules.product.mapper.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author w-tomato
 * @description 产品分类服务
 * @date 2023/12/1
 */
@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    public ProductCategory selectOne(Integer id) {
        return productCategoryMapper.selectByPrimaryKey(id);
    }

    public List<ProductCategory> selectByIdList(List<Integer> idList) {
        return productCategoryMapper.selectByIdList(idList);
    }



}
