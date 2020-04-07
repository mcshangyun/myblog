package com.mcsy.blog.interceptor;

import jdk.internal.org.objectweb.asm.Handle;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截
 *
 * @author 15199
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    /**
     * 请求到达之前进行处理的方法
     *
     * @param request  请求
     * @param response 响应
     * @param handler  控制器
     * @return 返回布尔值
     * @throws Exception 出现异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null) {
            //如果是空的就重定向到登陆页面
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
