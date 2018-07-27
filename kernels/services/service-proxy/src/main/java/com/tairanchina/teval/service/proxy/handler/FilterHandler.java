package com.tairanchina.teval.service.proxy.handler;

import com.tairanchina.teval.common.dto.ProxyConfig;
import com.tairanchina.teval.plugin.template.proxy.filter.FilterContext;
import com.tairanchina.teval.plugin.template.proxy.filter.ProxyFilter;
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

public class FilterHandler {

    private static final Logger logger = LoggerFactory.getLogger(FilterHandler.class);

    private CircuitBreaker breaker;
    private ProxyFilter proxyFilter;
    private ProxyConfig.FilterConfig config;

    public FilterHandler(ProxyFilter proxyFilter, ProxyConfig.FilterConfig config, Vertx vertx) {
        this.proxyFilter = proxyFilter;
        this.config = config;
        proxyFilter.init(config.getArgs());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> proxyFilter.destroy(config.getArgs())));
        breaker = CircuitBreaker.create("task-" + config.getId() + "-circuit-breaker", vertx,
                new CircuitBreakerOptions()
                        .setMaxFailures(config.getMaxFailureTimes())
                        .setTimeout(config.getExecTimeoutMs())
                        .setResetTimeout(config.getResetTimeoutMs())
                        .setFallbackOnFailure(true)
        ).openHandler(v -> logger.error("Circuit [" + config.getId() + "] opened")).halfOpenHandler(v -> {
            logger.warn("Circuit [" + config.getId() + "] half opened");
        }).closeHandler(v -> logger.info("Circuit [" + config.getId() + "] closed"));
        GlobalContainer.healthChecks.register("filters/" + config.getId(), future -> {
            if (breaker.state() == CircuitBreakerState.CLOSED) {
                future.complete(Status.OK());
            } else {
                future.complete(Status.KO().setData(new JsonObject().put("status", breaker.state().toString())));
            }
        });
    }

    public Future<FilterContext> request(FilterContext context) {
        Future<FilterContext> future = Future.future();
        breaker.execute(
                cbFuture -> proxyFilter.request(context).whenComplete((r, e) -> {
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
                        if (config.getErrorStrategy().equals(ProxyConfig.FilterConfig.STRATEGY_IGNORE_AND_NEXT)) {
                            logger.warn("ProxyFilter [" + config.getId() + "] " + context.getAppId() + context.getRequest().toString() + " Execute Error, IGNORE it.");
                            future.complete(context);
                        } else {
                            future.fail(ar.cause());
                        }
                    }
                });
        return future;
    }

    public Future<FilterContext> response(FilterContext context) {
        Future<FilterContext> future = Future.future();
        breaker.execute(
                cbFuture -> proxyFilter.response(context).whenComplete((r, e) -> cbFuture.complete(context)))
                .setHandler(ar -> future.complete(context));
        return future;
    }

}
