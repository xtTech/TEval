package com.tairanchina.teval.service.proxy.function;

import io.vertx.core.Future;
import io.vertx.core.Vertx;

@FunctionalInterface
public interface ProxyFunction<I, O> {

    Future<O> apply(I input, Vertx vertx);

}
