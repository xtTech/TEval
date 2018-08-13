package com.tairanchina.teval.service.proxy.handler;

import com.tairanchina.teval.plugin.template.proxy.filter.PluginContext;
import com.tairanchina.teval.service.proxy.GlobalContainer;
import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ResponseProxyHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseProxyHandler.class);

    public Future<PluginContext> handle(PluginContext pluginContext) {
        Future<PluginContext> future = Future.future();
        List<PluginHandler> pluginHandlers = pluginContext.get_apiConfig().getPlugins().keySet().stream()
                .map(i -> GlobalContainer.pluginHandlers.get(i))
                .collect(Collectors.toList());
        Collections.reverse(pluginHandlers);
        Iterator<PluginHandler> filterHandlers = pluginHandlers.iterator();
        future.complete(pluginContext);
        while (filterHandlers.hasNext()) {
            PluginHandler handler = filterHandlers.next();
            pluginContext.setInnerConfig(pluginContext.get_apiConfig().getPlugins().get(handler.getPluginCode()));
            future.compose(handler::response);
        }
        return future;
    }

}
