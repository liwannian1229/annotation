package com.lwn.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ResponseResult<T> {

    private String message;
    private String code;
    private boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 返回成功结果
     */
    public static <T> ResponseResult<T> successResult(T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.message = "";
        result.code = ResultCode.SUCCESS;
        result.success = true;
        result.data = data;

        return result;
    }

    /**
     * 返回成功结果
     */
    public static <T> ResponseResult<T> successResult(String message, T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.message = message;
        result.code = ResultCode.SUCCESS;
        result.success = true;
        result.data = data;

        return result;
    }

    /**
     * 返回失败结果
     */
    public static <T> ResponseResult<T> failureResult(String message, String code) {
        ResponseResult<T> result = new ResponseResult<>();
        result.message = message;
        result.code = code;
        result.success = false;

        return result;
    }

    /**
     * 返回失败结果
     */
    public static <T> ResponseResult<T> failureResult(String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.message = message;
        result.code = ResultCode.FAILURE;
        result.success = false;

        return result;
    }

    /**
     * 返回失败结果
     */
    public static <T> ResponseResult<T> errorResult(String message, Exception e) {
        log.error("error:" + e.getMessage());
        ResponseResult<T> result = new ResponseResult<>();
        result.message = message;
        result.code = ResultCode.EXCEPTION;
        result.success = false;

        return result;
    }

    /**
     * 返回失败结果
     */
    public static <T> ResponseResult<T> exceptionResult(String message, Exception e, String code) {
        log.error("error:" + e.getMessage());
        ResponseResult<T> result = new ResponseResult<>();
        result.message = message + "error:" + e.getMessage();
        result.code = code;
        result.success = false;

        return result;
    }
}
