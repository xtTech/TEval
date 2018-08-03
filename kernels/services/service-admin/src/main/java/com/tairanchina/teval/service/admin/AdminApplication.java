package com.tairanchina.teval.service.admin;

import com.tairanchina.teval.TEvalApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class AdminApplication extends TEvalApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AdminApplication.class).web(true).run(args);
    }

}
