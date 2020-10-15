package com.lwn.my.service.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "登陆返回信息")
public class LoginVo {

    private String token;

    private String loginStatusCode;

    private String errorMessage;

    public LoginVo(String token, String loginStatusCode, String errorMessage) {

        this.token = token;

        this.loginStatusCode = loginStatusCode;

        this.errorMessage = errorMessage;
    }
}
