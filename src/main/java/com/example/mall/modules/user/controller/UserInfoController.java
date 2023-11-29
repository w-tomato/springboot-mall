package com.example.mall.modules.user.controller;

import com.example.mall.modules.user.entity.User;
import com.example.mall.modules.user.entity.bo.UserBO;
import com.example.mall.modules.user.services.UserService;
import com.example.mall.pojo.ResultObject;
import com.example.mall.security.LoginUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/23
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserService userService;

    @RequestMapping("/info")
    public ResultObject getUserInfo(HttpServletRequest request) {
        Object userInfo = request.getAttribute("userInfo");
        if (userInfo != null) {
            return new ResultObject(200, "success", userInfo);
        }
        return new ResultObject(201, "fail", null);
    }

    @RequestMapping("/update")
    public ResultObject update(HttpServletRequest request, @RequestBody UserBO userBO) {
        User user = new User();
        BeanUtils.copyProperties(userBO, user);
        UserBO userInfo = (UserBO) request.getAttribute("userInfo");
        user.setId(userInfo.getId());
        userService.updateUser(user);
        return new ResultObject(200, "success", null);
    }

}
