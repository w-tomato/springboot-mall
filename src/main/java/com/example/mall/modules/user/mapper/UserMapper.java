package com.example.mall.modules.user.mapper;

import com.example.mall.modules.user.entity.User;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/10
 */
public interface UserMapper {

    User selectOne(String name, String password);

    User selectById(Integer id);

}
