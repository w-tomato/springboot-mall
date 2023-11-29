package com.example.mall.modules.shoppingcart.entity;

import com.example.mall.modules.product.entity.Product;
import lombok.Data;

import java.util.Date;

@Data
public class ShoppingCart {
    private Integer id;

    private Integer userId;

    private Long productId;

    private Integer quantity;

    private Date createTime;

    private Date updateTime;

    private Product product;

}