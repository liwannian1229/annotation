package com.lwn.common.utils.config;

import com.lwn.common.utils.exception.AnnotationException;
import com.lwn.common.utils.exception.NoAuthException;
import com.lwn.common.utils.exception.TokenInValidException;
import com.lwn.common.utils.response.ResponseResult;
import com.lwn.common.utils.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionConfig implements WebMvcConfigurer {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ResponseResult<?>> exceptionHandler(Exception e) {
        if (e instanceof AnnotationException) {
            log.error(e.getMessage(), e);
            if (e instanceof TokenInValidException) {
                return new ResponseEntity<>(ResponseResult.failureResult(e.getMessage(), ResultCode.TOKEN_INVALID), HttpStatus.OK);
            }
            if (e instanceof NoAuthException) {
                return new ResponseEntity<>(ResponseResult.failureResult(e.getMessage(), ResultCode.NO_AUTH), HttpStatus.OK);
            }

            return new ResponseEntity<>(ResponseResult.failureResult(e.getMessage()), HttpStatus.OK);
        } else if (e instanceof MethodArgumentNotValidException) {
            log.warn(e.getMessage());
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
            List<ObjectError> allErrors = methodArgumentNotValidException.getBindingResult().getAllErrors();
            String message = "";
            if (!CollectionUtils.isEmpty(allErrors)) {
                message = allErrors.get(0).getDefaultMessage();
            }

            return new ResponseEntity<>(ResponseResult.failureResult(message), HttpStatus.OK);
        }

        log.error(e.getMessage());
        return new ResponseEntity<>(ResponseResult.errorResult("服务器异常", e), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
