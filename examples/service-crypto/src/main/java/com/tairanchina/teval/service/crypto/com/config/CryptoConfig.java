package com.tairanchina.teval.service.crypto.com.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 说明:
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/13.
 */
@Component
@ConfigurationProperties(prefix = "service.crypto")
public class CryptoConfig {

    private String seed;

    public String getSeed() {
        return seed;
    }

    public CryptoConfig setSeed(String seed) {
        this.seed = seed;
        return this;
    }
}
