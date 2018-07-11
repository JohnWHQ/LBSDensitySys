package com.john.density_info.interceptor;

import com.john.density_info.mode.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器模板
 */
@Component
public class MainInterceptor implements HandlerInterceptor{

    @Autowired
    HostHolder hostHolder;


    // controller 执行前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String token = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
        }
        if (!StringUtils.isEmpty(token)) {
            // to do hostHolder.set(entity);
        }
        //System.out.println("intercept the controller before");
        return true;
    }

    // controller执行中
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        //System.out.println("intercept the controller ing");
    }

    // controller执行后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
//        hostHolder.remove();
        //System.out.println("intercept the controller after");
    }
}
