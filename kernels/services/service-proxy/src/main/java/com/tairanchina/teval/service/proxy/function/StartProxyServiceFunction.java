package com.tairanchina.teval.service.proxy.function;

import com.tairanchina.teval.common.dto.ProxyConfig;
import com.tairanchina.teval.plugin.template.proxy.filter.FilterContext;
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
                                FilterContext context = ar.result();
                                logger.trace("【" + context.getTraceId() + "】Redirect request...");
                                HttpClientRequest c_req = GlobalContainer.httpClient.request(
                                        req.method(),
                                        context.getRequest().getPort(),
                                        context.getRequest().getHost(),
                                        context.getRequest().getPath() + "?" + context.getRequest().queryToString(), c_res -> {
                                            logger.trace("【" + context.getTraceId() + "】Redirect response: " + c_res.statusCode());
                                            req.response().setChunked(true);
                                            req.response().setStatusCode(c_res.statusCode());
                                            req.response().headers().setAll(c_res.headers());
                                            c_res.handler(data -> req.response().write(data));
                                            c_res.endHandler(v ->
                                                    responseProxyHandler.handle(context).setHandler(respAr -> {
                                                        if (respAr.failed()) {
                                                            logger.warn("【" + context.getTraceId() + "】Callback response fail", respAr.cause());
                                                            req.response().setStatusCode(500).end("");
                                                        } else {
                                                            req.response().end();
                                                        }
                                                    }));
                                        });
                                c_req.setChunked(true);
                                c_req.headers().setAll(context.getRequest().getHeaders());
                                req.handler(c_req::write);
                                req.endHandler(v -> c_req.end());
                            }
                        }))
                .listen(httpConfig.getProxyPort(), resultFuture.completer());
    }

}
