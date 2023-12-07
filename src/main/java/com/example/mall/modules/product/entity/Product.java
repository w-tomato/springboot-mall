package com.example.mall.modules.product.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private Long id;

    private String name;

    private Integer categoryId;

    private BigDecimal price;

    private String status;

    private String intro;

    private String coverImage;

}