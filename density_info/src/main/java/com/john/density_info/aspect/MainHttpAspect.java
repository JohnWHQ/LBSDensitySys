package com.john.density_info.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 模板切面AOP类
 */
@Aspect
@Component
public class MainHttpAspect {
    private static final Logger logger = LoggerFactory.getLogger(MainHttpAspect.class);

    // 切入点定义 拦截controller
    @Pointcut("execution(public * com.john.density_info.controller.mainController.*(..))")
    public void log(){
    }

    // 前切
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){

        logger.info("doBefore method log ----------------------------------------");

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest servletRequest = attributes.getRequest();

        // url
        logger.info("url={}", servletRequest.getRequestURL());

        // 请求方法
        logger.info("method={}", servletRequest.getMethod());

        // IP地址
        logger.info("ip={}", servletRequest.getRemoteAddr());

        // 调用的类方法
        logger.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName());

        // 请求参数
        logger.info("args={}",joinPoint.getArgs());
    }

    // 后切
    @After("log()")
    public void doAfter(){
        logger.info("doAfter method log ----------------------------------------");
    }

    // 返回值拦截
    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object){
        logger.info("doReturn method log ----------------------------------------");
        if (object == null){
            logger.info("response={}", "object is null");
        }
        logger.info("response={}", object.toString());
    }
}