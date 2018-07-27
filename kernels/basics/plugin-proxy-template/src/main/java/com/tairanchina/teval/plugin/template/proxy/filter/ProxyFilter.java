package com.tairanchina.teval.plugin.template.proxy.filter;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface ProxyFilter {

    default void init(Map<String, String> args) {
    }

    default void destroy(Map<String, String> args) {
    }

    default CompletableFuture<Boolean> request(FilterContext context) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        request(context, future);
        return future;
    }

    void request(FilterContext context, CompletableFuture<Boolean> future);

    default CompletableFuture<Void> response(FilterContext context) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        response(context, future);
        return future;
    }

    void response(FilterContext context, CompletableFuture<Void> future);

}
