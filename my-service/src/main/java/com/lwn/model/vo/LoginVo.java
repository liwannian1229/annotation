package com.lwn.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "登陆返回信息")
public class LoginVo {

    private String token;

    private String loginStatusCode;

    public LoginVo(String token, String loginStatusCode) {

        this.token = token;
        
        this.loginStatusCode = loginStatusCode;
    }
}
