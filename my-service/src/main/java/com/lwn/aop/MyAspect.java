package com.lwn.aop;

import com.lwn.annotation.TokenValidator;
import com.lwn.exception.AnnotationException;
import com.lwn.auth.TokenService;
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

    @Pointcut("@annotation(com.lwn.annotation.TokenValidator)||@within(com.lwn.annotation.TokenValidator)")
    public void pointcutToken() {

    }

    @Around("pointcutToken()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        TokenValidator methodAnnotation = method.getAnnotation(TokenValidator.class);

        // 方法优先级大于类
        if (methodAnnotation == null) {
            log.info("验证token,注解作用在类上");
            TokenValidator classAnnotation = pjp.getTarget().getClass().getAnnotation(TokenValidator.class);
            if (classAnnotation != null) {
                tokenService.checkTokenIsValid(classAnnotation.value());
            }

        } else {
            log.info("验证token,注解作用在方法上");
            tokenService.checkTokenIsValid(methodAnnotation.value());
        }

        return pjp.proceed();
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) throws AnnotationException {

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        tokenService.checkToken(request);
    }


}
