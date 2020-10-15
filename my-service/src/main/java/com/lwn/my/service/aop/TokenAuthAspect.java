package com.lwn.my.service.aop;

import com.lwn.my.service.annotation.TokenValidator;
import com.lwn.my.service.cache.RedisUtils;
import com.lwn.common.utils.enums.Const;
import com.lwn.my.service.exception.AnnotationException;
import com.lwn.my.service.exception.NoAuthException;
import com.lwn.repo.model.entity.UserInfo;
import com.lwn.common.utils.request.SessionHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author liwannian
 * @date 2020年9月11日17点58分17点58分
 */
@Slf4j
@Component
@Aspect
public class TokenAuthAspect {

    @Autowired
    private RedisUtils redisUtils;

    @Value("${token.timeout}")
    private Long tokenTimeOut;

    @Pointcut("@annotation(com.lwn.my.service.annotation.TokenValidator)||@within(com.lwn.my.service.annotation.TokenValidator)")
    private void pointcut() {

    }

    // @Around方法在@Before之前执行
    @Around("pointcut()")
    private Object around(ProceedingJoinPoint pjp) throws Throwable {

        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        TokenValidator methodAnnotation = method.getAnnotation(TokenValidator.class);

        // 方法优先级大于类
        if (methodAnnotation == null) {
            log.info("验证token,注解作用在类上");
            TokenValidator classAnnotation = pjp.getTarget().getClass().getAnnotation(TokenValidator.class);
            if (classAnnotation != null) {
                checkTokenIsValid(classAnnotation.value());
            }

        } else {
            log.info("验证token,注解作用在方法上");
            checkTokenIsValid(methodAnnotation.value());
        }

        return pjp.proceed();
    }

    private void checkTokenIsValid(boolean isCheck) {
        HttpServletRequest req = SessionHolder.getRequest();
        assert req != null;
        String token = req.getHeader("token");// getHeader()是获取HTTP头部信息,getParameter()是获取表单参数
        String userInfoId = redisUtils.get(Const.TOKEN + token, tokenTimeOut);
        if (StringUtils.isEmpty(token)) {
            token = req.getParameter("token");
            if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userInfoId)) {
                if (isCheck) {
                    throw new AnnotationException("token不存在");
                }
            }
        } else {
            if (StringUtils.isEmpty(userInfoId)) {
                if (isCheck) {
                    throw new AnnotationException("token失效");
                }
            }
        }
        checkUserInfo(isCheck, req, userInfoId);
    }

    private void checkUserInfo(boolean isCheck, HttpServletRequest req, String userInfoId) {
        UserInfo userInfo = redisUtils.get(Const.USER_INFO + userInfoId, UserInfo.class, tokenTimeOut);
        if (userInfo != null) {
            // 刷新缓存
            redisUtils.get(Const.USER_INFO + userInfoId, tokenTimeOut);
            req.setAttribute(Const.CURRENT_USER, userInfo);
        } else {
            if (isCheck) {
                throw new NoAuthException("登录失效,请重新登录");
            }
        }
    }
}
