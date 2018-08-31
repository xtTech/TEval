package com.tairanchina.teval.service.admin;

import com.tairanchina.teval.TEvalApplication;
import com.tairanchina.teval.service.admin.intercept.ValidPreInterceptor;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
public class AdminApplication extends TEvalApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AdminApplication.class).web(true).run(args);
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localInterceptor());
    }
    @Bean
    ValidPreInterceptor localInterceptor() {
        return new ValidPreInterceptor();
    }
}
