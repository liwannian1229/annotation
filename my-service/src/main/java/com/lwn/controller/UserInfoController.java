package com.lwn.controller;

import com.lwn.annotation.TokenValidator;
import com.lwn.model.ro.UserInfoRo;
import com.lwn.model.vo.LoginVo;
import com.lwn.response.ResponseResult;
import com.lwn.token.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

/**
 * @author liwannian
 * @date 2020年9月11日17点58分17点58分
 */
@Slf4j
@RestController
@RequestMapping(value = "/userInfo")
@Api(value = "hello相关")
public class UserInfoController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "登录获取token")
    @PostMapping("/login")
    public ResponseResult<LoginVo> login(@Validated @RequestBody UserInfoRo ro) {

        return ResponseResult.successResult(userService.doLogin(ro));
    }

    @ApiOperation(value = "注册用户")
    @PostMapping("/register")
    public ResponseResult<String> register(@Validated @RequestBody UserInfoRo ro) {

        userService.createUser(ro);

        return ResponseResult.successResult("注册成功!");
    }

    @TokenValidator
    @GetMapping("/loginOut")
    public ResponseResult<String> loginOut() {

        userService.loginOut();

        return ResponseResult.successResult("退出成功!");
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
