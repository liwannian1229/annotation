package com.lwn.testClass;

import cn.hutool.core.convert.Converter;

// HuTool 自定义的转换器
public class CustomConverter implements Converter<String> {

    @Override
    public String convert(Object value, String defaultValue) throws IllegalArgumentException {

        return "Custom:" + value.toString();
    }
}
