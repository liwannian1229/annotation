package com.lwn.controller;

import com.lwn.annotation.MyAnnotation;
import com.lwn.annotation.MyAnnotation_1;
import com.lwn.annotation.TokenValidator;
import com.lwn.common.CommonUtil;
import com.lwn.model.ro.UserInfoRo;
import com.lwn.response.ResponseResult;
import com.lwn.token.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author liwannian
 * @date 2020年9月11日17点58分17点58分
 */
@Slf4j
@RestController
@RequestMapping(value = "/hello")
@Api(value = "hello相关")
public class HelloWorldController {

    @Autowired
    private TokenService tokenService;

    @ApiOperation(value = "获取token")
    @PostMapping("/getToken")
    public ResponseResult<String> getToken(@RequestBody @Validated UserInfoRo ro) {

        return ResponseResult.successResult(tokenService.createToken(ro));
    }

    @TokenValidator
    @PostMapping("/hello")
    public ResponseResult<StringBuilder> hello(@RequestParam("num") int num) {

        return ResponseResult.successResult(CommonUtil.output(num));
    }

    @PostMapping("/hello1")
    @TokenValidator(false)
    public ResponseResult<String> hello1() {

        return ResponseResult.successResult("访问hello1成功", null);
    }

    @PostMapping("/hello2")
    @TokenValidator
    public ResponseResult<String> hello2() {

        log.debug("debug");// 没有配置日志文件 默认日志级别为info,低于info的debug不会显示
        log.info("info");
        log.warn("warn");
        log.error("error");
        log.trace("trace");

        return ResponseResult.successResult("访问hello2成功", null);

    }

}

