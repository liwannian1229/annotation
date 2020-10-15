package com.lwn.my.service.model.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "修改信息")
public class UpdatePeopleRo extends AddPeopleRo {

    @ApiModelProperty(value = "修改人类名称", example = "快乐")
    @NotNull(message = "修改人类的id不能为空")
    private Long id;
}
