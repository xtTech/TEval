package com.tairanchina.teval.common.dto;

import java.util.HashMap;
import java.util.Map;

public class ProxyConfig {

    private static final int DEFAULT_MAX_HEADER_SIZE = 16 * 1024;
    private static final int DEFAULT_MAX_POOL_SIZE = 5;
    private static final int DEFAULT_CONNECT_TIMEOUT_MS = 60000;

    private HttpConfig http;
    private RedisConfig redis;
    private Map<String, FilterConfig> filters;

    private long apiVersion;

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

    public Map<String, FilterConfig> getFilters() {
        return filters;
    }

    public ProxyConfig setFilters(Map<String, FilterConfig> filters) {
        this.filters = filters;
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

    public static class FilterConfig {

        public static final String STRATEGY_IGNORE_AND_NEXT = "IGNORE_AND_NEXT";
        // TODO
        public static final String STRATEGY_RETURN_ERROR = "RETURN_ERROR";
        // TODO
        public static final String STRATEGY_RETRY_ONCE = "RETRY_ONCE";

        private static final int DEFAULT_MAX_FAILURE_TIMES = 5;
        private static final int DEFAULT_EXECT_TIMEOUT_MS = 2000;
        private static final int DEFAULT_RESET_TIMEOUT_MS = 30000;

        private String id;
        private String errorStrategy = STRATEGY_IGNORE_AND_NEXT;
        private Map<String, String> args = new HashMap<>();

        private int maxFailureTimes = DEFAULT_MAX_FAILURE_TIMES;
        private int execTimeoutMs = DEFAULT_EXECT_TIMEOUT_MS;
        private int resetTimeoutMs = DEFAULT_RESET_TIMEOUT_MS;

        public String getId() {
            return id;
        }

        public FilterConfig setId(String id) {
            this.id = id;
            return this;
        }

        public String getErrorStrategy() {
            return errorStrategy;
        }

        public FilterConfig setErrorStrategy(String errorStrategy) {
            this.errorStrategy = errorStrategy;
            return this;
        }

        public Map<String, String> getArgs() {
            return args;
        }

        public FilterConfig setArgs(Map<String, String> args) {
            this.args = args;
            return this;
        }

        public int getMaxFailureTimes() {
            return maxFailureTimes;
        }

        public FilterConfig setMaxFailureTimes(int maxFailureTimes) {
            this.maxFailureTimes = maxFailureTimes;
            return this;
        }

        public int getExecTimeoutMs() {
            return execTimeoutMs;
        }

        public FilterConfig setExecTimeoutMs(int execTimeoutMs) {
            this.execTimeoutMs = execTimeoutMs;
            return this;
        }

        public int getResetTimeoutMs() {
            return resetTimeoutMs;
        }

        public FilterConfig setResetTimeoutMs(int resetTimeoutMs) {
            this.resetTimeoutMs = resetTimeoutMs;
            return this;
        }
    }

}
