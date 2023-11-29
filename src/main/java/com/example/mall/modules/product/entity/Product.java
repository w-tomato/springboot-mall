package com.example.mall.modules.product.entity;

import lombok.Data;

@Data
public class Product {
    private Long id;

    private String name;

    private Integer categoryId;

    private Long price;

    private Integer remain;

    private String status;

    private String intro;

    private String coverImage;

}