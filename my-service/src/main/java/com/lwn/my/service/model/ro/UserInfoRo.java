package com.lwn.my.service.model.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ApiModel(value = "用户信息")
public class UserInfoRo {

    @ApiModelProperty(value = "用户名", example = "lwn")
    @Size(min = 1, max = 10, message = "用户名在1~10的字符")
    private String name;

    @ApiModelProperty(value = "密码", example = "12345678")
    @Size(min = 8, message = "密码至少8位起")
    private String password;

    @ApiModelProperty(value = "用户名", example = "lwn")
    @NotBlank(message = "验证码不能为空")
    private String captcha;
}
