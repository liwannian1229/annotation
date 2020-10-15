package com.lwn.my.service.exception;

public class TokenInValidException extends AnnotationException {
    
    public TokenInValidException(String message) {
        super(message);
    }

    public TokenInValidException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
