package com.lwn.my.service.exception;

public class NotFoundException extends AnnotationException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
