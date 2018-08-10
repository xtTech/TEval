package com.tairanchina.teval.common.dto;

import java.util.List;
import java.util.Set;

public class ProxyConfig {

    private static final int DEFAULT_MAX_HEADER_SIZE = 16 * 1024;
    private static final int DEFAULT_MAX_POOL_SIZE = 5;
    private static final int DEFAULT_CONNECT_TIMEOUT_MS = 60000;

    private Set<String> serviceIds;
    private HttpConfig http;
    private RedisConfig redis;
    private List<PluginConfig> plugins;

    private long apiVersion;

    public Set<String> getServiceIds() {
        return serviceIds;
    }

    public ProxyConfig setServiceIds(Set<String> serviceIds) {
        this.serviceIds = serviceIds;
        return this;
    }

    public HttpConfig getHttp() {
        return http;
    }

    public ProxyConfig setHttp(HttpConfig http) {
        this.http = http;
        return this;
    }

    public RedisConfig getRedis() {
        return redis;
    }

    public ProxyConfig setRedis(RedisConfig redis) {
        this.redis = redis;
        return this;
    }

    public List<PluginConfig> getPlugins() {
        return plugins;
    }

    public ProxyConfig setPlugins(List<PluginConfig> plugins) {
        this.plugins = plugins;
        return this;
    }

    public long getApiVersion() {
        return apiVersion;
    }

    public ProxyConfig setApiVersion(long apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }

    public static class HttpConfig {
        private int maxHeaderSize = DEFAULT_MAX_HEADER_SIZE;
        private int maxPoolSize = DEFAULT_MAX_POOL_SIZE;
        private int connectTimeoutMs = DEFAULT_CONNECT_TIMEOUT_MS;
        private int proxyPort;
        private int managementPort;

        public int getMaxHeaderSize() {
            return maxHeaderSize;
        }

        public HttpConfig setMaxHeaderSize(int maxHeaderSize) {
            this.maxHeaderSize = maxHeaderSize;
            return this;
        }

        public int getMaxPoolSize() {
            return maxPoolSize;
        }

        public HttpConfig setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
            return this;
        }

        public int getConnectTimeoutMs() {
            return connectTimeoutMs;
        }

        public HttpConfig setConnectTimeoutMs(int connectTimeoutMs) {
            this.connectTimeoutMs = connectTimeoutMs;
            return this;
        }

        public int getProxyPort() {
            return proxyPort;
        }

        public HttpConfig setProxyPort(int proxyPort) {
            this.proxyPort = proxyPort;
            return this;
        }

        public int getManagementPort() {
            return managementPort;
        }

        public HttpConfig setManagementPort(int managementPort) {
            this.managementPort = managementPort;
            return this;
        }
    }

    public static class RedisConfig {
        private String host;
        private int port;
        private String auth;

        public String getHost() {
            return host;
        }

        public RedisConfig setHost(String host) {
            this.host = host;
            return this;
        }

        public int getPort() {
            return port;
        }

        public RedisConfig setPort(int port) {
            this.port = port;
            return this;
        }

        public String getAuth() {
            return auth;
        }

        public RedisConfig setAuth(String auth) {
            this.auth = auth;
            return this;
        }
    }

    public static class PluginConfig {

        public static final String STRATEGY_IGNORE_AND_NEXT = "IGNORE_AND_NEXT";
        // TODO
        public static final String STRATEGY_RETURN_ERROR = "RETURN_ERROR";
        // TODO
        public static final String STRATEGY_RETRY_ONCE = "RETRY_ONCE";

        private static final int DEFAULT_MAX_FAILURE_TIMES = 5;
        private static final int DEFAULT_EXECT_TIMEOUT_MS = 2000;
        private static final int DEFAULT_RESET_TIMEOUT_MS = 30000;

        private String code;
        private String errorStrategy = STRATEGY_IGNORE_AND_NEXT;
        private Object args;

        private int maxFailureTimes = DEFAULT_MAX_FAILURE_TIMES;
        private int execTimeoutMs = DEFAULT_EXECT_TIMEOUT_MS;
        private int resetTimeoutMs = DEFAULT_RESET_TIMEOUT_MS;

        public String getCode() {
            return code;
        }

        public PluginConfig setCode(String code) {
            this.code = code;
            return this;
        }

        public String getErrorStrategy() {
            return errorStrategy;
        }

        public PluginConfig setErrorStrategy(String errorStrategy) {
            this.errorStrategy = errorStrategy;
            return this;
        }

        public Object getArgs() {
            return args;
        }

        public PluginConfig setArgs(Object args) {
            this.args = args;
            return this;
        }

        public int getMaxFailureTimes() {
            return maxFailureTimes;
        }

        public PluginConfig setMaxFailureTimes(int maxFailureTimes) {
            this.maxFailureTimes = maxFailureTimes;
            return this;
        }

        public int getExecTimeoutMs() {
            return execTimeoutMs;
        }

        public PluginConfig setExecTimeoutMs(int execTimeoutMs) {
            this.execTimeoutMs = execTimeoutMs;
            return this;
        }

        public int getResetTimeoutMs() {
            return resetTimeoutMs;
        }

        public PluginConfig setResetTimeoutMs(int resetTimeoutMs) {
            this.resetTimeoutMs = resetTimeoutMs;
            return this;
        }
    }

}
