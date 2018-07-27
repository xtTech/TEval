package com.tairanchina.teval.plugin.template.proxy.async;

import java.util.concurrent.CompletableFuture;

public interface AsyncTask<R,T> {

    CompletableFuture<R> execute(T context);

}
