package com.example.mall.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class AllowOriginFilter implements Filter {
 
    @SuppressWarnings("unused")
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
 
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest)req;
        // response.setHeader("Access-Control-Allow-Origin", "*"); // 设置允许所有跨域访问（不支持带Cookie）
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin")); // 设置允许所有跨域访问（支持带Cookie）
        response.setHeader("Access-Control-Allow-Credentials", "true"); // 配合前端
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept,Authorization,token");
        chain.doFilter(req,res);
    }
 
    public void init(FilterConfig filterConfig) {}
 
    public void destroy() {}
}