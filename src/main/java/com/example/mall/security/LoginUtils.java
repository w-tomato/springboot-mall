package com.example.mall.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.mall.modules.user.entity.User;

import java.util.Date;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/10
 */
public class LoginUtils {

    public static String getToken(User user, long time) {
        long endTime = System.currentTimeMillis() + time;
        Date end = new Date(endTime);//token结束时间
        String token = JWT.create()
                .withAudience(user.getId().toString()) // 将 user id 保存到 token 里面
                .withIssuedAt(new Date())//token开始时间
                .withExpiresAt(end)//token存活截止时间
                .sign(Algorithm.HMAC256(user.getPassword()));// 用用户密码进行加密
        return token;
    }


}
