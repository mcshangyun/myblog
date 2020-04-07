package com.mcsy.blog.config;

import com.mcsy.blog.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * webmvc的配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 添加拦截器
     * @param registry 拦截器的注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                //拦截admin路径下的所有路径
                .addPathPatterns("/admin/**")
                //排除/admin请求路径和/admin/login请求路径
                .excludePathPatterns("/admin","/admin/login");
    }
}
