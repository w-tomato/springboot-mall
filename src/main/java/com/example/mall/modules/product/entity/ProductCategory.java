package com.example.mall.modules.product.entity;

import lombok.Data;

@Data
public class ProductCategory {
    private Integer id;

    private Integer parentId;

    private String categoryName;
}