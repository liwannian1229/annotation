package com.lwn.annotation;

import java.lang.annotation.*;

/**
 * @author liwannian
 * @date 2020年9月11日17点58分
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {

}
