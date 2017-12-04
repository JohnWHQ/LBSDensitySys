package com.john.density_info.interceptor;

import com.john.density_info.mode.HostHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 登录状态管理拦截器
 *
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    HostHolder hostHolder;

    private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 设置session有效时间为1小时
        request.getSession().setMaxInactiveInterval(3600);
        // 同步给全局会话控制实例
        hostHolder.setUser_token(request.getSession().getId());
        // 写入日志
        logger.info("登录日志记录-session_id={}",request.getSession().getId());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        logger.info("登录用户信息-user_info={}",hostHolder.getUserInfo());
        hostHolder.set_info(hostHolder.getUser_token(),hostHolder.getUserInfo());
    }
}
