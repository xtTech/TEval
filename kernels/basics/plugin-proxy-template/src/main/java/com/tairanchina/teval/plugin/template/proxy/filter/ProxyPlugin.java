package com.tairanchina.teval.plugin.template.proxy.filter;

import com.ecfront.dew.common.$;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.CompletableFuture;

public interface ProxyPlugin<C> {

    default C parseConfig(Object jsonConfig) {
        Class<C> clazz = (Class<C>) (((ParameterizedType) this.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0]);
        return $.json.toObject(jsonConfig, clazz);
    }

    default void innerInit(Object config) {
        init((C) config);
    }

    default void innerDestroy(Object config) {
        destroy((C) config);
    }

    void init(C Config);

    void destroy(C Config);

    default CompletableFuture<Boolean> request(PluginContext<?> context) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        request((PluginContext<C>) context, future);
        return future;
    }

    void request(PluginContext<C> context, CompletableFuture<Boolean> future);

    default CompletableFuture<Void> response(PluginContext<?> context) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        response((PluginContext<C>) context, future);
        return future;
    }

    void response(PluginContext<C> context, CompletableFuture<Void> future);

}
