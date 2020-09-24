package com.lwn.exception;

/**
 * @author liwannian
 * @date 2020年9月11日17点58分17点58分
 */
public class AnnotationException extends RuntimeException {

    public AnnotationException(String message) {
        super(message);
    }

    public AnnotationException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
