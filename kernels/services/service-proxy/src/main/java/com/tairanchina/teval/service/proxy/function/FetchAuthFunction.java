package com.tairanchina.teval.service.proxy.function;

import com.tairanchina.teval.common.dto.AuthConfig;
import com.tairanchina.teval.service.proxy.GlobalContainer;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.function.Function;
import java.util.stream.Collectors;

public class FetchAuthFunction extends AbsProxyFunction<ServerInfo, Void> {

    private static final String FLAG_AUTH_FETCH_PATH = "/proxy/auth";

    private static final FetchAuthFunction INSTANCE = new FetchAuthFunction();

    public static FetchAuthFunction inst() {
        return INSTANCE;
    }

    @Override
    protected void apply(ServerInfo serverInfo, Vertx vertx, Future<Void> resultFuture) {
        GlobalContainer.httpClient.getAbs(serverInfo.getServerUrl() + FLAG_AUTH_FETCH_PATH + "?proxyId=" + serverInfo.getProxyId() + "&proxySecret=" + serverInfo.getProxySecret(),
                ar -> {
                    if (ar.statusCode() != 200) {
                        resultFuture.fail("Fetch Auth Error [" + ar.statusCode() + "] " + ar.statusMessage());
                    } else {
                        ar.bodyHandler(h -> {
                            try {
                                GlobalContainer.authConfig = h.toJsonArray()
                                        .stream()
                                        .map(i -> ((JsonObject) i).mapTo(AuthConfig.class))
                                        .collect(Collectors.toMap(AuthConfig::getAppId, Function.identity()));
                                resultFuture.complete();
                            } catch (IllegalArgumentException e) {
                                logger.error("Auth parse error.", e);
                                resultFuture.fail(e);
                            }
                        });
                    }
                }).end();
    }

}
