package com.lwn.controller;

import com.lwn.annotation.MyAnnotation;
import com.lwn.annotation.MyAnnotation_1;
import common.CommonUtil;
import token.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liwannian
 * @date 2020年9月11日17点58分17点58分
 */
@RestController
@Slf4j
public class HelloWorld {

    @Autowired
    private TokenService tokenService;
    private Logger log1;

    @GetMapping("/getToken")
    public String getToken() {

        return tokenService.createToken();
    }

    @PostMapping("/hello")
    @MyAnnotation
    public StringBuilder hello(int num) {

        return CommonUtil.output(num);
    }

    @PostMapping("/hello1")
    @MyAnnotation_1(false)
    public String hello1() {

        return "访问hello1成功";
    }

    @PostMapping("/hello2")
    @MyAnnotation_1
    public String hello2() {

        log.debug("debug");// 没有配置日志文件 默认日志级别为info,低于info的debug不会显示
        log.info("info");
        log.warn("warn");
        log.error("error");
        log.trace("trace");

        return "hello2";
    }

}
