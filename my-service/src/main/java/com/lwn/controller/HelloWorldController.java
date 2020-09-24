package com.lwn.controller;

import com.lwn.annotation.MyAnnotation;
import com.lwn.annotation.MyAnnotation_1;
import com.lwn.token.TokenService;
import common.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import response.ResponseResult;

/**
 * @author liwannian
 * @date 2020年9月11日17点58分17点58分
 */
@Slf4j
@RestController
public class HelloWorldController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/getToken")
    public ResponseResult<String> getToken() {

        return ResponseResult.successResult(tokenService.createToken());
    }

    @PostMapping("/hello")
    @MyAnnotation
    public ResponseResult<StringBuilder> hello(@Param("num") int num) {

        return ResponseResult.successResult(CommonUtil.output(num));
    }

    @PostMapping("/hello1")
    @MyAnnotation_1(false)
    public ResponseResult<String> hello1() {

        return ResponseResult.successResult("访问hello1成功", null);
    }

    @PostMapping("/hello2")
    @MyAnnotation_1
    public ResponseResult<String> hello2() {

        log.debug("debug");// 没有配置日志文件 默认日志级别为info,低于info的debug不会显示
        log.info("info");
        log.warn("warn");
        log.error("error");
        log.trace("trace");

        return ResponseResult.successResult("访问hello2成功", null);

    }

}

