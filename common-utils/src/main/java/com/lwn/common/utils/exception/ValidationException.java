package com.lwn.common.utils.exception;

public class ValidationException extends AnnotationException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
