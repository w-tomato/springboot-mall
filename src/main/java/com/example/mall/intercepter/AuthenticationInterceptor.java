package com.example.mall.intercepter;

import com.alibaba.fastjson.JSON;
import com.example.mall.modules.records.services.UserAccessRecordsService;
import com.example.mall.modules.user.entity.User;
import com.example.mall.modules.user.entity.UserRole;
import com.example.mall.modules.user.entity.bo.UserBO;
import com.example.mall.modules.user.services.UserRoleService;
import com.example.mall.modules.user.services.UserService;
import com.example.mall.security.LoginUtils;
import com.example.mall.security.PassToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author w-tomato
 * @description
 * @date 2023/11/10
 */

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    UserAccessRecordsService userAccessRecordsService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        // 通过所有OPTIONS请求
        if (httpServletRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true;
        }

        // 获取请求头中的token和refreshToken
        String token = httpServletRequest.getHeader("Authorization");
        String refreshToken = httpServletRequest.getHeader("freshToken");

        // 如果该请求不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 检查是否有PassToken注解，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        // 验证token和refreshToken的有效性
        if (token == null || refreshToken == null) {
            setResponse(httpServletResponse, 401, "登录信息过期，请重新登录");
            return false;
        }

        Integer userId = LoginUtils.validateToken(token);
        boolean needRefresh = false;
        if (userId == null) {
            setResponse(httpServletResponse, 401, "非法Token，请重新登录");
            return false;
        } else if (userId < 0) {
            needRefresh = true;
            userId = LoginUtils.validateToken(refreshToken);
            if (userId == null) {
                setResponse(httpServletResponse, 401, "非法Token，请重新登录");
                return false;
            } else if (userId < 0) {
                setResponse(httpServletResponse, 401, "登录信息过期，请重新登录");
                return false;
            }
        }

        User user = userService.selectById(userId);
        if (user == null) {
            setResponse(httpServletResponse, 401, "用户不存在，请重新登录");
            return false;
        }

        // 根据用户角色ID查询用户角色列表
        List<Integer> roleIdList = Arrays.stream(user.getRoleId().split(",")).map(Integer::parseInt).collect(Collectors.toList());
        List<UserRole> userRoleList = userRoleService.getUserRoleList(roleIdList);
        UserBO userBO = new UserBO();
        BeanUtils.copyProperties(user, userBO);
        userBO.setRoles(userRoleList.stream().map(UserRole::getName).collect(Collectors.toList()));
        httpServletRequest.setAttribute("userInfo", userBO);

        if (needRefresh) {
            // 生成新的token和refreshToken
            String newToken = LoginUtils.createToken(user, 3 * 1000);
            String newRefToken = LoginUtils.createToken(user, 24 * 60 * 60 * 1000);

            // 更新响应头中的token和refreshToken
            httpServletResponse.setHeader("Authorization", newToken);
            httpServletResponse.setHeader("freshToken", newRefToken);
            httpServletRequest.setAttribute("token", newToken);
        } else {
            // 放入原来的token和refreshToken
            httpServletResponse.setHeader("Authorization", token);
            httpServletResponse.setHeader("freshToken", refreshToken);
        }
        return true;
    }



    private void setResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        ResponseObject responseObject = new ResponseObject(message, status, null);
        response.getWriter().write(JSON.toJSONString(responseObject));
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

    class ResponseObject {
        private String message;
        private Integer code;
        private Object data;

        public ResponseObject() {
        }

        public ResponseObject(String message, Integer code, Object data) {
            this.message = message;
            this.code = code;
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

}