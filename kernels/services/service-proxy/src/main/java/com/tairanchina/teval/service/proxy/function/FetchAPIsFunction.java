package com.tairanchina.teval.service.proxy.function;

import com.tairanchina.teval.common.dto.APIConfig;
import com.tairanchina.teval.service.proxy.GlobalContainer;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.stream.Collectors;

public class FetchAPIsFunction extends AbsProxyFunction<ServerInfo, Void> {

    private static final String FLAG_APIS_FETCH_PATH = "/proxy/apis";

    private static final FetchAPIsFunction INSTANCE = new FetchAPIsFunction();

    public static FetchAPIsFunction inst() {
        return INSTANCE;
    }

    @Override
    protected void apply(ServerInfo serverInfo, Vertx vertx, Future<Void> resultFuture) {
        GlobalContainer.httpClient.getAbs("http://" + serverInfo.getServerUrl() + FLAG_APIS_FETCH_PATH + "?proxyId=" + serverInfo.getProxyId() + "&proxySecret=" + serverInfo.getProxySecret(), ar -> {
            if (ar.statusCode() != 200) {
                resultFuture.fail("Fetch APIs Error [" + ar.statusCode() + "] " + ar.statusMessage());
            } else {
                ar.bodyHandler(h -> {
                    try {
                        GlobalContainer.apiConfig = h.toJsonArray()
                                .stream()
                                .map(i -> ((JsonObject) i).mapTo(APIConfig.class))
                                .collect(Collectors.toList());
                        resultFuture.complete();
                    } catch (IllegalArgumentException e) {
                        logger.error("APIs parse error.", e);
                        resultFuture.fail(e);
                    }
                });
            }
        }).end();
    }

}
