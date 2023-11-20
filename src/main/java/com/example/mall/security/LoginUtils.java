package com.example.mall.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.mall.modules.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/10
 */
public class LoginUtils {

    // 日志
    private static final Logger logger = LoggerFactory.getLogger(LoginUtils.class);

    //  随机生成的token秘钥
    private static final String TOKEN_KEY = "eyKhdWQiOiIiIwiZXhwIjoLCJpYXQiOjE3MDA0NjA0NDN9";

    public static String createToken(User user, long expireTime) {
        //过期时间
        Date expireDate = new Date(System.currentTimeMillis() + expireTime);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        return JWT.create()
                .withHeader(map)// 添加头部
                //可以将基本信息放到claims中
                .withClaim("id", user.getId())//userId
                .withExpiresAt(expireDate) //超时设置,设置过期的日期
                .withIssuedAt(new Date()) //签发时间
                .sign(Algorithm.HMAC256(TOKEN_KEY));
    }

    public static Integer validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_KEY)).build();
            DecodedJWT verify = verifier.verify(token);
            Claim id = verify.getClaim("id");
            if (id == null) {
                return 0;
            }
            return id.asInt();
        } catch (JWTVerificationException e) {
            if (e.getMessage().contains("expired")) {
                return -1;
            }
            logger.error("token验证失败", e);
            return null;
        }
    }


}
