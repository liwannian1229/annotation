package com.lwn.model.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "增加信息")
public class AddPeopleRo {

    @ApiModelProperty(value = "新增人类名称", example = "快乐")
    @NotBlank(message = "人名不能为空")
    private String name;
}
