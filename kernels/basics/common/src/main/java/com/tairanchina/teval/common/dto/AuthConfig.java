package com.tairanchina.teval.common.dto;

public class AuthConfig {

    private String appId;
    private String appSecret;

    public String getAppId() {
        return appId;
    }

    public AuthConfig setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public AuthConfig setAppSecret(String appSecret) {
        this.appSecret = appSecret;
        return this;
    }
}
