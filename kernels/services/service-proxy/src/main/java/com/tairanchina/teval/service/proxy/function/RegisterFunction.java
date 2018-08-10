package com.tairanchina.teval.service.proxy.function;

import com.tairanchina.teval.service.proxy.GlobalContainer;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

public class RegisterFunction extends AbsProxyFunction<ServerInfo, Void> {

    private static final String FLAG_AUTH_FETCH_PATH = "/proxy/register";

    private static final RegisterFunction INSTANCE = new RegisterFunction();
    private static final int REPORT_PERIODIC_MS = 10000;

    public static RegisterFunction inst() {
        return INSTANCE;
    }

    @Override
    protected void apply(ServerInfo serverInfo, Vertx vertx, Future<Void> resultFuture) {
        vertx.setPeriodic(REPORT_PERIODIC_MS, event -> GlobalContainer.httpClient.getAbs(serverInfo.getServerUrl() + FLAG_AUTH_FETCH_PATH
                        + "?proxyId=" + serverInfo.getProxyId()
                        + "&proxySecret=" + serverInfo.getProxySecret(),
                ar -> {
                    if (ar.statusCode() != 200) {
                        resultFuture.fail("Register Proxy Instance Error [" + ar.statusCode() + "] " + ar.statusMessage());
                    }
                }).end());

    }

}
