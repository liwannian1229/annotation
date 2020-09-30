package com.lwn.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ApiCode {

    VALIDATION_ERROR("error"), VALIDATION_SUCCESS("success");

    private final String value;

    ApiCode(String value) {

        this.value = value;
    }

    @JsonValue
    public String getValue() {

        return value;
    }


}
