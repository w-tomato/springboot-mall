package com.example.mall.modules.order.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order {
    private Integer id;

    private Integer userId;

    private Date orderDate;

    private BigDecimal totalAmount;

    private String shippingAddress;

    private String paymentMethod;

    private String status;

    private Date createTime;

    private Date updateTime;

}