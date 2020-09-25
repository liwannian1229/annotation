package com.lwn.exception;

public class InvokeException extends AnnotationException {

    public InvokeException(String message) {
        super(message);
    }

    public InvokeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
