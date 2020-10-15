package com.lwn.my.service.cache;

import com.lwn.my.service.aop.RedisCacheAspect;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * <p>
 * 该注解用于缓存方法返回的数据，key支持spel表达式
 * 可指定缓存过期时间和redis文档目录
 * <p>
 * 如果从缓存中拿不到或者出现超时，异常等情况则从方法中拿
 * <p>
 * 注意，方法的返回值不建议使用基本数据类型，建议使用包装类
 *
 * @see RedisCacheAspect
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisCache {

    /**
     * 注解在方法上，将方法的返回值进行缓存，key为redis的key，值为json
     * expire为缓存过期时间，当缓存重新存入时，则缓存时间会重置
     * 如果设置resetExpire为false的情况下，如果缓存中存在该缓存，则直接更新数据而不会重置时间。
     * condition, resetExpire 暂时无法使用
     */


    @AliasFor("key")
    String value() default "";

    /**
     * 缓存超时时间，单位为 秒
     *
     * @return
     */
    long expire() default -1;

    String key() default "";

    @Deprecated
    String condition() default "";

    @Deprecated
    boolean resetExpire() default true;
}

