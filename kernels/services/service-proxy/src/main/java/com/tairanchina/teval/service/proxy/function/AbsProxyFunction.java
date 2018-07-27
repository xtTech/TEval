package com.tairanchina.teval.service.proxy.function;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbsProxyFunction<I, O> implements ProxyFunction<I, O> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Future<O> apply(I input, Vertx vertx) {
        logger.info("Loading " + this.getClass().getName());
        Future<O> resultFuture = Future.future();
        try {
            apply(input, vertx, resultFuture);
        } catch (Exception e) {
            logger.error("Load Error.",e);
            resultFuture.fail(e);
        }
        return resultFuture;
    }

    protected abstract void apply(I input, Vertx vertx, Future<O> resultFuture) throws Exception;

}
