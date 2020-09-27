package com.lwn.model.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ApiModel(value = "用户信息")
public class UserInfoRo {

    @ApiModelProperty(value = "用户名", example = "lwn")
    @NotBlank(message = "用户名不能为空")
    @Size(max = 10, message = "用户名长度不能过长")
    private String name;

    @ApiModelProperty(value = "密码", example = "12345678")
    @NotBlank(message = "密码不能为空")
    @Size(min = 8, message = "密码长度不能少于8位")
    private String password;
}
