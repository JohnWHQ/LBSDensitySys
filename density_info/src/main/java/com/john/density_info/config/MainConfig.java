package com.john.density_info.config;

import com.john.density_info.interceptor.LoginInterceptor;
import com.john.density_info.interceptor.MainInterceptor;
import com.john.density_info.interceptor.StatusInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * 注册模板类
 * 注入拦截器并注册
 *
 */
@Component
public class MainConfig extends WebMvcConfigurerAdapter{
    @Autowired
    MainInterceptor mainInterceptor;

    @Autowired
    StatusInterceptor statusInterceptor;

    @Autowired
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 模板类拦截器注册
        registry.addInterceptor(mainInterceptor);
        // 测试类拦截器注册
        registry.addInterceptor(statusInterceptor).addPathPatterns("/setting*");
        // 登录状态拦截器注册
        registry.addInterceptor(loginInterceptor).addPathPatterns("/login*");
        super.addInterceptors(registry);
    }

    // 配置ajax跨域访问
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
