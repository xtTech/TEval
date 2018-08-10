package com.tairanchina.teval.service.proxy.function;

import com.tairanchina.teval.common.dto.ProxyConfig;
import com.tairanchina.teval.plugin.template.proxy.filter.ProxyPlugin;
import com.tairanchina.teval.service.proxy.GlobalContainer;
import com.tairanchina.teval.service.proxy.handler.PluginHandler;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class FetchConfigFunction extends AbsProxyFunction<ServerInfo, Void> {

    private static final String FLAG_CONFIG_FETCH_PATH = "/proxy/config";

    private static final FetchConfigFunction INSTANCE = new FetchConfigFunction();
    private static final String FLAG_FILTER_PATH = "com.tairanchina.teval.plugin.proxy.filter.";

    public static FetchConfigFunction inst() {
        return INSTANCE;
    }

    @Override
    protected void apply(ServerInfo serverInfo, Vertx vertx, Future<Void> resultFuture) throws MalformedURLException {
        URL url = new URL(serverInfo.getServerUrl());
        // TODO proxySecret in header
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
            FetchAuthFunction.inst().apply(serverInfo, vertx);
            initOrModifyConfig(change.getNewConfiguration(), vertx);
        });
        ConfigRetriever.getConfigAsFuture(retriever)
                .setHandler(ar -> {
                    if (ar.failed()) {
                        resultFuture.fail(ar.cause());
                    } else {
                        try {
                            initOrModifyConfig(ar.result(), vertx);
                            resultFuture.complete();
                        } catch (IllegalArgumentException e) {
                            logger.error("Config parse error.", e);
                            resultFuture.fail(e);
                        }
                    }
                });
    }

    private void initOrModifyConfig(JsonObject config, Vertx vertx) {
        ProxyConfig proxyConfig = config.mapTo(ProxyConfig.class);
        Map<String, PluginHandler> pluginHandlers = new LinkedHashMap<>();
        for (ProxyConfig.PluginConfig pluginConfig : proxyConfig.getPlugins()) {
            ProxyPlugin<?> proxyPlugin = null;
            try {
                proxyPlugin = (ProxyPlugin) Class.forName(FLAG_FILTER_PATH + pluginConfig.getCode() + ".PluginMain").newInstance();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                logger.error("Load Plugin Error " + FLAG_FILTER_PATH + pluginConfig.getCode() + ".PluginMain", e);
                System.exit(1);
            }
            // Convert java model by args
            pluginConfig.setArgs(proxyPlugin.parseConfig(pluginConfig.getArgs()));
            pluginHandlers.put(pluginConfig.getCode(), new PluginHandler(proxyPlugin, pluginConfig, vertx));
        }
        GlobalContainer.pluginHandlers = pluginHandlers;
        GlobalContainer.proxyConfig = proxyConfig;
    }


}
