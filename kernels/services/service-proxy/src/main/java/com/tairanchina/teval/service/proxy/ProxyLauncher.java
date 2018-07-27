package com.tairanchina.teval.service.proxy;


import io.vertx.core.Launcher;
import io.vertx.core.VertxOptions;
import io.vertx.micrometer.MicrometerMetricsOptions;
import io.vertx.micrometer.VertxPrometheusOptions;

public class ProxyLauncher extends Launcher {

    @Override
    public void beforeStartingVertx(VertxOptions options) {
        options.setMetricsOptions(
                new MicrometerMetricsOptions()
                        .setPrometheusOptions(new VertxPrometheusOptions().setEnabled(true))
                        .setEnabled(true));
        super.beforeStartingVertx(options);
    }
}
