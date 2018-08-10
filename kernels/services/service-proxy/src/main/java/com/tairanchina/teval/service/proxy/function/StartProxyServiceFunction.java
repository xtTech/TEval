package com.tairanchina.teval.service.proxy.function;

import com.tairanchina.teval.common.dto.ProxyConfig;
import com.tairanchina.teval.plugin.template.proxy.filter.PluginContext;
import com.tairanchina.teval.service.proxy.GlobalContainer;
import com.tairanchina.teval.service.proxy.handler.RequestProxyHandler;
import com.tairanchina.teval.service.proxy.handler.ResponseProxyHandler;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;

public class StartProxyServiceFunction extends AbsProxyFunction<ProxyConfig.HttpConfig, HttpServer> {

    private static final StartProxyServiceFunction INSTANCE = new StartProxyServiceFunction();

    public static StartProxyServiceFunction inst() {
        return INSTANCE;
    }

    private static RequestProxyHandler requestProxyHandler = new RequestProxyHandler();
    private static ResponseProxyHandler responseProxyHandler = new ResponseProxyHandler();

    @Override
    protected void apply(ProxyConfig.HttpConfig httpConfig, Vertx vertx, Future<HttpServer> resultFuture) {
        vertx.createHttpServer(new HttpServerOptions()
                .setMaxHeaderSize(httpConfig.getMaxHeaderSize()))
                .requestHandler(req ->
                        requestProxyHandler.handle(req).setHandler(ar -> {
                            if (ar.failed()) {
                                logger.warn(ar.cause().getMessage());
                                req.response().setStatusCode(404).end();
                            } else {
                                PluginContext context = ar.result();
                                logger.trace("[" + context.getTraceId() + "] Redirect request...");
                                HttpClientRequest redirectReq = GlobalContainer.httpClient.request(
                                        req.method(),
                                        context.getRequest().getPort(),
                                        context.getRequest().getHost(),
                                        context.getRequest().getPath() + "?" + context.getRequest().queryToString(),
                                        redirectRes -> {
                                            logger.trace("[" + context.getTraceId() + "] Redirect response: " + redirectRes.statusCode());
                                            req.response().setChunked(true);
                                            req.response().setStatusCode(redirectRes.statusCode());
                                            req.response().headers().setAll(redirectRes.headers());
                                            redirectRes.handler(data -> req.response().write(data));
                                            redirectRes.endHandler(v ->
                                                    responseProxyHandler.handle(context).setHandler(respAr -> {
                                                        if (respAr.failed()) {
                                                            logger.warn("[" + context.getTraceId() + "] Callback response fail.", respAr.cause());
                                                            req.response().setStatusCode(500).end("");
                                                        } else {
                                                            req.response().end();
                                                        }
                                                    }));
                                        });
                                redirectReq.setChunked(true);
                                redirectReq.headers().setAll(context.getRequest().getHeaders());
                                req.handler(redirectReq::write);
                                req.endHandler(v -> redirectReq.end());
                            }
                        }))
                .listen(httpConfig.getProxyPort(), resultFuture.completer());
    }

}
