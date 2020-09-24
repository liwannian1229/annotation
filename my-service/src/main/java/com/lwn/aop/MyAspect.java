package com.lwn.aop;

import com.lwn.annotation.MyAnnotation_1;
import com.lwn.exception.AnnotationException;
import com.lwn.token.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author liwannian
 * @date 2020年9月11日17点58分17点58分
 */
@Slf4j
@Component
@Aspect
public class MyAspect {
    @Autowired
    private TokenService tokenService;

    @Pointcut("@annotation(com.lwn.annotation.MyAnnotation)")
    public void pointcut() {

    }

    @Pointcut("@annotation(com.lwn.annotation.MyAnnotation_1)||@within(com.lwn.annotation.MyAnnotation_1)")
    public void pointcut1() {

    }

    @Around("pointcut1()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

//        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        assert sra != null;
//        HttpServletRequest request = sra.getRequest();
//        request.getHeader("token");
//        request.getHeader("Authorization");
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        MyAnnotation_1 methodAnnotation = method.getAnnotation(MyAnnotation_1.class);

        // 方法优先级大于类
        if (methodAnnotation == null) {
            log.info("验证token,注解作用在类上");
            MyAnnotation_1 classAnnotation = pjp.getTarget().getClass().getAnnotation(MyAnnotation_1.class);
            if (classAnnotation != null) {
                tokenService.checkToken_1(classAnnotation.value());
            }

        } else {
            log.info("验证token,注解作用在方法上");
            tokenService.checkToken_1(methodAnnotation.value());
        }

        return pjp.proceed();
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) throws AnnotationException {

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        tokenService.checkToken(request);
    }


}
