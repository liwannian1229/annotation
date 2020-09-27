package com.lwn.model.ro;

import com.lwn.request.PageCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "人类信息")
public class PeopleRo extends PageCondition {

    @ApiModelProperty(value = "查询关键字", example = "快乐")
    private String name;
}
