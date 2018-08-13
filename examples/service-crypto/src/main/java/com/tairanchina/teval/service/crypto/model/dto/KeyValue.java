package com.tairanchina.teval.service.crypto.model.dto;

/**
 * 说明:
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/12.
 */
public class KeyValue {

    private String key;
    private String value;

    public KeyValue() {}

    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public KeyValue setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public KeyValue setValue(String value) {
        this.value = value;
        return this;
    }
}
