package com.tairanchina.teval.service.crypto.com.config;

import com.tairanchina.teval.service.crypto.com.interceptor.IdentifyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 说明:
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/13.
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Autowired
    private IdentifyInterceptor identifyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(identifyInterceptor);
    }
}
