package com.tairanchina.teval.plugin.proxy.filter.mock;

import com.tairanchina.teval.plugin.template.proxy.filter.PluginContext;
import com.tairanchina.teval.plugin.template.proxy.filter.ProxyPlugin;

import java.util.concurrent.CompletableFuture;

public class PluginMain implements ProxyPlugin<MockConfig> {

    @Override
    public void init(com.tairanchina.teval.plugin.proxy.filter.mock.MockConfig Config) {

    }

    @Override
    public void destroy(com.tairanchina.teval.plugin.proxy.filter.mock.MockConfig Config) {

    }

    @Override
    public void request(PluginContext<com.tairanchina.teval.plugin.proxy.filter.mock.MockConfig> context, CompletableFuture<Boolean> future) {
        future.complete(true);
    }

    @Override
    public void response(PluginContext<com.tairanchina.teval.plugin.proxy.filter.mock.MockConfig> context, CompletableFuture<Void> future) {
        future.complete(null);
    }

}
