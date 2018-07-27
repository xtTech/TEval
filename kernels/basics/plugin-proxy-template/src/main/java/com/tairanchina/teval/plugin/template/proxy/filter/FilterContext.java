package com.tairanchina.teval.plugin.template.proxy.filter;

import com.tairanchina.teval.common.dto.APIConfig;

public class FilterContext {

    private String appId;
    private APIConfig apiConfig;
    private String traceId;
    private long createTime;
    private FilterRequest request;

    public String getAppId() {
        return appId;
    }

    public FilterContext setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public APIConfig getApiConfig() {
        return apiConfig;
    }

    public FilterContext setApiConfig(APIConfig apiConfig) {
        this.apiConfig = apiConfig;
        return this;
    }

    public String getTraceId() {
        return traceId;
    }

    public FilterContext setTraceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    public long getCreateTime() {
        return createTime;
    }

    public FilterContext setCreateTime(long createTime) {
        this.createTime = createTime;
        return this;
    }

    public FilterRequest getRequest() {
        return request;
    }

    public FilterContext setRequest(FilterRequest request) {
        this.request = request;
        return this;
    }
}
