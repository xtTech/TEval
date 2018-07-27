package com.tairanchina.teval.service.proxy.function;

import com.tairanchina.teval.common.dto.ProxyConfig;
import com.tairanchina.teval.service.proxy.GlobalContainer;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.healthchecks.Status;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

public class SetRedisClientFunction extends AbsProxyFunction<ProxyConfig.RedisConfig, Void> {

    private static final SetRedisClientFunction INSTANCE = new SetRedisClientFunction();

    public static SetRedisClientFunction inst() {
        return INSTANCE;
    }

    @Override
    protected void apply(ProxyConfig.RedisConfig input, Vertx vertx, Future<Void> resultFuture) {
        GlobalContainer.redisClient = RedisClient.create(vertx, new RedisOptions()
                .setHost(input.getHost())
                .setPort(input.getPort())
                .setAuth(input.getAuth()));
        GlobalContainer.healthChecks.register("redis", future ->
                GlobalContainer.redisClient.echo("ha", ar -> {
                    if (ar.failed() || !"ha".equals(ar.result())) {
                        logger.error("Redis Client Error.", ar.cause());
                        future.complete(Status.KO());
                    } else {
                        future.complete(Status.OK());
                    }
                }));
        resultFuture.complete();
    }

}
