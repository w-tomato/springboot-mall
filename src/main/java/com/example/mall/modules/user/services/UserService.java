package com.example.mall.modules.user.services;

import com.example.mall.modules.user.entity.User;
import com.example.mall.modules.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/10
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User selectOne(String name, String password) {
        return userMapper.selectOne(name, password);
    }

    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

}
