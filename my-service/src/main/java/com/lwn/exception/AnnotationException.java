package com.lwn.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liwannian
 * @date 2020年9月11日17点58分17点58分
 */
@Slf4j
public class AnnotationException extends RuntimeException {

    public AnnotationException(String message) {
        super(message);
        log.error(message);
    }

    public AnnotationException(String message, Throwable throwable) {
        super(message, throwable);
        log.error(message);
    }

}
