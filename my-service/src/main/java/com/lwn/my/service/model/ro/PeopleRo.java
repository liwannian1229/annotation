package com.lwn.my.service.model.ro;

import com.lwn.common.utils.request.PageCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "人类entity")
public class PeopleRo extends PageCondition {

    @ApiModelProperty(value = "查询关键字", example = "快乐")
    private String name;
    // @Valid 注解用于作为属性的实体类,嵌套验证
}
