package com.lwn.common;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author liwannian
 * @date 2020年9月11日17点58分17点58分
 */
@Slf4j
@Aspect
@Component
public class GlobalLogAspect {

    @Pointcut("execution(public * com.lwn.controller..*.*(..))")
    public void webLog() {

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        log.info("请求地址 url:" + request.getRequestURL().toString());
        log.info("请求方法 http_method:" + request.getMethod());
        log.info("请求 ip:" + request.getRemoteAddr());
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            log.info("入参名 name:" + name + ",入参值 value:" + request.getParameter(name));
        }
        Object[] args = joinPoint.getArgs();
        log.info("方法签名 method signature: " + joinPoint.getSignature());
        if (args != null) {
            for (Object arg : args) {
                try {
                    log.info("json格式参数 args: " + JSON.toJSONString(arg));
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            }
        }
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        //处理完请求，返回内容
        log.info("响应数据 response：" + ret);
    }
}
