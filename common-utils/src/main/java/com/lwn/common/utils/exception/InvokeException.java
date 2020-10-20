package com.lwn.common.utils.exception;

public class InvokeException extends AnnotationException {

    public InvokeException(String message) {
        super(message);
    }

    public InvokeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
