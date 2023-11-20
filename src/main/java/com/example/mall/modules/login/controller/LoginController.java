package com.example.mall.modules.login.controller;

import com.example.mall.modules.user.entity.bo.UserBO;
import com.example.mall.modules.login.dto.UserLoginDTO;
import com.example.mall.modules.user.entity.User;
import com.example.mall.modules.user.entity.UserRole;
import com.example.mall.modules.user.services.UserRoleService;
import com.example.mall.pojo.ResultObject;
import com.example.mall.modules.user.services.UserService;
import com.example.mall.security.LoginUtils;
import com.example.mall.security.PassToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/7
 */
@RequestMapping("/user")
@RestController
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @PassToken
    @RequestMapping("/login")
    public ResultObject login(@RequestBody UserLoginDTO userLoginDTO) {
        if (userLoginDTO == null) {
            return new ResultObject();
        }
        if (userLoginDTO.getUsername() == null || userLoginDTO.getPassword() == null) {
            return new ResultObject();
        }

        // 暂时用明文密码，后面改成加密

        User user = userService.selectOne(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        if (user == null) {
            return new ResultObject(201, "用户名或密码错误", null);
        }

        // 生成token
        String token = LoginUtils.createToken(user, 3 * 1000);
        String freshToken = LoginUtils.createToken(user, 24 * 60 * 60 * 1000);
        Map<String, Object> dataMap= new HashMap<>();
        dataMap.put("token", token);
        dataMap.put("freshToken", freshToken);
        // 根据用户角色ID查询用户角色列表
        List<Integer> roleIdList = Arrays.stream(user.getRoleId().split(",")).map(Integer::parseInt).collect(Collectors.toList());
        List<UserRole> userRoleList = userRoleService.getUserRoleList(roleIdList);
        UserBO userBO = new UserBO();
        BeanUtils.copyProperties(user, userBO);
        userBO.setRoles(userRoleList.stream().map(UserRole::getName).collect(Collectors.toList()));
        dataMap.put("user", userBO);

        return new ResultObject(200, "登录成功", dataMap);
    }

    /*@RequestMapping("/info")
    public ResultObject info(String token) {
        ResultObject resultObject = new ResultObject();
        resultObject.setCode(200);
        resultObject.setMessage("登录成功");

        if ("admin-token".equals(token)) {
            UserBO userInfoBO = new UserBO();
            userInfoBO.setName("admin");
            userInfoBO.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            userInfoBO.setIntroduction("I am a super administrator");
            userInfoBO.setRoles(new ArrayList<String>(){{
                add("admin");
            }});
            resultObject.setData(userInfoBO);
        } else if ("editor-token".equals(token)) {
            UserBO userInfoBO = new UserBO();
            userInfoBO.setName("editor");
            userInfoBO.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            userInfoBO.setIntroduction("I am a Normal editor");
            userInfoBO.setRoles(new ArrayList<String>(){{
                add("editor");
            }});
            resultObject.setData(userInfoBO);
        } else {
            resultObject.setCode(500);
            resultObject.setMessage("账号或密码错误");
        }
        return resultObject;
    }*/

    @PassToken
    @RequestMapping("/logout")
    public ResultObject logout() {
        return new ResultObject(200, "登出成功", null);
    }

}
