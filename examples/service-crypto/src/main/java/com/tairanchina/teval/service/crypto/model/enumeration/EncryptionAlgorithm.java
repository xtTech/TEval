package com.tairanchina.teval.service.crypto.model.enumeration;

/**
 * 说明:
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/13.
 */
public enum EncryptionAlgorithm {

    SYMMETRIC_AES("AES"),
    SYMMETRIC_DES("DES"),

    MD5("md5");

    private String value;

    EncryptionAlgorithm(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public EncryptionAlgorithm setValue(String value) {
        this.value = value;
        return this;
    }
}
