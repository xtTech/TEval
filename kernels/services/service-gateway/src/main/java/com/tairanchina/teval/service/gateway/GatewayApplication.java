package com.tairanchina.teval.service.gateway;

import com.tairanchina.teval.TEvalApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
public class GatewayApplication extends TEvalApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(GatewayApplication.class).web(true).run(args);
    }

    @Bean
    public PatternServiceRouteMapper serviceRouteMapper() {
        return new PatternServiceRouteMapper("(?<name>^.+)", "${name}");
    }

}
