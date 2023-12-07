package com.example.mall.modules.product.entity;

import lombok.Data;

/**
 * @author w-tomato
 * @description
 * @date 2023/12/1
 */
@Data
public class ProductBO extends Product{

    private Integer inventory;

    private String categoryName;

}
