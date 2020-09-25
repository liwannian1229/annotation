package com.lwn.model.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "用户信息")
public class UserInfoRo {

    @ApiModelProperty(value = "用户id", example = "1")
    private Long id;

    @ApiModelProperty(value = "用户名", example = "lwn")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码", example = "123456")
    @NotBlank(message = "密码不能为空")
    private String password;
}
