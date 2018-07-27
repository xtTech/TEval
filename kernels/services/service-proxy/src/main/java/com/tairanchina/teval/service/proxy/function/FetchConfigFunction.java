package com.tairanchina.teval.service.proxy.function;

import com.tairanchina.teval.common.dto.ProxyConfig;
import com.tairanchina.teval.plugin.template.proxy.filter.ProxyFilter;
import com.tairanchina.teval.service.proxy.GlobalContainer;
import com.tairanchina.teval.service.proxy.handler.FilterHandler;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;

public class FetchConfigFunction extends AbsProxyFunction<ServerInfo, Void> {

    private static final String FLAG_CONFIG_FETCH_PATH = "/proxy/config";

    private static final FetchConfigFunction INSTANCE = new FetchConfigFunction();
    private static final String FLAG_FILTER_PATH = "com.tairanchina.teval.plugin.proxy.filter.";

    public static FetchConfigFunction inst() {
        return INSTANCE;
    }

    @Override
    protected void apply(ServerInfo serverInfo, Vertx vertx, Future<Void> resultFuture) throws MalformedURLException {
        URL url = new URL("http://" + serverInfo.getServerUrl());
        ConfigStoreOptions httpStore = new ConfigStoreOptions()
                .setType("http")
                .setConfig(new JsonObject()
                        .put("host", url.getHost())
                        .put("port", url.getPort() == -1 ? 80 : url.getPort())
                        .put("path", url.getPath() + FLAG_CONFIG_FETCH_PATH + "?proxyId=" + serverInfo.getProxyId() + "&proxySecret=" + serverInfo.getProxySecret()));
        ConfigRetriever retriever = ConfigRetriever.create(vertx, new ConfigRetrieverOptions().addStore(httpStore));
        retriever.listen(change -> {
            logger.info("Proxy Config was changed, reload config & apis.");
            // 配置有变更，重新获取APIs
            FetchAPIsFunction.inst().apply(serverInfo, vertx);
            GlobalContainer.proxyConfig = change.getNewConfiguration().mapTo(ProxyConfig.class);
            setFilterTaskHandlers(GlobalContainer.proxyConfig.getFilters(), vertx);
        });
        ConfigRetriever.getConfigAsFuture(retriever)
                .setHandler(ar -> {
                    if (ar.failed()) {
                        resultFuture.fail(ar.cause());
                    } else {
                        try {
                            GlobalContainer.proxyConfig = ar.result().mapTo(ProxyConfig.class);
                            setFilterTaskHandlers(GlobalContainer.proxyConfig.getFilters(), vertx);
                            resultFuture.complete();
                        } catch (IllegalArgumentException e) {
                            logger.error("Config parse error.", e);
                            resultFuture.fail(e);
                        }
                    }
                });
    }

    private void setFilterTaskHandlers(Map<String, ProxyConfig.FilterConfig> filterTaskConfig, Vertx vertx) {
        GlobalContainer.filterHandlers = filterTaskConfig.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, i -> {
                    ProxyFilter proxyFilter = null;
                    try {
                        proxyFilter = (ProxyFilter) Class.forName(FLAG_FILTER_PATH + i.getValue().getId() + ".PluginFilter").newInstance();
                    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                        logger.error("Load Filter Task Error " + FLAG_FILTER_PATH + i.getValue().getId() + ".PluginFilter", e);
                        System.exit(1);
                    }
                    return new FilterHandler(proxyFilter, i.getValue(), vertx);
                }));
    }


}
