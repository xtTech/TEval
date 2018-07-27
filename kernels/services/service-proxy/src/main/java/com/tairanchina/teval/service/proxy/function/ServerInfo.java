package com.tairanchina.teval.service.proxy.function;

public class ServerInfo {

    private String serverUrl;
    private String proxyId;
    private String proxySecret;

    public String getServerUrl() {
        return serverUrl;
    }

    public ServerInfo setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
        return this;
    }

    public String getProxyId() {
        return proxyId;
    }

    public ServerInfo setProxyId(String proxyId) {
        this.proxyId = proxyId;
        return this;
    }

    public String getProxySecret() {
        return proxySecret;
    }

    public ServerInfo setProxySecret(String proxySecret) {
        this.proxySecret = proxySecret;
        return this;
    }
}
