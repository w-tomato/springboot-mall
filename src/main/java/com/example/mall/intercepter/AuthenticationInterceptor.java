package com.example.mall.intercepter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.mall.modules.user.entity.User;
import com.example.mall.modules.user.services.UserService;
import com.example.mall.security.LoginUtils;
import com.example.mall.security.PassToken;
import com.example.mall.security.UserLoginToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/10
 */

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        //通过所有OPTION请求
        if(httpServletRequest.getMethod().toUpperCase().equals("OPTIONS")){
            return true;
        }

        // 这个token是一个访问令牌，用来获取用户信息
        String token = httpServletRequest.getHeader("Authorization");
        // 这个token是一个刷新令牌，用来刷新token，这个token时间比较长，因为如果只是token过期了，但是refreshToken还没过期，那么就可以用refreshToken来刷新token
        // 这样用户不需要重新登录，如果refreshToken也过期了，那么就需要重新登录了
        String refreshToken = httpServletRequest.getHeader("freshToken");// 从 http 请求头中取出 token

        // 如果该请求不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有PassToken注解，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        if (token == null) {
            httpServletResponse.setStatus(401);
            httpServletResponse.getWriter().write("Token为空，请重新登录");
            return false;
        }


        // 获取 token 中的 用户信息
        String userValue;
        try {
            userValue = JWT.decode(token).getAudience().get(0);
            if (StringUtils.isBlank(userValue) || !StringUtils.isNumeric(userValue)) {
                setResponse(httpServletResponse, 401, "Token解析失败，请重新登录");
                return false;
            }
        } catch (JWTDecodeException j) {
            setResponse(httpServletResponse, 401, "Token解析失败，请重新登录");
            throw new RuntimeException("401");
        }

        User user = userService.selectById(Integer.valueOf(userValue));
        if (user == null) {
            setResponse(httpServletResponse, 401, "用户不存在，请重新登录");
            return false;
        }

        Date oldTime = JWT.decode(token).getExpiresAt();
        Date refreshTime = JWT.decode(refreshToken).getExpiresAt();
        long oldDiff = oldTime.getTime() - new Date().getTime();
        long refreshDiff = refreshTime.getTime() - new Date().getTime();
        if (oldDiff <= 0) {
            if (refreshDiff <= 0) {
                setResponse(httpServletResponse, 401, "Token已过期，请重新登录");
                return false;
            }
        }
        String newToken = LoginUtils.getToken(user, 60* 60 * 1000);
        String newRefToken = LoginUtils.getToken(user, 24*60*60*1000);
        // 更新token
        httpServletResponse.setHeader("Authorization", newToken);
        httpServletResponse.setHeader("freshToken", newRefToken);

        // 这里可以判断是不是需要进行登录验证，默认全部需要，可以通过注解UserLoginToken来进行控制
//        if (method.isAnnotationPresent(UserLoginToken.class) {
//            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
//            if (userLoginToken.required()) {
                // 利用用户密码，解密验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    // token验证失败
                    setResponse(httpServletResponse, 401, "Token验证失败，请重新登录");
                    throw new RuntimeException("401");
                }
                return true;
//            }
//        }
//        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }

    private void setResponse(HttpServletResponse response, int code, String msg) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(code);
        try {
            response.getWriter().write(msg);
        } catch (Exception e) {
            log.error("response error", e);
        }
    }
}