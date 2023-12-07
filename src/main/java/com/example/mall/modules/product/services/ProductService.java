package com.example.mall.modules.product.services;

import com.example.mall.modules.product.entity.Product;
import com.example.mall.modules.product.entity.ProductBO;
import com.example.mall.modules.product.mapper.ProductMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/6
 */
@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public PageInfo<ProductBO> list(Integer pageNum, Integer pageSize, String name, String status) {
        return PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(
                () -> productMapper.selectByName(name, status));
    }

    public void add(Product product) {
        productMapper.insert(product);
    }

    public void update(Product product) {
        productMapper.updateByPrimaryKey(product);
    }

    public void delete(Long id) {
        productMapper.deleteByPrimaryKey(id);
    }

    public Product selectOne(Long id) {
        return productMapper.selectByPrimaryKey(id);
    }



}
