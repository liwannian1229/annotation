package com.lwn.aop;

import com.lwn.annotation.TokenValidator;
import com.lwn.auth.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

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

    @Pointcut("@annotation(com.lwn.annotation.TokenValidator)||@within(com.lwn.annotation.TokenValidator)")
    public void pointcut() {

    }

    // @Around方法在@Before之前执行
    @Around("pointcut()")
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
}
