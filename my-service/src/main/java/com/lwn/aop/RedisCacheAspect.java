package com.lwn.aop;

import com.lwn.cache.CacheService;
import com.lwn.cache.RedisCache;
import com.lwn.cache.RedisCacheClear;
import com.lwn.common.CommonUtil;
import com.lwn.thread.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
@ConditionalOnBean(CacheService.class)
public class RedisCacheAspect {

    @Autowired
    private CacheService cacheService;

    /**
     * Around方法最先执行,优先级高于before
     *
     * @param pjp
     * @return
     * @throws Throwable
     * @annotation 是方法级别
     * @within 是对象级别
     */
    @Around("@annotation(com.lwn.cache.RedisCache)")
    public Object redisCache(ProceedingJoinPoint pjp) throws Throwable {
        log.debug("cache - redis");

        Object cacheResult;
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        RedisCache RedisCache = targetMethod.getAnnotation(RedisCache.class);
        if (RedisCache == null) {

            return pjp.proceed();
        }
        // 通过反射拿到方法的方法名和参数类型
        Method method = getMethod(pjp);
        Object[] args = pjp.getArgs();
        String key = CommonUtil.isEmpty(RedisCache.key()) ? RedisCache.value() : RedisCache.key();

        Object targetObj = pjp.getTarget();

        // 获得需要存储的key
        String cacheKey = parseKey(key, targetObj, method, args);
        // 拿到方法返回值类型
        Class<?> returnType = method.getReturnType();
        if (returnType.equals(void.class)) {

            return pjp.proceed();
        }
        try {
            cacheResult = cacheService.get(cacheKey, returnType);
        } catch (Exception e) {
            log.warn("缓存命中失败异常,key:" + cacheKey);
            log.error(e.getMessage(), e);

            return pjp.proceed();
        }
        if (cacheResult == null) {
            cacheResult = pjp.proceed();
            try {
                if (cacheResult != null) {
                    cacheService.set(cacheKey, cacheResult, RedisCache.expire());
                }
            } catch (Exception e) {
                log.warn("缓存命中失败异常,key:" + cacheKey);
                log.error(e.getMessage(), e);

                return cacheResult;
            }
        }

        return cacheResult;
    }

    @Around("@annotation(com.lwn.cache.RedisCacheClear)")
    public Object redisClean(ProceedingJoinPoint pjp) throws Throwable {

        log.debug("clear redis cache");
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        RedisCacheClear RedisCacheClear = targetMethod.getAnnotation(RedisCacheClear.class);
        if (RedisCacheClear != null) {
            Method method = getMethod(pjp);
            Object[] args = pjp.getArgs();
            String[] key = RedisCacheClear.key().length == 0 ? RedisCacheClear.value() : RedisCacheClear.key();
            String[] keys = RedisCacheClear.keys();
            Object targetObj = pjp.getTarget();
            try {
                if (key.length > 0) {
                    for (String item : key) {
                        String ckey = parseKey(item, targetObj, method, args);
                        if (RedisCacheClear.sync()) {
                            ThreadUtils.executeCachedThread(() -> cacheService.delete(ckey));
                        } else {
                            cacheService.delete(ckey);
                        }
                    }
                }
                if (keys.length > 0) {
                    for (String item : keys) {
                        String ckey = parseKey(item, targetObj, method, args);
                        if (RedisCacheClear.sync()) {
                            ThreadUtils.executeCachedThread(() -> cacheService.deleteKeys(ckey));
                        } else {
                            cacheService.deleteKeys(ckey);
                        }
                    }
                }
            } catch (Exception e) {
                log.warn("清理redis缓存失败异常");
                log.error(e.getMessage(), e);
            }
        }

        return pjp.proceed();
    }

    private Method getMethod(ProceedingJoinPoint pjp) throws NoSuchMethodException {

        //获取参数的类型
        Class[] argTypes = ((MethodSignature) pjp.getSignature()).getParameterTypes();

        return pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argTypes);

    }

    private String parseKey(String spEl, Object targetObj, Method method, Object[] args) {

        // SpEL表达式为空默认返回方法名
        if (CommonUtil.isEmpty(spEl)) {

            // 生成默认的key
            return defaultKey(method, args);
        }

        //获得被拦截方法参数列表
        LocalVariableTableParameterNameDiscoverer nd = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = nd.getParameterNames(method);
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }
        }
        context.setRootObject(targetObj);
        context.setVariable("this", targetObj);

        return parser.parseExpression(spEl).getValue(context, String.class);
    }

    private String defaultKey(Method method, Object[] args) {

        // 默认key为 包名+类名+方法名
        String defaultKey = method.getDeclaringClass().getPackage().getName() + method.getDeclaringClass().getName() + ":" + method.getName();
        StringBuilder builder = new StringBuilder(defaultKey);
        for (Object a : args) {
            builder.append(a.hashCode()).append("_");
        }

        return builder.toString();
    }
}
