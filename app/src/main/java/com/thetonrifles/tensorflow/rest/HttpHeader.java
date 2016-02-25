package com.thetonrifles.tensorflow.rest;

import java.io.Serializable;

public class HttpHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    private String key;
    private String value;

    public HttpHeader(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}