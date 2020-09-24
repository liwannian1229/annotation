package com.lwn.request;

import com.lwn.enumeration.OrderType;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class PageCondition {

    @Min(value = 1, message = "页码最小为1")
    private int pageIndex = 1;

    @Min(value = 1, message = "页容量最小为1")
    private int pageSize = 10;

    /**
     * 值为类的字段
     */
    private String sort = "";

    /**
     * 排序方式
     */
    private OrderType orderType;
}
