package com.lwn.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderType {

    DESC("desc"), ASC("asc");

    private String value;

    OrderType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

}
