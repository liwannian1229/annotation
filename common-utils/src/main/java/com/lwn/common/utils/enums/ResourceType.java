package com.lwn.common.utils.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResourceType {

    // 两个实例
    SOURCE("source", 0), CLASS("class", 1);

    // 两个属性
    private final String description;
    private final int intValue;

    // 一个构造器
    ResourceType(String description, int intValue) {
        this.description = description;
        this.intValue = intValue;
    }

    // 两个拿值方法

    @JsonValue
    public String getDescription() {
        return description;
    }

    @JsonValue
    public int getIntValue() {
        return intValue;
    }
}
