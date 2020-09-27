package com.lwn.request;

import com.lwn.enumeration.OrderType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@ApiModel(value = "分页信息")
public class PageCondition {

    @ApiModelProperty(value = "页码", example = "1", dataType = "int")
    @Min(value = 1, message = "页码最小为1")
    private int pageIndex = 1;

    @ApiModelProperty(value = "页容量", example = "10", dataType = "int")
    @Min(value = 1, message = "页容量最小为1")
    private int pageSize = 10;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段", example = "id,name,createDate", dataType = "String")
    private String sort = "";

    /**
     * 排序方式
     */
    @ApiModelProperty(value = "排序方式", example = "desc", dataType = "String")
    private OrderType orderType;
}
