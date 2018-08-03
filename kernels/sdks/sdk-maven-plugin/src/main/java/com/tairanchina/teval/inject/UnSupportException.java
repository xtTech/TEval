package com.tairanchina.teval.inject;

public class UnSupportException extends RuntimeException {

    public UnSupportException(String type) {
        super("Teval do NOT support [" + type + "]");
    }
}
