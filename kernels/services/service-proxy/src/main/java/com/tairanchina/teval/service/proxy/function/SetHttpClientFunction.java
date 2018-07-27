package com.tairanchina.teval.service.proxy.function;

import com.tairanchina.teval.common.dto.ProxyConfig;
import com.tairanchina.teval.service.proxy.GlobalContainer;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClientOptions;

public class SetHttpClientFunction extends AbsProxyFunction<ProxyConfig.HttpConfig, Void> {

    private static final SetHttpClientFunction INSTANCE = new SetHttpClientFunction();

    public static SetHttpClientFunction inst() {
        return INSTANCE;
    }

    @Override
    protected void apply(ProxyConfig.HttpConfig input, Vertx vertx, Future<Void> resultFuture) {
        GlobalContainer.httpClient = vertx.createHttpClient(new HttpClientOptions()
                .setMaxHeaderSize(input.getMaxHeaderSize())
                .setMaxPoolSize(input.getMaxPoolSize())
                .setMaxRedirects(Integer.MAX_VALUE)
                .setConnectTimeout(input.getConnectTimeoutMs()));
        resultFuture.complete();
    }

}
