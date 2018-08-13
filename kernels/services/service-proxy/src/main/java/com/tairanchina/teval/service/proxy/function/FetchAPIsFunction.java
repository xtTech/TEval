package com.tairanchina.teval.service.proxy.function;

import com.ecfront.dew.common.$;
import com.tairanchina.teval.common.dto.APIConfig;
import com.tairanchina.teval.common.dto.ProxyConfig;
import com.tairanchina.teval.service.proxy.GlobalContainer;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FetchAPIsFunction extends AbsProxyFunction<ServerInfo, Void> {

    private static final String FLAG_APIS_FETCH_PATH = "/proxy/apis";

    private static final FetchAPIsFunction INSTANCE = new FetchAPIsFunction();

    public static FetchAPIsFunction inst() {
        return INSTANCE;
    }

    @Override
    protected void apply(ServerInfo serverInfo, Vertx vertx, Future<Void> resultFuture) {
        GlobalContainer.httpClient.getAbs(serverInfo.getServerUrl() + FLAG_APIS_FETCH_PATH + "?proxyId=" + serverInfo.getProxyId() + "&proxySecret=" + serverInfo.getProxySecret(),
                ar -> {
                    if (ar.statusCode() != 200) {
                        resultFuture.fail("Fetch APIs Error [" + ar.statusCode() + "] " + ar.statusMessage());
                    } else {
                        ar.bodyHandler(h -> {
                            try {
                                GlobalContainer.apiConfig = h.toJsonArray()
                                        .stream()
                                        .map(i -> ((JsonObject) i).mapTo(APIConfig.class))
                                        .collect(Collectors.toList());
                                sortPlugins(GlobalContainer.apiConfig);
                                resultFuture.complete();
                            } catch (IllegalArgumentException e) {
                                logger.error("APIs parse error.", e);
                                resultFuture.fail(e);
                            }
                        });
                    }
                }).end();
    }

    private void sortPlugins(List<APIConfig> apiConfig) {
        apiConfig.forEach(conf ->
                conf.setPlugins(GlobalContainer.proxyConfig.getPlugins()
                        .stream()
                        .filter(proxyConf -> conf.getPlugins().containsKey(proxyConf.getCode()))
                        .collect(Collectors.toMap(
                                ProxyConfig.PluginConfig::getCode,
                                proxyConf -> mergeConfig(proxyConf.getCode(), proxyConf.getArgs(), conf.getPlugins().get(proxyConf.getCode())),
                                (u, v) -> {
                                    throw new IllegalStateException(String.format("Duplicate key %s", u));
                                }, LinkedHashMap::new))
                ));
    }

    private Object mergeConfig(String proxyCode, Object proxyConfig, Object mapApiConfig) {
        // api config first, Merge api config to proxy config
        Object apiConfig = mapApiConfig == null
                ? proxyConfig : GlobalContainer.pluginHandlers.get(proxyCode).getProxyPlugin().parseConfig(mapApiConfig);
        try {
            $.bean.copyProperties(proxyConfig, apiConfig);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return proxyConfig;
    }

}
