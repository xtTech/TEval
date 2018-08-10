package com.tairanchina.teval.service.proxy.function;

import com.tairanchina.teval.service.proxy.GlobalContainer;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.micrometer.backends.BackendRegistries;

public class StartMonitorFunction extends AbsProxyFunction<Integer, HttpServer> {

    private static final StartMonitorFunction INSTANCE = new StartMonitorFunction();

    public static StartMonitorFunction inst() {
        return INSTANCE;
    }

    private static final String FLAG_METRICS_PATH = "/metrics";
    private static final String FLAG_HEALTH_PATH = "/health";

    @Override
    protected void apply(Integer port, Vertx vertx, Future<HttpServer> resultFuture) {
        vertx.createHttpServer().requestHandler(request -> {
            if (request.method().equals(HttpMethod.GET)
                    && request.path().equals(FLAG_METRICS_PATH)) {
                PrometheusMeterRegistry prometheusRegistry = (PrometheusMeterRegistry) BackendRegistries.getDefaultNow();
                if (prometheusRegistry != null) {
                    new ClassLoaderMetrics().bindTo(prometheusRegistry);
                    new JvmMemoryMetrics().bindTo(prometheusRegistry);
                    new JvmGcMetrics().bindTo(prometheusRegistry);
                    new ProcessorMetrics().bindTo(prometheusRegistry);
                    new JvmThreadMetrics().bindTo(prometheusRegistry);
                    String response = prometheusRegistry.scrape();
                    request.response().end(response);
                } else {
                    request.response().setStatusCode(500).end();
                }
            } else if (request.method().equals(HttpMethod.GET)
                    && request.path().equals(FLAG_HEALTH_PATH)) {
                GlobalContainer.healthChecks.invoke(r -> request.response().end(r.toBuffer()));
            }
        }).listen(port, resultFuture.completer());
    }

}
