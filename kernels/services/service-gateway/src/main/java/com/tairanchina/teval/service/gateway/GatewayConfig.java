package com.tairanchina.teval.service.gateway;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "teval.service.gateway")
public class GatewayConfig {


}
