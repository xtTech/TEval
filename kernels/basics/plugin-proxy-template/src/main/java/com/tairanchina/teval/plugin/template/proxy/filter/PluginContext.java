package com.tairanchina.teval.plugin.template.proxy.filter;

import com.tairanchina.teval.common.dto.APIConfig;

public class PluginContext<C> {

    private String traceId;
    private long createTime;
    private String serviceId;
    private C config;
    private PluginRequest request;
    // System fields, plug-ins used with caution
    private APIConfig _apiConfig;

    public PluginContext setInnerConfig(Object config) {
        return setConfig((C) config);
    }

    public String getTraceId() {
        return traceId;
    }

    public PluginContext setTraceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    public long getCreateTime() {
        return createTime;
    }

    public PluginContext setCreateTime(long createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getServiceId() {
        return serviceId;
    }

    public PluginContext setServiceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public C getConfig() {
        return config;
    }

    public PluginContext setConfig(C config) {
        this.config = config;
        return this;
    }

    public PluginRequest getRequest() {
        return request;
    }

    public PluginContext setRequest(PluginRequest request) {
        this.request = request;
        return this;
    }

    public APIConfig get_apiConfig() {
        return _apiConfig;
    }

    public PluginContext<C> set_apiConfig(APIConfig _apiConfig) {
        this._apiConfig = _apiConfig;
        return this;
    }
}
