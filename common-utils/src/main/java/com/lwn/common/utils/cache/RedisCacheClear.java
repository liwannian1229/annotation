package com.lwn.common.utils.cache;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisCacheClear {

    /**
     * key 为删除响应的key缓存，keys表示级联删除
     * {@example 如有某个key为example:123:keys的缓存， 当keys为example时将删除全部以example:开头的所有缓存}
     * key和keys都可以指定多个
     * <p>
     * condition暂时没有用到，所以暂时没有做实现。
     */

    @AliasFor("key")
    String[] value() default {};

    String[] key() default {};

    String[] keys() default {};

    boolean sync() default false;

    @Deprecated
    String condition() default "";
}
