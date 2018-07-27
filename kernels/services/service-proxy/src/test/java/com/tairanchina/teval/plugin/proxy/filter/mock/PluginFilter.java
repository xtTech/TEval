package com.tairanchina.teval.plugin.proxy.filter.mock;

import com.tairanchina.teval.plugin.template.proxy.filter.FilterContext;
import com.tairanchina.teval.plugin.template.proxy.filter.ProxyFilter;

import java.util.concurrent.CompletableFuture;

public class PluginFilter implements ProxyFilter {

    @Override
    public void request(FilterContext context, CompletableFuture<Boolean> future) {
        future.complete(true);
    }

    @Override
    public void response(FilterContext context, CompletableFuture<Void> future) {
        future.complete(null);
    }
}
