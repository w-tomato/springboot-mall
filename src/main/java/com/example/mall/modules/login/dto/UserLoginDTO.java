package com.example.mall.modules.login.dto;

import org.springframework.stereotype.Component;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/7
 */
@Component
public class UserLoginDTO {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
