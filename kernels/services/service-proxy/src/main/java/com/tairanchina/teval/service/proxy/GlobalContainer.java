package com.tairanchina.teval.service.proxy;


import com.tairanchina.teval.common.dto.APIConfig;
import com.tairanchina.teval.common.dto.ProxyConfig;
import com.tairanchina.teval.service.proxy.handler.FilterHandler;
import io.vertx.core.http.HttpClient;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.healthchecks.HealthChecks;
import io.vertx.redis.RedisClient;

import java.util.List;
import java.util.Map;

public class GlobalContainer {

    public static SharedData sharedData;

    public static ProxyConfig proxyConfig;

    public static Map<String, FilterHandler> filterHandlers;

    public static List<APIConfig> apiConfig;

    public static RedisClient redisClient;

    public static HttpClient httpClient;

    public static HealthChecks healthChecks;

}
