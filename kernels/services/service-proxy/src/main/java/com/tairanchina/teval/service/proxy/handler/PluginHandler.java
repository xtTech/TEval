package com.tairanchina.teval.service.proxy.handler;

import com.tairanchina.teval.common.dto.ProxyConfig;
import com.tairanchina.teval.plugin.template.proxy.filter.PluginContext;
import com.tairanchina.teval.plugin.template.proxy.filter.ProxyPlugin;
import com.tairanchina.teval.service.proxy.GlobalContainer;
import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.circuitbreaker.CircuitBreakerState;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.healthchecks.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginHandler {

    private static final Logger logger = LoggerFactory.getLogger(PluginHandler.class);

    private CircuitBreaker breaker;
    private ProxyPlugin<?> proxyPlugin;
    private ProxyConfig.PluginConfig config;

    public PluginHandler(ProxyPlugin<?> proxyPlugin, ProxyConfig.PluginConfig config, Vertx vertx) {
        this.proxyPlugin = proxyPlugin;
        this.config = config;
        proxyPlugin.innerInit(config.getArgs());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> proxyPlugin.innerDestroy(config.getArgs())));
        breaker = CircuitBreaker
                .create("plugin-" + config.getCode() + "-circuit-breaker", vertx,
                        new CircuitBreakerOptions()
                                .setMaxFailures(config.getMaxFailureTimes())
                                .setTimeout(config.getExecTimeoutMs())
                                .setResetTimeout(config.getResetTimeoutMs())
                                .setFallbackOnFailure(true))
                .openHandler(v -> logger.error("Circuit [" + config.getCode() + "] opened"))
                .halfOpenHandler(v -> logger.warn("Circuit [" + config.getCode() + "] half opened"))
                .closeHandler(v -> logger.info("Circuit [" + config.getCode() + "] closed"));
        GlobalContainer.healthChecks.register("filters/" + config.getCode(), future -> {
            if (breaker.state() == CircuitBreakerState.CLOSED) {
                future.complete(Status.OK());
            } else {
                future.complete(Status.KO().setData(new JsonObject().put("status", breaker.state().toString())));
            }
        });
    }

    public Future<PluginContext> request(PluginContext<?> context) {
        Future<PluginContext> future = Future.future();
        breaker.execute(
                cbFuture -> proxyPlugin.request(context).whenComplete((r, e) -> {
                    if (r) {
                        cbFuture.complete(context);
                    } else {
                        cbFuture.fail(e);
                    }
                }))
                .setHandler(ar -> {
                    if (ar.succeeded()) {
                        future.complete(context);
                    } else {
                        if (config.getErrorStrategy().equals(ProxyConfig.PluginConfig.STRATEGY_IGNORE_AND_NEXT)) {
                            logger.warn("ProxyPlugin [" + config.getCode() + "] " + context.getServiceId() + " # " + context.getRequest().toString() + " Execute Error, IGNORE it.");
                            future.complete(context);
                        } else {
                            future.fail(ar.cause());
                        }
                    }
                });
        return future;
    }

    public Future<PluginContext> response(PluginContext<?> context) {
        Future<PluginContext> future = Future.future();
        breaker.execute(
                cbFuture -> proxyPlugin.response(context).whenComplete((r, e) -> cbFuture.complete(context)))
                .setHandler(ar -> future.complete(context));
        return future;
    }

    public String getPluginCode() {
        return config.getCode();
    }

    public ProxyPlugin<?> getProxyPlugin() {
        return proxyPlugin;
    }
}
