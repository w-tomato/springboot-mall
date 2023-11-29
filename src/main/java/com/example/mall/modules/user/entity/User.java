package com.example.mall.modules.user.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/7
 */
@Data
public class User {

    private Integer id;

    private String name;

    private String nickname;

    private String password;

    private String phone;

    private String roleId;

    private String introduction;

    private String shippingAddress;

    private String avatar;

    private Date createTime;

    private Date updateTime;

}
