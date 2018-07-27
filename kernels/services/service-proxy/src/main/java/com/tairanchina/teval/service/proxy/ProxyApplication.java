package com.tairanchina.teval.service.proxy;

import com.tairanchina.teval.service.proxy.function.*;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.healthchecks.HealthChecks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyApplication extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(ProxyApplication.class);

    private static final String FLAG_SERVER_URL = "serverUrl";
    private static final String FLAG_PROXY_ID = "proxyId";
    private static final String FLAG_PROXY_SECRET = "proxySecret";

    private static final String DEFAULT_SERVER_URL = "feval.tairanchina.com";
    private static final String DEFAULT_PROXY_ID = "default";

    @Override
    public void start(Future<Void> startFuture) {
        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory");
        System.setProperty("hazelcast.logging.type", "slf4j");

        logger.info("Starting Proxy.");

        GlobalContainer.sharedData = vertx.sharedData();
        GlobalContainer.healthChecks = HealthChecks.create(vertx);

        ServerInfo serverInfo = new ServerInfo()
                .setServerUrl(config().getString(FLAG_SERVER_URL, DEFAULT_SERVER_URL))
                .setProxyId(config().getString(FLAG_PROXY_ID, DEFAULT_PROXY_ID))
                .setProxySecret(config().getString(FLAG_PROXY_SECRET, ""));

        startFuture.compose(v -> FetchConfigFunction.inst().apply(serverInfo, vertx))
                .compose(v -> SetHttpClientFunction.inst().apply(GlobalContainer.proxyConfig.getHttp(), vertx))
                .compose(v -> FetchAPIsFunction.inst().apply(serverInfo, vertx))
                .compose(v -> StartMonitorFunction.inst().apply(GlobalContainer.proxyConfig.getHttp().getManagementPort(), vertx))
                .compose(v -> SetRedisClientFunction.inst().apply(GlobalContainer.proxyConfig.getRedis(), vertx))
                .compose(v -> StartProxyServiceFunction.inst().apply(GlobalContainer.proxyConfig.getHttp(), vertx))
                .setHandler(ar -> {
                    if (ar.failed()) {
                        logger.error("Startup Failure.", ar.cause());
                        startFuture.fail(ar.cause());
                        System.exit(1);
                    } else {
                        logger.info("Startup Successful.");
                    }
                });
    }

    public void stop(Future<Void> stopFuture) {
        logger.info("Stopping...");
        GlobalContainer.httpClient.close();
        GlobalContainer.redisClient.close(stopFuture);
    }

}
